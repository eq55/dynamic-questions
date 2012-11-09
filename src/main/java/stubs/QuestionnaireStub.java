package stubs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import question.Question;
import question.QuestionGrouping;
import question.QuestionGroupingRepository;
import question.QuestionOption;
import question.QuestionOptionRepository;
import question.QuestionPage;
import question.QuestionPageRepository;
import question.QuestionRepository;
import question.QuestionText;
import question.Questionnaire;
import question.QuestionnaireRepository;

public class QuestionnaireStub implements InitializingBean {

	private QuestionOption questionOption1;
	private QuestionOption questionOption2;
	private QuestionText questionText1;
	private QuestionText questionText2;
	private QuestionText questionText3;
	private QuestionText questionText4;
	private QuestionText questionText5;
	private QuestionText questionText6;
	private QuestionText questionText7;
	private QuestionText questionText8;
	private QuestionText questionText9;
	private QuestionText questionText10;
	private QuestionText questionText11;
	private QuestionText questionText12;
	private Question question1;
	private Question question2;
	private Question question3;
	private Question question4;
	private Question question5;
	private Question question6;
	private Question question7;
	private Question question8;
	private Question question9;
	private Question question10;
	private Question question11;
	private Question question12;
	private QuestionGrouping questionGrouping1;
	private QuestionGrouping questionGrouping2;
	private QuestionGrouping questionGrouping3;
	private QuestionPage questionPage1;
	private QuestionPage questionPage2;
	private QuestionPage questionPage3;
	private Questionnaire questionnaire1;
	
	
	private QuestionnaireStub(){};
	
	@Autowired
	private QuestionnaireStub(QuestionnaireRepository questionnaireRepository, QuestionRepository questionRepository, QuestionGroupingRepository questionGroupingRepository, QuestionPageRepository questionPageRepository, QuestionOptionRepository questionOptionRepository) {
		
		this.questionOption1 = new QuestionOption();
		this.questionOption1.setText("Male");
		questionOptionRepository.save(this.questionOption1);
		
		this.questionOption2 = new QuestionOption();
		this.questionOption2.setText("Female");
		questionOptionRepository.save(this.questionOption2);
		
		List<QuestionOption> questionOptions1 = new ArrayList<QuestionOption>();
		questionOptions1.add(this.questionOption1);
		questionOptions1.add(this.questionOption2);
		
		//Setup some question text
		questionText1 = new QuestionText();
		questionText1.setLabelText("Title");
		
		questionText2 = new QuestionText();
		questionText2.setLabelText("First name");
		
		questionText3 = new QuestionText();
		questionText3.setLabelText("Surname");
		
		questionText4 = new QuestionText();
		questionText4.setLabelText("Gender");
		
		questionText5 = new QuestionText();
		questionText5.setLabelText("Email address");
		
		questionText6 = new QuestionText();
		questionText6.setLabelText("Occupation");
		
		questionText7 = new QuestionText();
		questionText7.setLabelText("Vehcile Make");
		
		questionText8 = new QuestionText();
		questionText8.setLabelText("Vehicle Model");
		
		questionText9 = new QuestionText();
		questionText9.setLabelText("Vehicle fuel type");
		
		questionText10 = new QuestionText();
		questionText10.setLabelText("What do you use your vehicle for");
		
		questionText11 = new QuestionText();
		questionText11.setLabelText("What is the value of your vehicle");
		
		questionText12 = new QuestionText();
		questionText12.setLabelText("Where is your vehicle kept overnight");
		
		
		//Setup questions
		question1 = new Question();
		question1.setRenderType("string");
		question1.setModel("question 1 model string");
		question1.setText(questionText1);
		question1 = questionRepository.save(question1);
		
		question2 = new Question();
		question2.setRenderType("string");
		question2.setModel("question 2 model string");
		question2.setText(questionText2);
		question2 = questionRepository.save(question2);
		
		question3 = new Question();
		question3.setRenderType("string");
		question3.setModel("question 3 model string");
		question3.setText(questionText3);
		question3 = questionRepository.save(question3);
		
		question4 = new Question();
		question4.setRenderType("radio");
		question4.setModel("question 4 model string");
		question4.setText(questionText4);
		question4.setOptions(questionOptions1);
		question4 = questionRepository.save(question4);
		
		question5 = new Question();
		question5.setRenderType("string");
		question5.setModel("question 5 model string");
		question5.setText(questionText5);
		question5 = questionRepository.save(question5);
		
		question6 = new Question();
		question6.setRenderType("string");
		question6.setModel("question 6 model string");
		question6.setText(questionText6);
		question6 = questionRepository.save(question6);
		
		question7 = new Question();
		question7.setRenderType("string");
		question7.setModel("question 7 model string");
		question7.setText(questionText7);
		question7 = questionRepository.save(question7);
		
		question8 = new Question();
		question8.setRenderType("string");
		question8.setModel("question 8 model string");
		question8.setText(questionText8);
		question8 = questionRepository.save(question8);
		
		question9 = new Question();
		question9.setRenderType("string");
		question9.setModel("question 9 model string");
		question9.setText(questionText9);
		question9 = questionRepository.save(question9);
		
		question10 = new Question();
		question10.setRenderType("string");
		question10.setModel("question 10 model string");
		question10.setText(questionText10);
		question10 = questionRepository.save(question10);
		
		question11 = new Question();
		question11.setRenderType("string");
		question11.setModel("question 11 model string");
		question11.setText(questionText11);
		question11 = questionRepository.save(question11);
		
		question12 = new Question();
		question12.setRenderType("string");
		question12.setModel("question 12 model string");
		question12.setText(questionText12);
		question12 = questionRepository.save(question12);
		
		//Setup some lists of questions
		List<Question> questionList1 = new ArrayList<Question>();
		questionList1.add(question1);
		questionList1.add(question2);
		questionList1.add(question3);
		questionList1.add(question4);
		questionList1.add(question5);
		questionList1.add(question6);
		
		List<Question> questionList2 = new ArrayList<Question>();
		questionList2.add(question7);
		questionList2.add(question8);
		questionList2.add(question9);
				
		List<Question> questionList3 = new ArrayList<Question>();
		questionList3.add(question10);
		questionList3.add(question11);
		questionList3.add(question12);
		
		//Setup 3 question groupings
		questionGrouping1 = new QuestionGrouping();
		questionGrouping1.setOrderBy(1);
		questionGrouping1.setName("Group1");
		questionGrouping1.setQuestions(questionList1);
		questionGrouping1 = questionGroupingRepository.save(questionGrouping1);
		
		questionGrouping2 = new QuestionGrouping();
		questionGrouping2.setOrderBy(2);
		questionGrouping2.setName("Group2");
		questionGrouping2.setQuestions(questionList2);
		questionGrouping2 = questionGroupingRepository.save(questionGrouping2);
		
		questionGrouping3 = new QuestionGrouping();
		questionGrouping3.setOrderBy(3);
		questionGrouping3.setName("Group3");
		questionGrouping3.setQuestions(questionList3);
		questionGrouping3 = questionGroupingRepository.save(questionGrouping3);
				
		List<QuestionGrouping> questionGroupingslist1 = new ArrayList<QuestionGrouping>();
		questionGroupingslist1.add(questionGrouping1);
		
		List<QuestionGrouping> questionGroupingslist2 = new ArrayList<QuestionGrouping>();
		questionGroupingslist2.add(questionGrouping2);
		
		List<QuestionGrouping> questionGroupingslist3 = new ArrayList<QuestionGrouping>();
		questionGroupingslist3.add(questionGrouping3);
		
		this.questionPage1 = new QuestionPage();
		this.questionPage1.setQuestionGroups(questionGroupingslist1);
		this.questionPage1.setName("About You");
		questionPageRepository.save(this.questionPage1);
		
		this.questionPage2 = new QuestionPage();
		this.questionPage2.setQuestionGroups(questionGroupingslist2);
		this.questionPage2.setName("Find Your Car");
		questionPageRepository.save(this.questionPage2);
		
		this.questionPage3 = new QuestionPage();
		this.questionPage3.setQuestionGroups(questionGroupingslist3);
		this.questionPage3.setName("About Your Car");
		questionPageRepository.save(this.questionPage3);
		
		List<QuestionPage> questionPages = new ArrayList<QuestionPage>();
		questionPages.add(this.questionPage1);
		questionPages.add(this.questionPage2);
		questionPages.add(this.questionPage3);
		
		questionnaire1 = new Questionnaire();
		questionnaire1.setBrand("lv");
		questionnaire1.setDevice("device1");
		questionnaire1.setProduct("product1");
		questionnaire1.setVersion("version1");		
		questionnaire1.setQuestionPages(questionPages);
		questionnaire1 = questionnaireRepository.save(questionnaire1);
		
//		List<QuestionGrouping> questionGroupingslist2 = new ArrayList<QuestionGrouping>();
//		questionGroupingslist2.add(questionGrouping2);
//		questionGroupingslist2.add(questionGrouping1);
//		questionGroupingslist2.add(questionGrouping3);
		
//		questionnaire2 = new Questionnaire();
//		questionnaire2.setBrand("nw");
//		questionnaire2.setDevice("device1");
//		questionnaire2.setProduct("product1");
//		questionnaire2.setVersion("version1");		
//		questionnaire2.setQuestionGroups(questionGroupingslist2);
//		questionnaireRepository.save(questionnaire2);
		
	}
		
//	@PostConstruct
//	public void init() {
//		questionnaireRepository.save(questionnaire1);
//	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public QuestionText getQuestionText1() {
		return questionText1;
	}
	public void setQuestionText1(QuestionText questionText1) {
		this.questionText1 = questionText1;
	}
	public QuestionText getQuestionText2() {
		return questionText2;
	}
	public void setQuestionText2(QuestionText questionText2) {
		this.questionText2 = questionText2;
	}
	public QuestionText getQuestionText3() {
		return questionText3;
	}
	public void setQuestionText3(QuestionText questionText3) {
		this.questionText3 = questionText3;
	}
	public QuestionText getQuestionText4() {
		return questionText4;
	}
	public void setQuestionText4(QuestionText questionText4) {
		this.questionText4 = questionText4;
	}
	public QuestionText getQuestionText5() {
		return questionText5;
	}
	public void setQuestionText5(QuestionText questionText5) {
		this.questionText5 = questionText5;
	}
	public QuestionText getQuestionText6() {
		return questionText6;
	}
	public void setQuestionText6(QuestionText questionText6) {
		this.questionText6 = questionText6;
	}
	public Question getQuestion1() {
		return question1;
	}
	public void setQuestion1(Question question1) {
		this.question1 = question1;
	}
	public Question getQuestion2() {
		return question2;
	}
	public void setQuestion2(Question question2) {
		this.question2 = question2;
	}
	public Question getQuestion3() {
		return question3;
	}
	public void setQuestion3(Question question3) {
		this.question3 = question3;
	}
	public Question getQuestion4() {
		return question4;
	}
	public void setQuestion4(Question question4) {
		this.question4 = question4;
	}
	public Question getQuestion5() {
		return question5;
	}
	public void setQuestion5(Question question5) {
		this.question5 = question5;
	}
	public Question getQuestion6() {
		return question6;
	}
	public void setQuestion6(Question question6) {
		this.question6 = question6;
	}
	public QuestionGrouping getQuestionGrouping1() {
		return questionGrouping1;
	}
	public void setQuestionGrouping1(QuestionGrouping questionGrouping1) {
		this.questionGrouping1 = questionGrouping1;
	}
	public QuestionGrouping getQuestionGrouping2() {
		return questionGrouping2;
	}
	public void setQuestionGrouping2(QuestionGrouping questionGrouping2) {
		this.questionGrouping2 = questionGrouping2;
	}
	public QuestionGrouping getQuestionGrouping3() {
		return questionGrouping3;
	}
	public void setQuestionGrouping3(QuestionGrouping questionGrouping3) {
		this.questionGrouping3 = questionGrouping3;
	}
	public Questionnaire getQuestionnaire1() {
		return questionnaire1;
	}
	public void setQuestionnaire1(Questionnaire questionnaire1) {
		this.questionnaire1 = questionnaire1;
	}
	
	
	
}
