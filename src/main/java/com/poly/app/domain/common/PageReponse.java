package com.poly.app.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PageReponse<T> {
    private List<T> data;
    private long totalPages;
    private int currentPage;

}
