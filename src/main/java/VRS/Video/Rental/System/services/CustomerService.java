package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.enums.AvailabilityStatus;
import VRS.Video.Rental.System.exceptions.GlobalRuntimeException;
import VRS.Video.Rental.System.mail.EmailSenderService;
import org.springframework.http.ResponseEntity;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.RentedVideosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AvailableVideosRepo availableVideosRepo;
    private final RentedVideosRepo rentedVideosRepo;
    private final StoreService storeService;
    private EmailSenderService emailSenderService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AvailableVideosRepo availableVideosRepo, RentedVideosRepo rentedVideosRepo, StoreService storeService) {
        this.customerRepository = customerRepository;
        this.availableVideosRepo = availableVideosRepo;
        this.rentedVideosRepo = rentedVideosRepo;
        this.storeService = storeService;
    }

    public List<Video> getAllVideos() {
        return availableVideosRepo.findAll();
    }

    public ResponseEntity<String> rentVideo(String videoName, String name) {
        Customer customer = customerRepository.findByfullName(name);
        if (customer == null) {
            throw new GlobalRuntimeException("Customer not found, Please register.");
        }

        Video video = availableVideosRepo.findByName(videoName);
        if (video == null) {
            throw new GlobalRuntimeException("Video not found.");
        }

        if (video.getAvailabilityStatus() == AvailabilityStatus.NOT_AVAILABLE) {
            throw new GlobalRuntimeException("Sorry, this video is currently out of stock.");
        }

        Integer price = video.getPrice();
        Integer balance = customer.getAccount_balance();
        Integer rented_qty = video.getRented_quantity();
        if (balance < price) {
            throw new GlobalRuntimeException("Insufficient funds.");
        }
        else{
        customer.setAccount_balance(balance - price);
        video.setQuantity(video.getQuantity() - 1);
        storeService.addStoreFunds(price);
        video.setRented_quantity(rented_qty + 1);
        }
        rentedVideosRepo.save(video);
        customerRepository.save(customer);
        availableVideosRepo.save(video);
        return ResponseEntity.ok("Enjoy your video");
    }

    public Video getVideo(String name) {
        return availableVideosRepo.findByName(name);
    }

    public void addNewCustomer(Customer customer) {
        Customer existingCustomer = customerRepository.findByemail(customer.getEmail());
        if(existingCustomer != null){
            throw new GlobalRuntimeException("Customer already exists!");
        }
        customerRepository.save(customer);
        emailSenderService.sendEmail(customer.getEmail(),
                "Welcome!!",
                "You are now officially a member of this Video Store!");
    }

    public Customer viewProfile(String fullName) {
        Customer customer = customerRepository.findByfullName(fullName);
        if(!customer.getFullName().isEmpty()){
            return customer;
        }

        throw new GlobalRuntimeException("Customer Not Found");
    }
}
