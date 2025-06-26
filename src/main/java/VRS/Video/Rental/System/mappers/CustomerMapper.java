package VRS.Video.Rental.System.mappers;

import VRS.Video.Rental.System.dtos.CustomerDto;
import VRS.Video.Rental.System.entities.Customer;
import org.mapstruct.Mapper;

@Mapper()
public interface CustomerMapper {
    CustomerDto tDto(Customer customer);


}
