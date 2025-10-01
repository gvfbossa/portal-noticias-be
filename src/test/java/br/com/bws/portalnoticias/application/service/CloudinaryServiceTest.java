package br.com.bws.portalnoticias.application.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CloudinaryServiceTest {

    private CloudinaryService cloudinaryService;
    private Cloudinary cloudinaryMock;
    private Uploader uploaderMock;

    @BeforeEach
    void setup() {
        cloudinaryMock = Mockito.mock(Cloudinary.class);
        uploaderMock = Mockito.mock(Uploader.class);

        when(cloudinaryMock.uploader()).thenReturn(uploaderMock);

        cloudinaryService = new CloudinaryService(cloudinaryMock);

        try {
            var field = CloudinaryService.class.getDeclaredField("FOLDER");
            field.setAccessible(true);
            field.set(cloudinaryService, "empresaX");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deveFazerUploadERetornarUrl() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "teste.jpg",
                "image/jpeg",
                "conteudo".getBytes()
        );

        Map<String, Object> mockResult = new HashMap<>();
        mockResult.put("url", "https://res.cloudinary.com/exemplo/teste.jpg");

        when(uploaderMock.upload(any(byte[].class), any(Map.class))).thenReturn(mockResult);

        String url = cloudinaryService.uploadImage(file);

        assertNotNull(url);
        assertEquals("https://res.cloudinary.com/exemplo/teste.jpg", url);
    }
}
