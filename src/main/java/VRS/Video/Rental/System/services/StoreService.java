package VRS.Video.Rental.System.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class StoreService {
    private Integer store_funds = 0;

    public void addStoreFunds(Integer price){
        store_funds += price;
    }
}
