package recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> handleIdNotFoundException(IdNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(404));
    }

    @ExceptionHandler(InvalidNumArgumentsException.class)
    public ResponseEntity<String> handleInvalidNumArgumentsException(InvalidNumArgumentsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(400));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<String> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(403));
    }
}
