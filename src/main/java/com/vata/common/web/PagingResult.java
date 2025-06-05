package com.vata.common.web;

import java.util.List;
import org.springframework.data.domain.Page;

public record PagingResult<T>(
        List<T> content,
        int page,
        int size,
        int totalPages,
        long totalElements,
        boolean hasNext
) {
    public static <T> PagingResult<T> from(Page<T> page) {
        return new PagingResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.hasNext()
        );
    }
}
