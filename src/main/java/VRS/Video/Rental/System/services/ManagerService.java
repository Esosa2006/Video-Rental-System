package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.dtos.video.VideoRegistrationDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ManagerService{
    Page<Customer> viewAllCustomers(int page, int size);

    Integer checkStoreFunds();

    List<Video> getRentedVideos();

    Video addNewVideo(VideoRegistrationDto videoRegistrationDto, String managerName);

    ResponseEntity<Video> editVideoDetails(Long id, Map<String, Object> updates);

    void deleteVideo(String name);
}
