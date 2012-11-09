package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import question.Question;
import service.QuestionnaireGateway;
import stubs.QuestionnaireStub;
import answers.Answers;

@Controller
@RequestMapping("/{brand}")
public class QuoteController {

	private static Logger LOG = LoggerFactory.getLogger(QuoteController.class);
	
	@Autowired
	QuestionnaireStub questionnaireStub;
	
	@Autowired
	QuestionnaireGateway questionnaireGateway;
	
	@Autowired
	Answers answers;
	
	@RequestMapping("/quote")
	public String renderAboutYourCarLV(@PathVariable String brand, Model model, Answers answers) {
		
		System.out.println("ANSWERS ***:" + answers);
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("brand", brand);
		args.put("product", "product1");
		args.put("device", "device1");
		args.put("version", "version1");
		args.put("answers", answers);
		
		Map<String, Object> questionsAndPage = questionnaireGateway.findByBrandAndProductAndDeviceAndVersion(args);
			
		model.addAttribute("questions", questionsAndPage.get("questions"));
		model.addAttribute("page", questionsAndPage.get("page"));
		model.addAttribute("answers", answers);
		
		return "quote";
	}

}
