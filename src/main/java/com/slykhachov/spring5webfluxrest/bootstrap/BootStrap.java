package com.slykhachov.spring5webfluxrest.bootstrap;

import com.slykhachov.spring5webfluxrest.domain.Category;
import com.slykhachov.spring5webfluxrest.domain.Vendor;
import com.slykhachov.spring5webfluxrest.repositories.CategoryRepository;
import com.slykhachov.spring5webfluxrest.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BootStrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;


    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0) {
            this.loadCategoriesData();
        } else {
            System.out.println("Categories already in Data Base: " + categoryRepository.count().block());
        }

        if (vendorRepository.count().block() == 0) {
            this.loadVendorData();
        } else {
            System.out.println("Vendors already in Data Base: " + vendorRepository.count().block());
        }

    }

     private void loadCategoriesData() {
         System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

         categoryRepository.save(Category.builder()
                 .description("Fruits").build()).block();

         categoryRepository.save(Category.builder()
                 .description("Nuts").build()).block();

         categoryRepository.save(Category.builder()
                 .description("Breads").build()).block();

         categoryRepository.save(Category.builder()
                 .description("Meats").build()).block();

         categoryRepository.save(Category.builder()
                 .description("Eggs").build()).block();

         System.out.println("Loaded Categories: " + categoryRepository.count().block());
     }

     private void loadVendorData() {
         vendorRepository.save(Vendor.builder()
                 .firstName("Joe")
                 .lastName("Buck").build()).block();

         vendorRepository.save(Vendor.builder()
                 .firstName("Micheal")
                 .lastName("Weston").build()).block();

         vendorRepository.save(Vendor.builder()
                 .firstName("Jessie")
                 .lastName("Waters").build()).block();

         vendorRepository.save(Vendor.builder()
                 .firstName("Bill")
                 .lastName("Nershi").build()).block();

         vendorRepository.save(Vendor.builder()
                 .firstName("Jimmy")
                 .lastName("Buffett").build()).block();

         System.out.println("Loaded Vendors: " + vendorRepository.count().block());
     }

}
