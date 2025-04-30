package Services;

import Entities.Customer;
import Entities.Videos;
import Mappers.CustomerMapper;
import Repositories.AvailableVideosRepo;
import Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AvailableVideosRepo availableVideosRepo;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AvailableVideosRepo availableVideosRepo) {
        this.customerRepository = customerRepository;
        this.availableVideosRepo = availableVideosRepo;
    }

    public List<Videos> getAllVideos() {
        return availableVideosRepo.findAll();
    }

    public Videos rentVideo(String videoName, String name) {
        Customer customer = customerRepository.findByName(name);
        Long id = customer.getId();
        if(customerRepository.existsById(id)){
            System.out.println("Welcome");
            Videos video = availableVideosRepo.findByName(videoName);
            if(video.getQuantity() == 0){
                System.out.println("Sorry, this video sis currently out of stock!");
                return null;
            }
            else{
                Integer price_of_vid = video.getPrice();
                Integer customerAccount = customer.getAccount_balance();
                System.out.println("Video is available");
                if (customerAccount >= price_of_vid){
                    System.out.println("Successful purchase!");
                    customerAccount =- price_of_vid;
                    customer.setAccount_balance(customerAccount);
                    return video;
                }
                else{
                    System.out.println("Insufficient Funds");
                    return null;
                }
            }
        }
        else{
            System.out.println("Please register your details!");
        }
        return null;
    }

    public Videos getVideo(String name) {
        return availableVideosRepo.findByName(name);
    }

    public void addNewCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
