package VRS.Video.Rental.System.services;

import VRS.Video.Rental.System.dtos.video.VideoDto;
import VRS.Video.Rental.System.entities.Video;
import org.springframework.data.domain.Page;

public interface VideoService {
    Page<Video> getAllVideos(int page, int size);

    VideoDto getVideo(String videoName);

    void setAvailability(Video video);
}
