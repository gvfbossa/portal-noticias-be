package br.com.bws.portalnoticias.domain.entity;

import br.com.bws.portalnoticias.domain.model.AdPosition;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagem;

    private String url;

    @Enumerated(EnumType.STRING)
    private AdPosition position;

    private LocalDate dataExpiracao;
}
