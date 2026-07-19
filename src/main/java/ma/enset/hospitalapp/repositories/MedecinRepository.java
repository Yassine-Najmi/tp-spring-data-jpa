package ma.enset.hospitalapp.repositories;

import ma.enset.hospitalapp.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
}
