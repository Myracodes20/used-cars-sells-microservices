package usedcarsproject.inventoryservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.*;
import usedcarsproject.inventoryservice.config.MultiFormatLocalDateDeserializer;
import usedcarsproject.inventoryservice.util.CarStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDto {
    // id is optional on create; keep @Positive if present
    @Positive
    private Long id;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @Min(1886)
    private Integer year;

    @PositiveOrZero
    private Integer mileage;

    @NotNull
    @Positive
    private BigDecimal price;

    @Size(max = 1000)
    private String description;

    @PastOrPresent
    @JsonDeserialize(using = MultiFormatLocalDateDeserializer.class)
    private LocalDate dateListed;

    @NotNull
    private CarStatus carStatus;
}
