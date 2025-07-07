package VRS.Video.Rental.System.entities;

import VRS.Video.Rental.System.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreManager {
    @Id
    @SequenceGenerator(
            name = "my_sequence_generator", sequenceName = "my_sequence", allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator"
    )
    private Long id;
    @Column(name = "managerName")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated
    private Role role;
}
