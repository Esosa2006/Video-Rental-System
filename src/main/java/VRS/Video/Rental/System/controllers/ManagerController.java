package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Videos;
import VRS.Video.Rental.System.services.CustomerService;
import VRS.Video.Rental.System.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {
    private final CustomerService customerService;
    private final ManagerService managerService;

    @Autowired
    public ManagerController(CustomerService customerService, ManagerService managerService) {
        this.customerService = customerService;
        this.managerService = managerService;
    }

    //Customer Management
    @GetMapping("/profiles")
    public List<Customer> viewAllCustomers(){
        return managerService.viewAllCustomers();
    }

    //Inventory Management
    @GetMapping("/inventory/storefunds")
    public Integer checkStoreFunds(){
        return managerService.checkStoreFunds();
    }

    @GetMapping("/inventory/available")
    public List<Videos> getAvailableVideos(){
        return customerService.getAllVideos();
    }

    @GetMapping("/inventory/video")
    public Videos getVideo(@RequestParam (value = "video_name", required = true) String video_name){
        return customerService.getVideo(video_name);
    }

    @GetMapping("/inventory/rented")
    public List<Videos> getRentedVideos(){
        return managerService.getRentedVideos();
    }

    @PostMapping("/inventory/add")
    public ResponseEntity<Videos> addNewVideo(@RequestBody Videos video){
        Videos added = managerService.addNewVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @PatchMapping(path = "/inventory/{id}")
    public ResponseEntity<Videos> editVideoDetails(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
            ){
        return managerService.editVideoDetails(id, updates);
    }

    @DeleteMapping("/inventory/delete")
    public ResponseEntity<Videos> deleteVideo(
        @RequestParam(required = true, value = "name") String name
    )
    {
        managerService.deleteVideo(name);
        return ResponseEntity.noContent().build();
    }

}
