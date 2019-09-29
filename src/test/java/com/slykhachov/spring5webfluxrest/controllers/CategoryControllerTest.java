package com.slykhachov.spring5webfluxrest.controllers;

import com.slykhachov.spring5webfluxrest.domain.Category;
import com.slykhachov.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void listTest() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().description("Cat1").build(),
                                Category.builder().description("Cat2").build()
                        )
        );

        webTestClient.get()
                .uri(CategoryController.BASE_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getByIdTest() {
        String id = "someId";
        BDDMockito.given(categoryRepository.findById(id))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        webTestClient.get()
                .uri(CategoryController.BASE_URL + id)
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createFromStreamTest() {

        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Flux<Category> flux = Flux.just(
                Category.builder().description("Cat1").build(),
                Category.builder().description("Cat2").build()
        );

        webTestClient.post()
                .uri(CategoryController.BASE_URL)
                .body(flux, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateTest() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> mono = Mono.just(Category.builder().description("Cat").build());

        webTestClient.put()
                .uri(CategoryController.BASE_URL + "abcdefghij")
                .body(mono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

}