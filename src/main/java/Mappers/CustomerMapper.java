package Mappers;

import Dtos.CustomerDto;
import Entities.Customer;
import org.mapstruct.Mapper;

@Mapper()
public interface CustomerMapper {
    CustomerDto tDto(Customer customer);
}
