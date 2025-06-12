
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

import com.shoppingcart.dream_shops.service.order.IOrderService;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  @Override
  public Order placeOrder(Long userId) {

    return null; // Placeholder return statement
  }

  private Order createOrder(Cart cart) {
    Order order = new Order();
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
    } catch (NotFoundHttpException e) {
      throw e; // rethrow the exception if it occurs
    } catch (Exception e) {
      throw new InternalServerHttpException("Failed to retrieve order: " + e.getMessage());
    }
  }

}
