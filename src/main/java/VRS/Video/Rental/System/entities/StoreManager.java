package VRS.Video.Rental.System.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StoreManager {
    @Id
    private Long id;
    private String name;
    private String email;
}
