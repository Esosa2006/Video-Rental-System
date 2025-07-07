package VRS.Video.Rental.System.security;

import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.StoreManager;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public MyUserDetailsService(CustomerRepository customerRepository, ManagerRepository managerRepository) {
        this.customerRepository = customerRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Customer customer = customerRepository.findByemail(email);
            if (customer != null){
                return new UserPrincipal(customer.getEmail(), customer.getPassword(), customer.getRole());
            }
            StoreManager manager = managerRepository.findByEmail(email);
            if (manager != null) {
                return new UserPrincipal(manager.getEmail(), manager.getPassword(), manager.getRole());
            }
            throw new UsernameNotFoundException("This user does not exist!");
    }
}
