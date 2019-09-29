package com.slykhachov.spring5webfluxrest.controllers;

import com.slykhachov.spring5webfluxrest.domain.Category;
import com.slykhachov.spring5webfluxrest.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories/";

    private final CategoryRepository categoryRepository;

    @GetMapping
    public Flux<Category> list() {
        return this.categoryRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Category> getById(@PathVariable String id) {
        return this.categoryRepository.findById(id);
    }

}
