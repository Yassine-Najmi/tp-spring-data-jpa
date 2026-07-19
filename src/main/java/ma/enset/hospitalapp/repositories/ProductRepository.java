package ma.enset.hospitalapp.repositories;

import ma.enset.hospitalapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
