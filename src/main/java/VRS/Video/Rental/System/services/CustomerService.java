package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.dtos.customer.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.video.RentVideoDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<String> rentVideo(RentVideoDto rentVideoDto, String email);

    CustomerRegistrationDto viewProfile(String fullName);
}
