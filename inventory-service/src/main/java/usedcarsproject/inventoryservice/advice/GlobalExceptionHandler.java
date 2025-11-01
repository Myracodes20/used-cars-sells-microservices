package usedcarsproject.inventoryservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import usedcarsproject.inventoryservice.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResource<?>> handleResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildErrorResponse(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResource<?>> handleException(Exception exception) {
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResource<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Validation Error")
                .subErrors(errors)
                .build();
        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResource<?>> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        String message = "Malformed JSON request";
        if (exception.getMostSpecificCause() != null && exception.getMostSpecificCause().getMessage() != null) {
            message = exception.getMostSpecificCause().getMessage();
        }
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();
        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResource<?>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String name = exception.getName();
        String requiredType = exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "";
        String value = exception.getValue() != null ? exception.getValue().toString() : "null";
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", value, name, requiredType);
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();
        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResource<?>> handleIllegalArgument(IllegalArgumentException exception) {
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
        return buildErrorResponse(apiError);
    }

    private ResponseEntity<ApiResource<?>> buildErrorResponse(ApiError apiError) {
        ApiResource<?> apiResource = new ApiResource<>(apiError);
        return new ResponseEntity<>(apiResource, apiError.getHttpStatus());
    }
}
