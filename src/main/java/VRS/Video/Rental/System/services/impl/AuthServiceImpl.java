package VRS.Video.Rental.System.services.impl;

import VRS.Video.Rental.System.dtos.user.UserLoginDto;
import VRS.Video.Rental.System.dtos.customer.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.manager.ManagerRegistrationDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.StoreManager;
import VRS.Video.Rental.System.enums.Role;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerAlreadyExistsException;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerAuthenticationException;
import VRS.Video.Rental.System.exceptions.managerExceptions.ManagerAlreadyExistsException;
import VRS.Video.Rental.System.mappers.CustomerMapper;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.ManagerRepository;
import VRS.Video.Rental.System.security.JWTService;
import VRS.Video.Rental.System.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final ManagerRepository managerRepository;

    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, AuthenticationManager authManager, JWTService jwtService, ManagerRepository managerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.managerRepository = managerRepository;
    }

    @Override
    public ResponseEntity<CustomerRegistrationDto> signUp(CustomerRegistrationDto customerRegistrationDto) {
        Customer existingCustomer = customerRepository.findByemail(customerRegistrationDto.getEmail());
        if(existingCustomer != null){
            throw new CustomerAlreadyExistsException("Customer already exists!");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Customer newCustomer = new Customer();
        newCustomer.setAge(customerRegistrationDto.getAge());
        newCustomer.setEmail(customerRegistrationDto.getEmail());
        newCustomer.setFullName(customerRegistrationDto.getFullName());
        newCustomer.setAccount_balance(customerRegistrationDto.getAccount_balance());
        newCustomer.setPassword(encoder.encode(customerRegistrationDto.getPassword()));
        newCustomer.setRole(Role.CUSTOMER);
        customerRepository.save(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.tDto(newCustomer));
    }

    @Override
    public ResponseEntity<String> signUpManager(ManagerRegistrationDto managerRegistrationDto) {
        StoreManager manager = managerRepository.findByEmail(managerRegistrationDto.getEmail());
        if (manager != null){
            throw new ManagerAlreadyExistsException("Manager already exists");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        StoreManager newManager = new StoreManager();
        newManager.setEmail(managerRegistrationDto.getEmail());
        newManager.setName(managerRegistrationDto.getFullName());
        newManager.setRole(Role.MANAGER);
        newManager.setPassword(encoder.encode(managerRegistrationDto.getPassword()));
        managerRepository.save(newManager);
        return ResponseEntity.status(HttpStatus.CREATED).body("Manager created successfully!");
    }

    @Override
    public ResponseEntity<Object> login(UserLoginDto userLoginDto) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        if (!authentication.isAuthenticated()){
            throw new CustomerAuthenticationException("Authentication Failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(userLoginDto.getEmail()));
    }
}
