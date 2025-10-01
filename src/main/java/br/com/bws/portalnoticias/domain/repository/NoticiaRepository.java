package br.com.bws.portalnoticias.domain.repository;

import br.com.bws.portalnoticias.domain.entity.Noticia;
import org.springframework.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    @NonNull
    Page<Noticia> findAll( Pageable pageable);
}
