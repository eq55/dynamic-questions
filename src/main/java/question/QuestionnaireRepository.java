package question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
	
	Questionnaire findByBrandAndProductAndDevice(String brand, String product, String device);

	Questionnaire findByBrandAndProductAndDeviceAndVersion(String brand, String product, String device, String version);
}
