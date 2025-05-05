package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Videos;
import VRS.Video.Rental.System.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/allVideos")
    public List<Videos> getAllVideos(){
        return customerService.getAllVideos();
    }

    @GetMapping("/video")
    public Videos getVideo(@RequestParam (value = "video_name", required = true) String video_name){
        return customerService.getVideo(video_name);
    }

    @GetMapping("/myProfile")
    public Customer viewProfile(@RequestParam(value = "fullName", required = true) String fullName){
        return customerService.viewProfile(fullName);
    }

    @PostMapping("/registration")
    public void customerRegistration(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
    }

    @PostMapping("/rent")
    public ResponseEntity<String> rentVideo(
            @RequestParam(value = "videoName", required = true) String videoName,
            @RequestParam(value = "name", required = true) String name) {

        return customerService.rentVideo(videoName, name);
    }

}
