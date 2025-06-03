package VRS.Video.Rental.System.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Username required!")
    @Column(name = "fullName")
    private String fullName;
    @Email(message = "Email format is invalid!")
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "age")
    @Min(value = 18, message = "Must be at least 18 years old")
    private Integer age;
    @NotBlank(message = "Account Balance required!")
    private Integer account_balance;

}
