package usedcarsproject.inventoryservice.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private List<String> subErrors;

    public ApiError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}