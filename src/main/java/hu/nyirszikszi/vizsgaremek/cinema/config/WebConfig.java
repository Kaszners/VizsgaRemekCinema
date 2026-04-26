package hu.nyirszikszi.vizsgaremek.cinema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir-images}")
    private String imagesDir;

    @Value("${app.upload-dir-trailers}")
    private String trailersDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagesDir);

        registry.addResourceHandler("/trailers/**")
                .addResourceLocations("file:" + trailersDir);
    }
}
