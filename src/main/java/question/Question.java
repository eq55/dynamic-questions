package question;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="questions")
public class Question implements Serializable {
	
	private static final long serialVersionUID = -5913309448534395602L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@OneToOne(cascade={CascadeType.ALL})
	private QuestionText text;
	
	@Column(nullable=false)
	private String model;
	
	@Column(nullable=false)
	private String renderType;
	
	@OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(
			name="question_option_to_question",
			joinColumns={@JoinColumn(name="question_id")},
			inverseJoinColumns={@JoinColumn(name="question_option_id")})
	private List<QuestionOption> options;
	
	public Question() {}
	
	public QuestionText getText() {
		return text;
	}

	public void setText(QuestionText text) {
		this.text = text;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRenderType() {
		return renderType;
	}

	public void setRenderType(String renderType) {
		this.renderType = renderType;
	}

	public List<QuestionOption> getOptions() {
		return options;
	}

	public void setOptions(List<QuestionOption> options) {
		this.options = options;
	}

	
}
