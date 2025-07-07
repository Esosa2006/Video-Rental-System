package VRS.Video.Rental.System.dtos.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManagerRegistrationDto {
    @NotBlank(message = "Username required!")
    private String fullName;
    @Email(message = "Email format is invalid!")
    private String email;
    @Min(value = 18, message = "Must be at least 18 years old")
    private Integer age;
    @NotBlank(message = "Password is required!")
    private String password;
}
