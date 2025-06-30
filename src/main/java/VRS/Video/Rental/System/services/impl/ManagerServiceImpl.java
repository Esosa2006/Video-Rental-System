package VRS.Video.Rental.System.services.impl;

import VRS.Video.Rental.System.dtos.VideoRegistrationDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.exceptions.videoExceptions.VideoAlreadyExistsException;
import VRS.Video.Rental.System.exceptions.videoExceptions.VideoNotFoundException;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.RentedVideosRepo;
import VRS.Video.Rental.System.services.ManagerService;
import VRS.Video.Rental.System.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final CustomerRepository customerRepository;
    private final RentedVideosRepo rentedVideosRepo;
    private final AvailableVideosRepo availableVideosRepo;
    private final StoreService storeService;

    @Autowired
    public ManagerServiceImpl(CustomerRepository customerRepository, RentedVideosRepo rentedVideosRepo, AvailableVideosRepo availableVideosRepo, StoreService storeService) {
        this.customerRepository = customerRepository;
        this.rentedVideosRepo = rentedVideosRepo;
        this.availableVideosRepo = availableVideosRepo;
        this.storeService = storeService;
    }

    @Override
    public Page<Customer> viewAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable);
    }

    @Override
    public Integer checkStoreFunds() {
        return storeService.getStore_funds();
    }

    @Override
    public List<Video> getRentedVideos() {
        return rentedVideosRepo.findAll();
    }

    @Override
    public Video addNewVideo(VideoRegistrationDto videoRegistrationDto) {
        Video existingVideo = availableVideosRepo.findByName(videoRegistrationDto.getName());
        if(existingVideo != null){
            throw new VideoAlreadyExistsException("Video already exists!");
        }
        Video video = new Video();
        video.setQuantity(videoRegistrationDto.getQuantity());
        video.setName(videoRegistrationDto.getName());
        video.setPrice(videoRegistrationDto.getPrice());
        video.setAvailability();
        return availableVideosRepo.save(video);
    }

    @Override
    public ResponseEntity<Video> editVideoDetails(Long id, Map<String, Object> updates) {
        Video video = availableVideosRepo.findById(id).orElseThrow(() -> new VideoNotFoundException("Video not found"));
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

    @Override
    public void deleteVideo(String name) {
        Video video = availableVideosRepo.findByName(name);
        if(!video.getName().isEmpty()){
            availableVideosRepo.delete(video);
        }
        throw new VideoNotFoundException("Video not found");
    }

    @Override
    public Video getVideo(String videoName) {
        return availableVideosRepo.findByName(videoName);
    }
}
