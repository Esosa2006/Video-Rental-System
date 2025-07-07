package VRS.Video.Rental.System.mappers;

import VRS.Video.Rental.System.dtos.customer.CustomerRegistrationDto;
import VRS.Video.Rental.System.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerRegistrationDto tDto(Customer customer){
        CustomerRegistrationDto customerRegistrationDto = new CustomerRegistrationDto();
        customerRegistrationDto.setFullName(customer.getFullName());
        customerRegistrationDto.setEmail(customer.getEmail());
        customerRegistrationDto.setAge(customer.getAge());
        customerRegistrationDto.setAccount_balance(customer.getAccount_balance());
        return customerRegistrationDto;
    };
}
