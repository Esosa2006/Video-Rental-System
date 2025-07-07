package VRS.Video.Rental.System.mappers;

import VRS.Video.Rental.System.dtos.video.VideoDto;
import VRS.Video.Rental.System.entities.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {
    public VideoDto toDto(Video video){
        VideoDto dto = new VideoDto();
        dto.setName(video.getName());
        dto.setPrice(video.getPrice());
        dto.setQuantity(video.getQuantity());
        dto.setAvailabilityStatus(video.getAvailabilityStatus());
        return dto;
    }
}
