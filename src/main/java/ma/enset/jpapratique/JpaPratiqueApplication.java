package ma.enset.jpapratique;

import ma.enset.jpapratique.entities.Patient;
import ma.enset.jpapratique.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaPratiqueApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaPratiqueApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 50; i++) {
            patientRepository.save(new Patient(null,"Zouhir", new Date(),Math.random()>0.5?true:false,(int)(Math.random()*100)));
        }


        Page<Patient> patients=patientRepository.findAll(PageRequest.of(0,10));
        System.out.println("Total pages: "+patients.getTotalPages());
        System.out.println("Total elements: "+patients.getTotalElements());
        System.out.println("Nombre de la page: "+patients.getNumber());
        List<Patient> content=patients.getContent();
        Page<Patient> byMalade=patientRepository.findByMalade(true,PageRequest.of(0,4));
        List<Patient> byMaladeAndScore=patientRepository.findByMaladeAndScoreLessThan(true,25);
        List<Patient> chercherPatients2=patientRepository.chercherPatient2("Zouhir",9);
        for (Patient p : chercherPatients2){
            System.out.println("=====================================");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.getScore());
            System.out.println(p.isMalade());
        }
        System.out.println("***********************************************");
        Patient patient=patientRepository.findById(3L).orElse(null);
        if (patient != null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
            patient.setScore(310);
            patientRepository.save(patient);
        }
        patientRepository.deleteById(1L);



    }
}
