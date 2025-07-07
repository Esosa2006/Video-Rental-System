package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.dtos.user.UserLoginDto;
import VRS.Video.Rental.System.dtos.customer.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.manager.ManagerRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<CustomerRegistrationDto> signUp(CustomerRegistrationDto customerRegistrationDto);

    ResponseEntity<Object> login(UserLoginDto userLoginDto);

    ResponseEntity<String> signUpManager(@Valid ManagerRegistrationDto managerRegistrationDto);
}
