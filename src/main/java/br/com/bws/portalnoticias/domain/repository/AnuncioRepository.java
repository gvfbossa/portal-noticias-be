package br.com.bws.portalnoticias.domain.repository;

import br.com.bws.portalnoticias.domain.entity.Anuncio;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    List<Anuncio> findByDataExpiracaoGreaterThanEqual(LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Anuncio a WHERE a.dataExpiracao < :dataHoje")
    void deleteByDataExpiracaoBefore(LocalDate dataHoje);

}
