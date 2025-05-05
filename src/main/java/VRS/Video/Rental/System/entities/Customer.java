package VRS.Video.Rental.System.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @SequenceGenerator(
            name = "my_sequence_generator", sequenceName = "my_sequence", allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator"
    )
    private Long Id;
    private String fullName;
    private String email;
    private Integer age;
    private Integer account_balance;
}
