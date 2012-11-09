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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="questionnaires")
public class Questionnaire implements Serializable {

	private static final long serialVersionUID = 2442105886393163421L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String product;
	
	@Column
	private String version;
	
	@Column
	private String device;
	
	@Column
	private String brand;
	
	@ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(
		name="questionpages_to_questionnaire",
		joinColumns={@JoinColumn(name="questionnaire_id")},
		inverseJoinColumns={@JoinColumn(name="question_page_id")})
	private List<QuestionPage> questionPages;
		
	public Questionnaire(){}
	
	public void setQuestionPages(List<QuestionPage> questionPages) {
		this.questionPages = questionPages;
	}
	
	public List<QuestionPage> getQuestionPages() {
		return this.questionPages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
