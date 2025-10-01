package br.com.bws.portalnoticias.domain.repository;


import br.com.bossawebsolutions.base_api.repository.AppUserRepository;
import br.com.bws.portalnoticias.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, AppUserRepository {

}
