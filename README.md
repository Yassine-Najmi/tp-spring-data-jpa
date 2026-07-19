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
