package br.com.bws.portalnoticias.domain.entity;

import br.com.bws.portalnoticias.domain.model.Category;
import br.com.bws.portalnoticias.domain.model.Type;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String headline;

    private String subtitle;

    @Column(length = 500)
    private String summary;

    private String imagePath;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Lob
    private String fullText;
}
