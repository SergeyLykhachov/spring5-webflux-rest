package com.slykhachov.spring5webfluxrest.controllers;

import com.slykhachov.spring5webfluxrest.domain.Category;
import com.slykhachov.spring5webfluxrest.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> createFromStream(@RequestBody Publisher<Category> categoryStream) {
        return this.categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping("{id}")
    public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return this.categoryRepository.save(category);

    }

    @PatchMapping("{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {

        Category foundCategory = categoryRepository.findById(id).block();

        if(!foundCategory.getDescription().equals(category.getDescription())) {
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }

}
