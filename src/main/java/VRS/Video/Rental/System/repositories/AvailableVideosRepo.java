package VRS.Video.Rental.System.repositories;

import VRS.Video.Rental.System.entities.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableVideosRepo extends JpaRepository<Videos, Long> {
    Videos findByName(String name);
}
