package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.dtos.customer.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.video.RentVideoDto;
import VRS.Video.Rental.System.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/myProfile")
    public CustomerRegistrationDto viewProfile(Authentication auth){
        String email = auth.getName();
        return customerService.viewProfile(email);
    }

    @PostMapping("/rent")
    public ResponseEntity<String> rentVideo(
            @RequestBody RentVideoDto rentVideoDto,
            Authentication authentication) {
        String email = authentication.getName();
        return customerService.rentVideo(rentVideoDto, email);
    }

}
