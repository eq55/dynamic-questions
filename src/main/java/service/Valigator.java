package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.webflow.execution.FlowExecutionKey;

import question.Question;
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
		FlowExecutionKey key = (FlowExecutionKey) args.get("key");
		
		String currentPage = "";
		Answers answers = (Answers) args.get("answers");
		if (answers != null && key != null && answers.lookupPageByFlowKey(key) != null) {
			currentPage = answers.lookupPageByFlowKey(key);
		}
		
		Questionnaire questionnaire = questionnaireRepository.findByBrandAndProductAndDeviceAndVersion(brand, product, device, version);
		//Work out what question page to return...
		if (answers == null) {
			answers = new Answers();
		}
		if (currentPage.equals("")) {
			if (answers.getPageAnswers() == null || answers.getPageAnswers().isEmpty()) {
				answers.addNewAnswerList(questionnaire.getQuestionPages().get(0).getName());
				answers.storeKeyAndDynamicPage(key, questionnaire.getQuestionPages().get(0).getName());
				return setReturnMap(questionnaire, 0);
			} else {
				answers.addNewAnswerList(questionnaire.getQuestionPages().get(1).getName());
				answers.storeKeyAndDynamicPage(key, questionnaire.getQuestionPages().get(1).getName());
				return setReturnMap(questionnaire, 1);
			}			
		} else {
			   Map<String, Object> returnMap = new HashMap<String, Object>();
			   List<QuestionPage> pages = questionnaire.getQuestionPages();
			   List<Question> questions = null;
			   for (QuestionPage page: pages) {
				   if (page.getName().equals(currentPage)) {
					   questions = page.getQuestionGroups().get(0).getQuestions();
				   }
			   }
			   returnMap.put("questions", questions);
			   returnMap.put("page", currentPage);
			   return returnMap;
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
