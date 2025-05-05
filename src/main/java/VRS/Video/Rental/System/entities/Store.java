package VRS.Video.Rental.System.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Data
@Component
public class Store {
    @Id
    private Long id;
    private Integer store_funds = 0;
}
