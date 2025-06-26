package VRS.Video.Rental.System.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VideoRegistrationDto {
    @NotBlank(message = "Name is required")
    private String name;
    @Min(value = 0, message = "No negative values")
    @NotBlank(message = "price is required")
    private Integer price;
    @Min(value = 0, message = "No negative values")
    @NotBlank(message = "Quantity is required")
    private Integer quantity;
}
