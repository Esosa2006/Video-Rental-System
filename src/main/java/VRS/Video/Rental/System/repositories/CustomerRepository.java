package VRS.Video.Rental.System.repositories;

import VRS.Video.Rental.System.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByfullName(String fullName);
}
