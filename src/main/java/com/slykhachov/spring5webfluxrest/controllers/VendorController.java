package com.slykhachov.spring5webfluxrest.controllers;

import com.slykhachov.spring5webfluxrest.domain.Vendor;
import com.slykhachov.spring5webfluxrest.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(VendorController.BASE_URL)
@AllArgsConstructor
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors/";

    private final VendorRepository vendorRepository;

    @GetMapping
    public Flux<Vendor> list() {
        return this.vendorRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Vendor> findById(@PathVariable String id) {
        return  this.vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> createFromStream(@RequestBody Publisher<Vendor> categoryStream) {
        return this.vendorRepository.saveAll(categoryStream).then();
    }

    @PutMapping("{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return this.vendorRepository.save(vendor);
    }

}
