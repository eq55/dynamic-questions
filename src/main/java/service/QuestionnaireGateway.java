package service;

import java.util.Map;

import org.springframework.integration.annotation.Gateway;

public interface QuestionnaireGateway {

	@Gateway
	Map<String, Object> findByBrandAndProductAndDeviceAndVersion(Map<String, Object> args); //String brand, String product, String device, String version
	
}
