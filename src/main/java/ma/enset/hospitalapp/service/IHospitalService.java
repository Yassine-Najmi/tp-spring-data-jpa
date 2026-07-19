package ma.enset.hospitalapp.service;

import ma.enset.hospitalapp.entities.Consultation;
import ma.enset.hospitalapp.entities.Medecin;
import ma.enset.hospitalapp.entities.Patient;
import ma.enset.hospitalapp.entities.RendezVous;

public interface IHospitalService {
    Patient savePatient(Patient patient);

    Medecin saveMedecin(Medecin medecin);

    RendezVous saveRDV(RendezVous rendezVous);

    Consultation saveConsultation(Consultation consultation);
}
