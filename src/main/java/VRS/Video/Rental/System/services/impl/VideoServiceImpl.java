package VRS.Video.Rental.System.services.impl;

import VRS.Video.Rental.System.dtos.video.VideoDto;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.enums.AvailabilityStatus;
import VRS.Video.Rental.System.mappers.VideoMapper;
import VRS.Video.Rental.System.repositories.AvailableVideosRepo;
import VRS.Video.Rental.System.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {
    private final AvailableVideosRepo availableVideosRepo;
    private final VideoMapper videoMapper;

    @Autowired
    public VideoServiceImpl(AvailableVideosRepo availableVideosRepo, VideoMapper videoMapper) {
        this.availableVideosRepo = availableVideosRepo;
        this.videoMapper = videoMapper;
    }

    @Override
    public Page<Video> getAllVideos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return availableVideosRepo.findAll(pageable);

    }

    @Override
    public VideoDto getVideo(String name) {
        Video video = availableVideosRepo.findByName(name);
        return videoMapper.toDto(video);
    }

    public void setAvailability(Video video){
        if(video.getQuantity() == 0){
            video.setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE);
        }
        else{
            video.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        }
    }
}
