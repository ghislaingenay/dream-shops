import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.shoppingcart.dream_shops.enums.OrderStatus;
import com.shoppingcart.dream_shops.http_exception.InternalServerHttpException;
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.model.Cart;
import com.shoppingcart.dream_shops.model.Order;
import com.shoppingcart.dream_shops.model.OrderItem;
import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.repository.OrderRepository;
import com.shoppingcart.dream_shops.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import com.shoppingcart.dream_shops.service.cart.CartService;
import com.shoppingcart.dream_shops.service.order.IOrderService;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final CartService cartService; // Assuming you have a CartService to manage carts

  @Override
  public Order placeOrder(Long userId) {
    Cart cart = cartService.getCartByUserId(userId);
    Order order = createOrder(cart);
    List<OrderItem> orderItems = createOrderItems(order, cart);
    order.setTotalAmount(calculateTotalAmount(orderItems));
    order.setOrderItems(new HashSet<>(orderItems));
    Order savedOrder;
    try {
      savedOrder = orderRepository.save(order);
      cartService.clearCart(cart.getId()); // Clear the cart after placing the order
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to place order: " + e.getMessage());
    }

    return savedOrder;
  }

  private Order createOrder(Cart cart) {
    Order order = new Order();
    order.setUser(cart.getUser());
    order.setOrderStatus(OrderStatus.PEDNING);
    order.setOrderDate(LocalDate.now());
    order.setTotalAmount(BigDecimal.ZERO);

    try {
      return orderRepository.save(order);
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to create order: " + e.getMessage());
    }
  }

  private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
    return orderItems.stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private List<OrderItem> createOrderItems(Order order, Cart cart) {
    return cart.getCartItems().stream().map(cartItem -> {
      Product product = cartItem.getProduct();
      product.setInventory(product.getInventory() - cartItem.getQuantity());
      productRepository.save(product); // Update product inventory
      return new OrderItem(order, product, cartItem.getUnitPrice(), cartItem.getQuantity());
    }).toList();

  }

  @Override
  public Order getOrder(Long orderId) {
    try {
      return orderRepository.findById(orderId)
          .orElseThrow(() -> new NotFoundHttpException("Order not found"));
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve order: " + e.getMessage());
    }
  }

  @Override
  public List<Order> getOrdersByUserId(Long userId) {
    try {
      return orderRepository.findByUserId(userId);
    } catch (DataAccessException e) {
      throw new InternalServerHttpException("Failed to retrieve orders for user: " + e.getMessage());
    } catch (Exception e) {
      throw new InternalServerHttpException("An unexpected error occurred: " + e.getMessage());
    }
  }
}
