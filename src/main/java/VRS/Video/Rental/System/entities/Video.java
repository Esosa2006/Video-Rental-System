package VRS.Video.Rental.System.entities;

import VRS.Video.Rental.System.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Video {
    @Id
    @SequenceGenerator(
            name = "my_sequence_generator", sequenceName = "my_sequence", allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator"
    )
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer rented_quantity;
    @Transient
    private AvailabilityStatus availabilityStatus;

    public void checkIfAvailable(){
        if(quantity == 0){
            availabilityStatus = AvailabilityStatus.NOT_AVAILABLE;
        }
        else{
            availabilityStatus = AvailabilityStatus.AVAILABLE;
        }
    }
}
