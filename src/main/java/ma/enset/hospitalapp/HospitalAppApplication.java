package ma.enset.hospitalapp;

import ma.enset.hospitalapp.entities.Product;
import ma.enset.hospitalapp.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class HospitalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            System.out.println("===== 1. Ajout de produits =====");
            productRepository.save(Product.builder().name("Computer").price(4300).quantity(55).build());
            productRepository.save(Product.builder().name("Printer").price(1200).quantity(10).build());
            productRepository.save(Product.builder().name("Smart Phone").price(3200).quantity(28).build());
            productRepository.save(Product.builder().name("Keyboard").price(150).quantity(100).build());

            System.out.println("===== 2. Liste de tous les produits =====");
            List<Product> products = productRepository.findAll();
            products.forEach(p -> {
                System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice() + " | " + p.getQuantity());
            });

            System.out.println("===== 3. Produit par id =====");
            Product product = productRepository.findById(1L).orElse(null);
            System.out.println(product);

            System.out.println("===== 4. Recherche =====");
            System.out.println("--- findByNameContains('er') ---");
            productRepository.findByNameContains("er").forEach(System.out::println);

            System.out.println("--- findByPriceGreaterThan(3000) ---");
            productRepository.findByPriceGreaterThan(3000).forEach(System.out::println);

            System.out.println("--- search(@Query) avec %er% ---");
            productRepository.search("%er%").forEach(System.out::println);

            System.out.println("===== 5. Mise à jour d'un produit =====");
            Product toUpdate = productRepository.findById(1L).orElseThrow();
            toUpdate.setPrice(4500);
            productRepository.save(toUpdate);
            System.out.println("Après update : " + productRepository.findById(1L).orElse(null));

            System.out.println("===== 6. Suppression puis nouvelle liste =====");
            productRepository.deleteById(4L);
            productRepository.findAll().forEach(p -> {
                System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice() + " | " + p.getQuantity());
            });
        };
    }
}
