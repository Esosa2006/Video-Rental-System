package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.exceptions.GlobalRuntimeException;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.RentedVideosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Customer> viewAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable);
    }

    public Integer checkStoreFunds() {
        return storeService.getStore_funds();
    }

    public List<Video> getRentedVideos() {
        return rentedVideosRepo.findAll();
    }

    public Video addNewVideo(Video video) {
        video.checkIfAvailable();
        return availableVideosRepo.save(video);
    }

    public ResponseEntity<Video> editVideoDetails(Long id, Map<String, Object> updates) {
        Video video = availableVideosRepo.findById(id).orElseThrow(() -> new GlobalRuntimeException("Video not found"));
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
        Video video = availableVideosRepo.findByName(name);
        if(!video.getName().isEmpty()){
            availableVideosRepo.delete(video);
        }
        throw new GlobalRuntimeException("Video not found");
    }
}
