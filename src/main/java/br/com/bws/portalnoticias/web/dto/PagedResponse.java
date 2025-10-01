package br.com.bws.portalnoticias.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PagedResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
    private boolean last;

    public PagedResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.last = page.isLast();
    }

}
