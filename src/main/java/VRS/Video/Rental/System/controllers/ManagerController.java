package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.dtos.VideoRegistrationDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.services.CustomerService;
import VRS.Video.Rental.System.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<Customer> viewAllCustomers(@RequestParam (defaultValue = "0") int page,
                                           @RequestParam (defaultValue = "10") int size){
        return managerService.viewAllCustomers(page, size);
    }

    //Inventory Management
    @GetMapping("/inventory/storefunds")
    public Integer checkStoreFunds(){
        return managerService.checkStoreFunds();
    }

    @GetMapping("/inventory/available")
    public Page<Video> getAvailableVideos(@RequestParam (defaultValue = "0") int page,
                                          @RequestParam (defaultValue = "5") int size){
        return customerService.getAllVideos(page, size);
    }

    @GetMapping("/inventory/video")
    public Video getVideo(@RequestParam (value = "video_name", required = true) String video_name){
        return managerService.getVideo(video_name);
    }

    @GetMapping("/inventory/rented")
    public List<Video> getRentedVideos(){
        return managerService.getRentedVideos();
    }

    @PostMapping("/inventory/add")
    public ResponseEntity<Video> addNewVideo(@RequestBody VideoRegistrationDto videoRegistrationDto){
        Video added = managerService.addNewVideo(videoRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @PatchMapping(path = "/inventory/{id}")
    public ResponseEntity<Video> editVideoDetails(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
            ){
        return managerService.editVideoDetails(id, updates);
    }

    @DeleteMapping("/inventory/delete")
    public ResponseEntity<Video> deleteVideo(
        @RequestParam(required = true, value = "name") String name
    )
    {
        managerService.deleteVideo(name);
        return ResponseEntity.noContent().build();
    }

}
