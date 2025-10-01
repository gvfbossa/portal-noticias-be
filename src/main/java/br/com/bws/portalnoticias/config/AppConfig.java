package br.com.bws.portalnoticias.config;

import br.com.bossawebsolutions.base_api.infrastructure.web.config.CustomObjectMapper;
import br.com.bossawebsolutions.base_api.model.AppUser;
import br.com.bws.portalnoticias.domain.entity.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    @PostConstruct
    public void init() {
        Map<String, Class<? extends AppUser>> appUserTypeMapping = Map.of(
                "Usuario", Usuario.class
        );
        CustomObjectMapper.registerAppUserImplementations(appUserTypeMapping);
    }
}