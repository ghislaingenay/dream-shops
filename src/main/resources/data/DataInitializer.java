package data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.shoppingcart.dream_shops.model.Category;
import com.shoppingcart.dream_shops.model.Product;
import com.shoppingcart.dream_shops.repository.CategoryRepository;
import com.shoppingcart.dream_shops.repository.ProductRepository;
import com.shoppingcart.dream_shops.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  // private void createUserIfNotExists(String username, String password) {
  // if (!userRepository.findBy('email', username)) {
  // User user = new User();
  // user.setUsername(username);
  // user.setPassword(passwordEncoder.encode(&é"")); // In a real application,
  // ensure to hash the
  // password
  // userRepository.save(user);
  // }
  // }

  @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Initialize categories
        if (!categoryRepository.existsByName("Electronics")) {
            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryRepository.save(electronics);
        }

        // Initialize products
        if (!productRepository.existsByNameAndBrand("Electronics", "Apple")) {
            Product smartphone = new Product();
            smartphone.setName("Smartphone");
            smartphone.setCategory(categoryRepository.findByName("Electronics"));
            productRepository.save(smartphone);
        }

        // Initialize users
        

}
