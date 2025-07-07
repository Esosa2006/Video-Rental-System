package VRS.Video.Rental.System.dtos.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRegistrationDto {
    @NotBlank(message = "Username required!")
    private String fullName;
    @Email(message = "Email format is invalid!")
    private String email;
    @Min(value = 18, message = "Must be at least 18 years old")
    private Integer age;
    @NotBlank(message = "Account Balance required!")
    private Integer account_balance;
    @NotBlank(message = "Password is required!")
    private String password;
}
