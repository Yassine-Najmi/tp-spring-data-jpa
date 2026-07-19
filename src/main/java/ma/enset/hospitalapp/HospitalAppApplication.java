package ma.enset.hospitalapp;

import ma.enset.hospitalapp.entities.Consultation;
import ma.enset.hospitalapp.entities.Medecin;
import ma.enset.hospitalapp.entities.Patient;
import ma.enset.hospitalapp.entities.Product;
import ma.enset.hospitalapp.entities.RendezVous;
import ma.enset.hospitalapp.entities.StatusRDV;
import ma.enset.hospitalapp.entities.Role;
import ma.enset.hospitalapp.entities.User;
import ma.enset.hospitalapp.repositories.ProductRepository;
import ma.enset.hospitalapp.service.IHospitalService;
import ma.enset.hospitalapp.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
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
            if (productRepository.existsById(4L)) {
                productRepository.deleteById(4L);
            }
            productRepository.findAll().forEach(p -> {
                System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice() + " | " + p.getQuantity());
            });
        };
    }

    @Bean
    CommandLineRunner hospitalDemo(IHospitalService hospitalService) {
        return args -> {
            System.out.println("===== Hôpital : patients =====");
            Patient p1 = hospitalService.savePatient(
                    Patient.builder().nom("Hassan").dateNaissance(new Date()).malade(false).build());
            Patient p2 = hospitalService.savePatient(
                    Patient.builder().nom("Yassine").dateNaissance(new Date()).malade(true).build());
            System.out.println("Patients : " + p1.getId() + "-" + p1.getNom() + ", " + p2.getId() + "-" + p2.getNom());

            System.out.println("===== Hôpital : médecins =====");
            Medecin m1 = hospitalService.saveMedecin(
                    Medecin.builder().nom("Dr. Amina").email("amina@mail.com").specialite("Cardio").build());
            Medecin m2 = hospitalService.saveMedecin(
                    Medecin.builder().nom("Dr. Karim").email("karim@mail.com").specialite("Dentiste").build());
            System.out.println("Médecins : " + m1.getId() + "-" + m1.getNom() + ", " + m2.getId() + "-" + m2.getNom());

            System.out.println("===== Hôpital : rendez-vous =====");
            RendezVous rdv = hospitalService.saveRDV(
                    RendezVous.builder()
                            .date(new Date())
                            .status(StatusRDV.PENDING)
                            .patient(p1)
                            .medecin(m1)
                            .build());
            System.out.println("RDV id=" + rdv.getId() + " patient=" + rdv.getPatient().getNom()
                    + " medecin=" + rdv.getMedecin().getNom() + " status=" + rdv.getStatus());

            System.out.println("===== Hôpital : consultation =====");
            Consultation consultation = hospitalService.saveConsultation(
                    Consultation.builder()
                            .dateConsultation(new Date())
                            .rapport("Consultation cardio : bilans normaux")
                            .rendezVous(rdv)
                            .build());
            System.out.println("Consultation id=" + consultation.getId()
                    + " rdv=" + consultation.getRendezVous().getId()
                    + " rapport=" + consultation.getRapport());
        };
    }

    @Bean
    CommandLineRunner usersRolesDemo(IUserService userService) {
        return args -> {
            System.out.println("===== Users et Roles =====");
            userService.addNewUser(User.builder().username("user1").password("1234").build());
            userService.addNewUser(User.builder().username("user2").password("1234").build());
            userService.addNewUser(User.builder().username("admin").password("1234").build());

            userService.addNewRole(Role.builder().roleName("STUDENT").build());
            userService.addNewRole(Role.builder().roleName("USER").build());
            userService.addNewRole(Role.builder().roleName("ADMIN").build());

            userService.addRoleToUser("user1", "STUDENT");
            userService.addRoleToUser("user1", "USER");
            userService.addRoleToUser("user2", "USER");
            userService.addRoleToUser("admin", "USER");
            userService.addRoleToUser("admin", "ADMIN");

            User admin = userService.findUserByUserName("admin");
            System.out.println("admin roles :");
            admin.getRoles().forEach(r -> System.out.println(" - " + r.getRoleName()));
        };
    }
}
