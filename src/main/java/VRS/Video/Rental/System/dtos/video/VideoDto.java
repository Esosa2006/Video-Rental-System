package VRS.Video.Rental.System.dtos.video;

import VRS.Video.Rental.System.enums.AvailabilityStatus;
import lombok.Data;

@Data
public class VideoDto {
    private String name;
    private Integer price;
    private Integer quantity;
    private AvailabilityStatus availabilityStatus;
}
