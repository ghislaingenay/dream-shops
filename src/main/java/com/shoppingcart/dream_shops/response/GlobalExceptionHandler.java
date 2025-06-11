
import com.shoppingcart.dream_shops.http_exception.NotFoundHttpException;
import com.shoppingcart.dream_shops.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundHttpException.class)
  public ResponseEntity<ApiResponse> handleNotFound(NotFoundHttpException ex) {
    ApiResponse response = new ApiResponse(ex.getMessage(), null);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  // You can add more handlers for other exceptions here
}