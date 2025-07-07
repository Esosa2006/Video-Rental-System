package VRS.Video.Rental.System.entities;

import VRS.Video.Rental.System.enums.Role;
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
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "age")
    private Integer age;
    @Column(name = "account_balance")
    private Integer account_balance;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private Role role;
}
