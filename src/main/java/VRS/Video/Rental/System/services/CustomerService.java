package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.enums.AvailabilityStatus;
import VRS.Video.Rental.System.exceptions.GlobalRuntimeException;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerAlreadyExistsException;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerNotFoundException;
import VRS.Video.Rental.System.exceptions.customerExceptions.InsufficientFundsException;
import VRS.Video.Rental.System.exceptions.videoExceptions.OutOfStockException;
import VRS.Video.Rental.System.exceptions.videoExceptions.VideoNotFoundException;
import VRS.Video.Rental.System.mail.EmailSenderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.RentedVideosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AvailableVideosRepo availableVideosRepo;
    private final RentedVideosRepo rentedVideosRepo;
    private final StoreService storeService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AvailableVideosRepo availableVideosRepo, RentedVideosRepo rentedVideosRepo, StoreService storeService, EmailSenderService emailSenderService) {
        this.customerRepository = customerRepository;
        this.availableVideosRepo = availableVideosRepo;
        this.rentedVideosRepo = rentedVideosRepo;
        this.storeService = storeService;
        this.emailSenderService = emailSenderService;
    }

    public Page<Video> getAllVideos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return availableVideosRepo.findAll(pageable);
    }

    public ResponseEntity<String> rentVideo(String videoName, String name) {
        Customer customer = customerRepository.findByfullName(name);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found, Please register.");
        }

        Video video = availableVideosRepo.findByName(videoName);
        if (video == null) {
            throw new VideoNotFoundException("Video not found.");
        }

        if (video.getAvailabilityStatus() == AvailabilityStatus.NOT_AVAILABLE) {
            throw new OutOfStockException("Sorry, this video is currently out of stock.");
        }

        Integer price = video.getPrice();
        Integer balance = customer.getAccount_balance();
        Integer rented_qty = video.getRented_quantity();
        if (balance < price) {
            throw new InsufficientFundsException("Insufficient funds.");
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
            throw new CustomerAlreadyExistsException("Customer already exists!");
        }
        customerRepository.save(customer);
        emailSenderService.sendEmail(customer.getEmail(),
                "Welcome!!",
                "You are now officially a member of this Video Store!");
    }

    public Customer viewProfile(String fullName) {
        Customer customer = customerRepository.findByfullName(fullName);
        if(!customer.getFullName().isEmpty()) {
            return customer;
        }
        throw new CustomerNotFoundException("Customer Not Found");
    }
}
