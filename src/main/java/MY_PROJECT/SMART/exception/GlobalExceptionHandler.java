package MY_PROJECT.SMART.exception;

import MY_PROJECT.SMART.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        // ==================== RUNTIME EXCEPTION (400) ====================
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
            ErrorResponse error = new ErrorResponse(
                    "error",
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    LocalDateTime.now().toString()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // ==================== UNAUTHORIZED ILLEGAL ARGUMENT(400) ====================
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
            ErrorResponse error = new ErrorResponse(
                    "error",
                    HttpStatus.BAD_REQUEST.value(),
                    "Anda tidak memiliki akses!",
                    LocalDateTime.now().toString()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // ==================== NOT FOUND (404) ====================
        @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleEntityNotFoundException(jakarta.persistence.EntityNotFoundException ex) {
            ErrorResponse error = new ErrorResponse(
                    "error",
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    LocalDateTime.now().toString()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // ==================== GENERIC EXCEPTION (500) ====================
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
            ErrorResponse error = new ErrorResponse(
                    "error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Terjadi kesalahan pada server. Silakan coba lagi.",
                    LocalDateTime.now().toString()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
