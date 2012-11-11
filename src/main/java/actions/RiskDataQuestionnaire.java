package actions;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.execution.RequestContext;

import service.QuestionnaireGateway;
import answers.Answers;

public class RiskDataQuestionnaire extends FormAction {
	
	@Autowired
	QuestionnaireGateway questionnaireGateway;
	
	public Event getSomeQuestions (RequestContext ctx) throws Exception {
		Answers answers = (Answers) ctx.getFlowScope().get("answers");
		FlowExecutionKey key = ctx.getFlowExecutionContext().getKey();
		System.out.println("ANSWERS(get):" + answers);
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("brand", "lv");
		args.put("product", "product1");
		args.put("device", "device1");
		args.put("version", "version1");
		args.put("answers", answers);
		args.put("key", key);
		
		Map<String, Object> questionsAndPage = questionnaireGateway.findByBrandAndProductAndDeviceAndVersion(args);
			
		ctx.getViewScope().put("questions", questionsAndPage.get("questions"));
		ctx.getRequestScope().put("page", questionsAndPage.get("page"));
		return success();
	}
	
}
