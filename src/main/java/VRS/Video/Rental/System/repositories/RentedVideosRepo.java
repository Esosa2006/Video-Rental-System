package VRS.Video.Rental.System.repositories;

import VRS.Video.Rental.System.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentedVideosRepo extends JpaRepository<Video, Long> {
}
