package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.dtos.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.VideoDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface CustomerService {

    Page<Video> getAllVideos(int page, int size);

    ResponseEntity<String> rentVideo(String videoName, String name);

    VideoDto getVideo(String name);

    void addNewCustomer(CustomerRegistrationDto customerRegistrationDto);

    CustomerRegistrationDto viewProfile(String fullName);
}
