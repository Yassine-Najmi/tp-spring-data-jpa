package ma.enset.hospitalapp.service;

import ma.enset.hospitalapp.entities.Consultation;
import ma.enset.hospitalapp.entities.Medecin;
import ma.enset.hospitalapp.entities.Patient;
import ma.enset.hospitalapp.entities.RendezVous;
import ma.enset.hospitalapp.repositories.ConsultationRepository;
import ma.enset.hospitalapp.repositories.MedecinRepository;
import ma.enset.hospitalapp.repositories.PatientRepository;
import ma.enset.hospitalapp.repositories.RendezVousRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HospitalServiceImpl implements IHospitalService {

    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousRepository rendezVousRepository;
    private final ConsultationRepository consultationRepository;

    public HospitalServiceImpl(PatientRepository patientRepository,
                               MedecinRepository medecinRepository,
                               RendezVousRepository rendezVousRepository,
                               ConsultationRepository consultationRepository) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Medecin saveMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public RendezVous saveRDV(RendezVous rendezVous) {
        // Avec GenerationType.IDENTITY, l'id est généré à l'insertion si null
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }
}
