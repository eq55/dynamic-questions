package questions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import static junit.framework.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import question.Question;
import question.QuestionGrouping;
import question.QuestionGroupingRepository;
import question.QuestionRepository;
import question.QuestionText;
import question.QuestionTextRepository;
import question.Questionnaire;
import question.QuestionnaireRepository;


@ContextConfiguration(locations = {"classpath:/development-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"dev"})
public class QuestionTests {
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	QuestionGroupingRepository questionGroupingRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	QuestionTextRepository questionTextRepository;
	
	@Autowired
	QuestionnaireRepository questionnaireRepository;
	
	private QuestionText questionText1;
	private QuestionText questionText2;
	private QuestionText questionText3;
	private QuestionText questionText4;
	private QuestionText questionText5;
	private QuestionText questionText6;
	private Question question1;
	private Question question2;
	private Question question3;
	private Question question4;
	private Question question5;
	private Question question6;
	private QuestionGrouping questionGrouping1;
	private QuestionGrouping questionGrouping2;
	private QuestionGrouping questionGrouping3;
	private Questionnaire questionnaire1;
	
	@Before
	public void init() {
		//Setup some question text
		questionText1 = new QuestionText();
		questionText1.setLabelText("Some question text 1");
		
		questionText2 = new QuestionText();
		questionText2.setLabelText("Some question text 2");
		
		questionText3 = new QuestionText();
		questionText3.setLabelText("Some question text 3");
		
		questionText4 = new QuestionText();
		questionText4.setLabelText("Some question text 4");
		
		questionText5 = new QuestionText();
		questionText5.setLabelText("Some question text 5");
		
		questionText6 = new QuestionText();
		questionText6.setLabelText("Some question text 6");
		
		//Setup questions
		question1 = new Question();
		question1.setDataType("string");
		question1.setModel("question 1 model string");
		question1.setText(questionText1);
		
		question2 = new Question();
		question2.setDataType("string");
		question2.setModel("question 2 model string");
		question2.setText(questionText2);
		
		question3 = new Question();
		question3.setDataType("string");
		question3.setModel("question 3 model string");
		question3.setText(questionText3);
		
		question4 = new Question();
		question4.setDataType("string");
		question4.setModel("question 4 model string");
		question4.setText(questionText4);
		
		question5 = new Question();
		question5.setDataType("string");
		question5.setModel("question 5 model string");
		question5.setText(questionText5);
		
		question6 = new Question();
		question6.setDataType("string");
		question6.setModel("question 6 model string");
		question6.setText(questionText6);
		
		//Setup some lists of questions
		List<Question> questionList1 = new ArrayList<Question>();
		questionList1.add(question1);
		questionList1.add(question2);
		questionList1.add(question3);
		
		List<Question> questionList2 = new ArrayList<Question>();
		questionList2.add(question1);
		questionList2.add(question2);
		questionList2.add(question4);
		questionList2.add(question5);
		
		List<Question> questionList3 = new ArrayList<Question>();
		questionList3.add(question1);
		questionList3.add(question2);
		questionList3.add(question3);
		questionList3.add(question5);
		questionList3.add(question6);
		
		//Setup 3 question groupings
		questionGrouping1 = new QuestionGrouping();
		questionGrouping1.setOrderBy(1);
		questionGrouping1.setName("Group1");
		questionGrouping1.setQuestions(questionList1);
		
		questionGrouping2 = new QuestionGrouping();
		questionGrouping2.setOrderBy(2);
		questionGrouping2.setName("Group2");
		questionGrouping2.setQuestions(questionList2);
		
		questionGrouping3 = new QuestionGrouping();
		questionGrouping3.setOrderBy(3);
		questionGrouping3.setName("Group3");
		questionGrouping3.setQuestions(questionList3);
				
		List<QuestionGrouping> questionGroupingslist = new ArrayList<QuestionGrouping>();
		questionGroupingslist.add(questionGrouping1);
		questionGroupingslist.add(questionGrouping2);
		questionGroupingslist.add(questionGrouping3);
		
		questionnaire1 = new Questionnaire();
		questionnaire1.setQuestionGroups(questionGroupingslist);
		
	}
	
	@After
	public void tearDown() {
	    try {
	        clearDatabase();
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
	}	
	
	@Test
	public void testQuestionAndQuestionText() {
		Question savedQ = questionRepository.save(question1);
		assertEquals("Some question text 1", savedQ.getText().getLabelText());
	}
	
	@Test
	public void testQuestionAndQuestionGrouping() {
		QuestionGrouping savedQuestionGroup = questionGroupingRepository.save(questionGrouping1);
		assertEquals("question 1 model string", savedQuestionGroup.getQuestions().get(0).getModel());
		assertEquals("question 2 model string", savedQuestionGroup.getQuestions().get(1).getModel());
		assertEquals("question 3 model string", savedQuestionGroup.getQuestions().get(2).getModel());
	}
	
	@Test
	public void testQuestionnaireAndQuestionGrouping() {
		Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire1);
		assertEquals("Group1", savedQuestionnaire.getQuestionGroups().get(0).getName());
	}

	public void clearDatabase() throws Exception {
		  DataSource ds = (DataSource) context.getBean("dataSource");
		  Connection connection = null;
		  try {
		    connection = ds.getConnection();
		    try {
		      Statement stmt = connection.createStatement();
		      try {
		        stmt.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
		        connection.commit();
		      } finally {
		        stmt.close();
		      }
		    } catch (SQLException e) {
		        connection.rollback();
		        throw new Exception(e);
		    }
		    } catch (SQLException e) {
		        throw new Exception(e);
		    } finally {
		        if (connection != null) {
		            connection.close();
		        }
		    }
		}	
}
