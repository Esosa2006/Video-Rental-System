package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.dtos.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.VideoDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/allVideos")
    public Page<Video> getAllVideos(@RequestParam (defaultValue = "0") int page,
                                    @RequestParam (defaultValue = "5") int size){
        return customerService.getAllVideos(page, size);
    }

    @GetMapping("/video")
    public VideoDto getVideo(@RequestParam (value = "video_name", required = true) String video_name){
        return customerService.getVideo(video_name);
    }

    @GetMapping("/myProfile")
    public CustomerRegistrationDto viewProfile(@RequestParam(value = "fullName", required = true) String fullName){
        return customerService.viewProfile(fullName);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> customerRegistration(@Valid @RequestBody CustomerRegistrationDto customerRegistrationDto){
        customerService.addNewCustomer(customerRegistrationDto);
        return ResponseEntity.ok("Your profile has successfully been registered");
    }

    @PostMapping("/rent")
    public ResponseEntity<String> rentVideo(
            @RequestParam(value = "videoName", required = true) String videoName,
            @RequestParam(value = "name", required = true) String name) {
        return customerService.rentVideo(videoName, name);
    }

}
