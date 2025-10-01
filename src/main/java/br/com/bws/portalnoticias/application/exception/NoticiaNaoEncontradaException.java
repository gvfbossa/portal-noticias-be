package br.com.bws.portalnoticias.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoticiaNaoEncontradaException extends RuntimeException {


    public NoticiaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
