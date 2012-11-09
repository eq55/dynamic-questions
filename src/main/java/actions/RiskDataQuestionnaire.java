package actions;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import service.QuestionnaireGateway;
import answers.Answers;

public class RiskDataQuestionnaire extends FormAction {
	
	@Autowired
	QuestionnaireGateway questionnaireGateway;
	
	public Event getSomeQuestions (RequestContext ctx) throws Exception {
		Answers answers = (Answers) ctx.getViewScope().get("answers");
		System.out.println("ANSWERS:" + answers);
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("brand", "lv");
		args.put("product", "product1");
		args.put("device", "device1");
		args.put("version", "version1");
		args.put("answers", answers);
		
		Map<String, Object> questionsAndPage = questionnaireGateway.findByBrandAndProductAndDeviceAndVersion(args);
			
		ctx.getViewScope().put("questions", questionsAndPage.get("questions"));
		ctx.getRequestScope().put("page", questionsAndPage.get("page"));
//		ctx.getViewScope().put("answers", answers);
		return success();
	}
	
}
