package br.com.bws.portalnoticias.application.service;

import br.com.bws.portalnoticias.domain.entity.Usuario;
import br.com.bws.portalnoticias.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario retornaUsuario() {
        return usuarioRepository.findAll().getFirst();
    }

}
