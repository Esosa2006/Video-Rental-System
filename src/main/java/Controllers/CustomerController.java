package Controllers;

import Entities.Customer;
import Entities.Videos;
import Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Videos> getAllVideos(){
        return customerService.getAllVideos();
    }
    @GetMapping
    public Videos getVideo(@RequestParam (value = "video_name", required = true) String video_name){
        return customerService.getVideo(video_name);
    }

    @PostMapping
    public void customerRegistration(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
    }

    @PostMapping
    public Videos rentVideo(
            @RequestParam (value = "videoName", required = true) String videoName,
            @RequestParam (value = "name", required = true) String name){
        return customerService.rentVideo(videoName, name);
    }
}
