package ma.enset.hospitalapp.repositories;

import ma.enset.hospitalapp.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
