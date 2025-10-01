package br.com.bws.portalnoticias.web.controller;

import br.com.bws.portalnoticias.domain.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @GetMapping
    public List<Map<String, String>> getCategorias() {
        return Stream.of(Category.values())
                .map(cat -> Map.of(
                        "value", cat.name(),
                        "name", cat.getName()
                ))
                .collect(Collectors.toList());
    }

}