package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;

import question.QuestionPage;
import question.Questionnaire;
import question.QuestionnaireRepository;
import answers.Answers;

public class Valigator {

	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@ServiceActivator
	public Map<String, Object> find(Map<String, Object> args) {  // String brand, String product, String device, String version, List answers
		//Do some validation on answer object
		//Get a questionnaire
		String brand = (String) args.get("brand");
		String product = (String) args.get("product");
		String version = (String) args.get("version");
		String device = (String) args.get("device");
		
		Answers answers = (Answers) args.get("answers");
		
		Questionnaire questionnaire = questionnaireRepository.findByBrandAndProductAndDeviceAndVersion(brand, product, device, version);
		//Work out what question page to return...
		
		if (answers.getPageAnswers().isEmpty()) {
			answers.addNewAnswerList(questionnaire.getQuestionPages().get(0).getName());
			return setReturnMap(questionnaire, 0);
		} else {
			answers.addNewAnswerList(questionnaire.getQuestionPages().get(1).getName());			
			return setReturnMap(questionnaire, 1);
		}
	}
	
   private Map<String, Object> setReturnMap(Questionnaire questionnaire, int pageNumber) {
	   QuestionPage page = questionnaire.getQuestionPages().get(pageNumber);
	   Map<String, Object> returnMap = new HashMap<String, Object>(); 
	   returnMap.put("questions", page.getQuestionGroups().get(0).getQuestions());
	   returnMap.put("page", page.getName());
	   return returnMap;
   }
	
}
