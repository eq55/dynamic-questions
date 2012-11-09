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
@Table(name="question_pages")
public class QuestionPage implements Serializable {
	private static final long serialVersionUID = -7085414125193937911L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	@Column(nullable=false)
	private int orderBy;

	@ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(
		name="question_grouping_to_question_page",
		joinColumns={@JoinColumn(name="page_id")},
		inverseJoinColumns={@JoinColumn(name="group_id")})
	private List<QuestionGrouping> groups;
	
	public QuestionPage(){}
	
	public List<QuestionGrouping> getQuestionGroups() {
		return this.groups;
	}
	
	public void setQuestionGroups(List<QuestionGrouping> groups) {
		this.groups = groups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	

	
}
