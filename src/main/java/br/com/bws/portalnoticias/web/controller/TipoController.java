package br.com.bws.portalnoticias.web.controller;

import br.com.bws.portalnoticias.domain.model.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/tipos")
public class TipoController {

    @GetMapping
    public List<Map<String, String>> getTipos() {
        return Stream.of(Type.values())
                .map(type -> Map.of(
                        "value", type.name(),
                        "name", type.getName()
                ))
                .collect(Collectors.toList());
    }

}
