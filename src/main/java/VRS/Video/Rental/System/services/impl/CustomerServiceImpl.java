package VRS.Video.Rental.System.services.impl;

import VRS.Video.Rental.System.dtos.customer.CustomerRegistrationDto;
import VRS.Video.Rental.System.dtos.video.RentVideoDto;
import VRS.Video.Rental.System.entities.Customer;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.enums.AvailabilityStatus;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerNotFoundException;
import VRS.Video.Rental.System.exceptions.customerExceptions.InsufficientFundsException;
import VRS.Video.Rental.System.exceptions.videoExceptions.OutOfStockException;
import VRS.Video.Rental.System.exceptions.videoExceptions.VideoNotFoundException;
import VRS.Video.Rental.System.mail.EmailSenderService;
import VRS.Video.Rental.System.mappers.CustomerMapper;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.repositories.CustomerRepository;
import VRS.Video.Rental.System.repositories.RentedVideosRepo;
import VRS.Video.Rental.System.services.CustomerService;
import VRS.Video.Rental.System.services.StoreService;
import VRS.Video.Rental.System.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AvailableVideosRepo availableVideosRepo;
    private final RentedVideosRepo rentedVideosRepo;
    private final StoreService storeService;
    private final EmailSenderService emailSenderService;
    private final CustomerMapper customerMapper;
    private final VideoService videoService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AvailableVideosRepo availableVideosRepo, RentedVideosRepo rentedVideosRepo, StoreService storeService, EmailSenderService emailSenderService, CustomerMapper customerMapper, VideoService videoService) {
        this.customerRepository = customerRepository;
        this.availableVideosRepo = availableVideosRepo;
        this.rentedVideosRepo = rentedVideosRepo;
        this.storeService = storeService;
        this.emailSenderService = emailSenderService;
        this.customerMapper = customerMapper;
        this.videoService = videoService;
    }

    @Override
    public ResponseEntity<String> rentVideo(RentVideoDto rentVideoDto, String email) {
        String videoName = rentVideoDto.getVideoName();
        Customer customer = customerRepository.findByemail(email);
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
            videoService.setAvailability(video);
        }
        rentedVideosRepo.save(video);
        customerRepository.save(customer);
        availableVideosRepo.save(video);
        return ResponseEntity.ok("Enjoy your video");
    }

    @Override
    public CustomerRegistrationDto viewProfile(String email) {
        Customer customer = customerRepository.findByemail(email);
        if(!customer.getFullName().isEmpty()) {
            return customerMapper.tDto(customer);
        }
        throw new CustomerNotFoundException("Customer Not Found");
    }
}
