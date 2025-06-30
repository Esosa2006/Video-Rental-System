package VRS.Video.Rental.System.services.impl;

import VRS.Video.Rental.System.dtos.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.VideoDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.enums.AvailabilityStatus;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerAlreadyExistsException;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerNotFoundException;
import VRS.Video.Rental.System.exceptions.customerExceptions.InsufficientFundsException;
import VRS.Video.Rental.System.exceptions.videoExceptions.OutOfStockException;
import VRS.Video.Rental.System.exceptions.videoExceptions.VideoNotFoundException;
import VRS.Video.Rental.System.mail.EmailSenderService;
import VRS.Video.Rental.System.mappers.CustomerMapper;
import VRS.Video.Rental.System.mappers.VideoMapper;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.RentedVideosRepo;
import VRS.Video.Rental.System.services.CustomerService;
import VRS.Video.Rental.System.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AvailableVideosRepo availableVideosRepo;
    private final RentedVideosRepo rentedVideosRepo;
    private final StoreService storeService;
    private final EmailSenderService emailSenderService;
    private final VideoMapper videoMapper;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AvailableVideosRepo availableVideosRepo, RentedVideosRepo rentedVideosRepo, StoreService storeService, EmailSenderService emailSenderService, VideoMapper videoMapper, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.availableVideosRepo = availableVideosRepo;
        this.rentedVideosRepo = rentedVideosRepo;
        this.storeService = storeService;
        this.emailSenderService = emailSenderService;
        this.videoMapper = videoMapper;
        this.customerMapper = customerMapper;
    }

    @Override
    public Page<Video> getAllVideos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return availableVideosRepo.findAll(pageable);

    }

    @Override
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
            video.setAvailability();
        }
        rentedVideosRepo.save(video);
        customerRepository.save(customer);
        availableVideosRepo.save(video);
        return ResponseEntity.ok("Enjoy your video");
    }

    @Override
    public VideoDto getVideo(String name) {
        Video video = availableVideosRepo.findByName(name);
        return videoMapper.toDto(video);
    }

    @Override
    public void addNewCustomer(CustomerRegistrationDto customerRegistrationDto) {
        Customer existingCustomer = customerRepository.findByemail(customerRegistrationDto.getEmail());
        if(existingCustomer != null){
            throw new CustomerAlreadyExistsException("Customer already exists!");
        }
        Customer newCustomer = new Customer();
        newCustomer.setAge(customerRegistrationDto.getAge());
        newCustomer.setEmail(customerRegistrationDto.getEmail());
        newCustomer.setFullName(customerRegistrationDto.getFullName());
        newCustomer.setAccount_balance(customerRegistrationDto.getAccount_balance());
        customerRepository.save(newCustomer);
//        emailSenderService.sendEmail(newCustomer.getEmail(),
//                "Welcome!!",
//                "You are now officially a member of this Video Store!");

    }

    @Override
    public CustomerRegistrationDto viewProfile(String fullName) {
        Customer customer = customerRepository.findByfullName(fullName);
        if(!customer.getFullName().isEmpty()) {
            return customerMapper.tDto(customer);
        }
        throw new CustomerNotFoundException("Customer Not Found");
    }
}
