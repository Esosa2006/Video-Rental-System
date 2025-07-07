package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.dtos.user.UserLoginDto;
import VRS.Video.Rental.System.dtos.customer.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.manager.ManagerRegistrationDto;
import VRS.Video.Rental.System.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> signUp(@Valid @RequestBody CustomerRegistrationDto customerRegistrationDto){
        authService.signUp(customerRegistrationDto);
        return ResponseEntity.ok("Your profile has successfully been registered");
    }

    @PostMapping("/registerManager")
    public ResponseEntity<String> signUpManager(@Valid @RequestBody ManagerRegistrationDto managerRegistrationDto){
        authService.signUpManager(managerRegistrationDto);
        return ResponseEntity.ok("Registration Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginDto userLoginDto){
        return ResponseEntity.ok(authService.login(userLoginDto));
    }
}
