package VRS.Video.Rental.System.repositories;

import VRS.Video.Rental.System.entities.StoreManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<StoreManager, Long> {
    StoreManager findByEmail(String email);
}
