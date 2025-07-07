package VRS.Video.Rental.System.controllers;

import VRS.Video.Rental.System.dtos.video.VideoDto;
import VRS.Video.Rental.System.entities.Video;
import VRS.Video.Rental.System.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/allVideos")
    public Page<Video> getAllVideos(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam (defaultValue = "5") int size){
        return videoService.getAllVideos(page, size);
    }

    @GetMapping("/video")
    public VideoDto getVideo(@RequestParam (value = "video_name", required = true) String video_name){
        return videoService.getVideo(video_name);
    }
}
