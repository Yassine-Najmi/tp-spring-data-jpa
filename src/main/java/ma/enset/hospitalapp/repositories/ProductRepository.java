package ma.enset.hospitalapp.repositories;

import ma.enset.hospitalapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContains(String kw);

    List<Product> findByPriceGreaterThan(double p);

    @Query("select p from Product p where p.name like :x")
    List<Product> search(@Param("x") String kw);
}
