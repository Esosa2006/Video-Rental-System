package VRS.Video.Rental.System.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This will only serve static resources from specific paths
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // Do not map API paths like /api/** to static resources
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/");
    }
}
