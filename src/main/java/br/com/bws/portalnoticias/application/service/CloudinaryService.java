package br.com.bws.portalnoticias.application.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    private final Cloudinary cloudinary;

    @Value("${folder.company.name}")
    private String FOLDER;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public void clearCloudinaryFolder() {
        try {
            Map result = cloudinary.api().deleteResourcesByPrefix(FOLDER, ObjectUtils.emptyMap());

            System.out.println("Recursos deletados: " + result);
        } catch (Exception e) {
            logger.error("Erro ao remover recursos do Cloudinary. Erro: {}", e.getMessage());
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("folder", FOLDER + "/images");
        params.put("resource_type", "auto");

        var uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

        return (String) uploadResult.get("url");
    }
}
