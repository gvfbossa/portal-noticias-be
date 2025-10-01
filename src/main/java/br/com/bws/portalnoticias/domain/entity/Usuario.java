package br.com.bws.portalnoticias.domain.entity;

import br.com.bossawebsolutions.base_api.model.AppUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Usuario implements AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Override
    public Set<String> getRoles() {
        return Set.of();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return null;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return null;
    }
}
