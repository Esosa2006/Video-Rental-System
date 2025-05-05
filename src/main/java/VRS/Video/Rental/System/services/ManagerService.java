package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Videos;
import VRS.Video.Rental.System.exceptions.GlobalRuntimeException;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.RentedVideosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerService{
    private final CustomerRepository customerRepository;
    private final RentedVideosRepo rentedVideosRepo;
    private final AvailableVideosRepo availableVideosRepo;
    private final StoreService storeService;

    @Autowired
    public ManagerService(CustomerRepository customerRepository, RentedVideosRepo rentedVideosRepo, AvailableVideosRepo availableVideosRepo, StoreService storeService) {
        this.customerRepository = customerRepository;
        this.rentedVideosRepo = rentedVideosRepo;
        this.availableVideosRepo = availableVideosRepo;
        this.storeService = storeService;
    }

    public List<Customer> viewAllCustomers() {
        return customerRepository.findAll();
    }

    public Integer checkStoreFunds() {
        return storeService.getStore_funds();
    }

    public List<Videos> getRentedVideos() {
        return rentedVideosRepo.findAll();
    }

    public Videos addNewVideo(Videos video) {
        return availableVideosRepo.save(video);
    }

    public ResponseEntity<Videos> editVideoDetails(Long id, Map<String, Object> updates) {
        Videos video = availableVideosRepo.findById(id).orElseThrow(() -> new GlobalRuntimeException("Video not found"));
        if(updates.containsKey("name")){
            video.setName(String.valueOf(updates.get("name")));
        }
        if(updates.containsKey("price")){
            video.setPrice((Integer) updates.get("price"));
        }
        if (updates.containsKey("quantity")){
            video.setQuantity((Integer) updates.get("quantity"));
        }
        return ResponseEntity.ok(availableVideosRepo.save(video));
    }

    public void deleteVideo(String name) {
        Videos video = availableVideosRepo.findByName(name);
        if(!video.getName().isEmpty()){
            availableVideosRepo.delete(video);
        }
        throw new GlobalRuntimeException("Video not found");
    }
}
