package com.slykhachov.spring5webfluxrest.controllers;

import com.slykhachov.spring5webfluxrest.domain.Vendor;
import com.slykhachov.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class VendorControllerTest {

    VendorRepository vendorRepository;
    VendorController vendorController;
    WebTestClient webTestClient;

    @Before
    public void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void listTest() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(
                        Flux.just(
                                Vendor.builder().firstName("Fred").lastName("Flinstone").build(),
                                Vendor.builder().firstName("Barney").lastName("Rubble").build()
                        )
        );

        webTestClient.get()
                .uri(VendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void findByIdTest() {
        String id = "someId";
        BDDMockito.given(vendorRepository.findById(id))
                .willReturn(
                        Mono.just(
                                Vendor.builder().firstName("Jimmy").lastName("Johns").build()
                        )
        );

        webTestClient.get()
                .uri(VendorController.BASE_URL + id)
                .exchange()
                .expectBody(Vendor.class);

    }

    @Test
    public void createVendorTest() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("First Name")
                .lastName("Last Name").build());

        webTestClient.post()
                .uri(VendorController.BASE_URL)
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateVendorTest() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> mono = Mono.just(
                Vendor.builder()
                .firstName("John")
                .lastName("Thompson")
                .build()
        );

        webTestClient.put()
                .uri(VendorController.BASE_URL + "abcdefg")
                .body(mono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

}