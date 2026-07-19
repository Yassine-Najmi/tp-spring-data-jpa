# Compte rendu - Spring Data JPA

Yassine Najmi

## Entité Product et configuration H2

### Entité Product

L'entité `Product` est une classe JPA annotée `@Entity`. L'identifiant est généré automatiquement avec la stratégie `IDENTITY`. Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`) évite d'écrire à la main les getters, setters et constructeurs.

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;
}
```

### Configuration H2

La base H2 est en mémoire. La console H2 est activée pour visualiser les tables. `show-sql=true` affiche les requêtes SQL générées. L'application écoute sur le port 8085.

```properties
server.port=8085

spring.datasource.url=jdbc:h2:mem:products-db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

### ProductRepository

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

En étendant `JpaRepository`, Spring Data JPA génère automatiquement une implémentation au démarrage. On dispose ainsi des méthodes CRUD classiques (`save`, `findById`, `findAll`, `deleteById`, etc.) sans écrire de code d'accès aux données.

## Opérations de gestion des produits

Les opérations sont testées au démarrage via un bean `CommandLineRunner` dans la classe principale. Le repository expose aussi des méthodes de recherche.

### Méthodes de recherche dans ProductRepository

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContains(String kw);

    List<Product> findByPriceGreaterThan(double p);

    @Query("select p from Product p where p.name like :x")
    List<Product> search(@Param("x") String kw);
}
```

Spring Data dérive le SQL à partir du nom de la méthode. Par exemple, `findByNameContains` produit un `WHERE name LIKE ...`, et `findByPriceGreaterThan` un `WHERE price > ...`. La méthode `search` utilise une requête JPQL explicite avec `@Query` et `@Param`.

Exemples de SQL générés par Hibernate lors des recherches :

```sql
select ... from product p1_0 where p1_0.name like ? escape '\'
select ... from product p1_0 where p1_0.price>?
select ... from product p1_0 where p1_0.name like ? escape ''
```

### CommandLineRunner

```java
@Bean
CommandLineRunner start(ProductRepository productRepository) {
    return args -> {
        productRepository.save(Product.builder().name("Computer").price(4300).quantity(55).build());
        productRepository.save(Product.builder().name("Printer").price(1200).quantity(10).build());
        productRepository.save(Product.builder().name("Smart Phone").price(3200).quantity(28).build());
        productRepository.save(Product.builder().name("Keyboard").price(150).quantity(100).build());

        productRepository.findAll().forEach(System.out::println);
        System.out.println(productRepository.findById(1L).orElse(null));

        productRepository.findByNameContains("er").forEach(System.out::println);
        productRepository.findByPriceGreaterThan(3000).forEach(System.out::println);
        productRepository.search("%er%").forEach(System.out::println);

        Product toUpdate = productRepository.findById(1L).orElseThrow();
        toUpdate.setPrice(4500);
        productRepository.save(toUpdate);

        productRepository.deleteById(4L);
        productRepository.findAll().forEach(System.out::println);
    };
}
```

Au lancement, on observe bien les `insert`, `select`, `update` et `delete` dans la console. Après suppression du produit d'id 4 (Keyboard), la liste ne contient plus que trois produits, et le prix du Computer est passé à 4500.
