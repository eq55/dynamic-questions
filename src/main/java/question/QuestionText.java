package question;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="question_texts")
public class QuestionText implements Serializable {
	private static final long serialVersionUID = 4071823852222600909L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@OneToOne(mappedBy="text")
	private Question question;
	
	@Column(nullable=false)
	private String labelText;
	
	@Column
	private String helpText;
	
	@Column(nullable=false)
	private boolean mandatory;

	@Column
	private String mandatoryText;
	
	@Column
	private String errorText;
	
	@Column
	private String hintText;
	
	@Column
	private String validationRule;
	
	public QuestionText(){}
	
	public String getLabelText() {
		return labelText;
	}
	
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
	
	public String getHelpText() {
		return helpText;
	}
	
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}
	
	public String getMandatoryText() {
		return mandatoryText;
	}
	
	public void setMandatoryText(String mandatoryText) {
		this.mandatoryText = mandatoryText;
	}
	
	public String getErrorText() {
		return errorText;
	}
	
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	
	public String getHintText() {
		return hintText;
	}
	
	public void setHintText(String hintText) {
		this.hintText = hintText;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getValidationRule() {
		return validationRule;
	}

	public void setValidationRule(String validationRule) {
		this.validationRule = validationRule;
	}
	
}
