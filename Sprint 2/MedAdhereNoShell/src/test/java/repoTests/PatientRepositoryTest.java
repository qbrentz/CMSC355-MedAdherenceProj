/*package repoTests;

import com.example.medadherence.repositories.PatientRepository;
import com.example.medadherence.models.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void repositoryIsNotNull() {
        assertNotNull(patientRepository);
    }

    @Test
    void saveAndFindPatient() {
        Patient patient = new Patient("Test Patient", "test@domain.com");
        patientRepository.save(patient);
        assertTrue(patientRepository.findAll().contains(patient));
    }
}
*/