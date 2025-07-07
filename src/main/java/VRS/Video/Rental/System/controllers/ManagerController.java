package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.dtos.video.VideoRegistrationDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {
    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    //Customer Management
    @GetMapping("/profiles")
    public Page<Customer> viewAllCustomers(@RequestParam (defaultValue = "0") int page,
                                           @RequestParam (defaultValue = "10") int size){
        return managerService.viewAllCustomers(page, size);
    }

    //Inventory Management
    @GetMapping("/storefunds")
    public Integer checkStoreFunds(){
        return managerService.checkStoreFunds();
    }

    @GetMapping("/videos/rented")
    public List<Video> getRentedVideos(){;
        return managerService.getRentedVideos();
    }

    @PostMapping("/videos/add")
    public ResponseEntity<Video> addNewVideo(@RequestBody VideoRegistrationDto videoRegistrationDto, Authentication authentication){
        String managerName = authentication.getName();
        Video added = managerService.addNewVideo(videoRegistrationDto, managerName);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @PatchMapping(path = "/videos/{id}")
    public ResponseEntity<Video> editVideoDetails(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
            ){
        return managerService.editVideoDetails(id, updates);
    }

    @DeleteMapping("/videos/delete")
    public ResponseEntity<Video> deleteVideo(@RequestParam(required = true, value = "name") String name)
    {
        managerService.deleteVideo(name);
        return ResponseEntity.noContent().build();
    }

}
