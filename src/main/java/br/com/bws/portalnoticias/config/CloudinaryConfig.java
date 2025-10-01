package br.com.bws.portalnoticias.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.http5.UploaderStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map configs = Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        );
        Cloudinary cloudinary = new Cloudinary(configs);
        UploaderStrategy uploaderStrategy = new UploaderStrategy();
        return cloudinary;
    }

}
