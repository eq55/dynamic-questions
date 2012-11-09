package answers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Answers implements Serializable {
	private static final long serialVersionUID = -5410693825269016621L;
	
	private Map<String, List<String>> pageAnswers;
		
	public Answers() {
		this.pageAnswers = new TreeMap<String, List<String>>();
	}
	
	public void addNewAnswerList(String pageName) {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		
		
		pageAnswers.put(pageName, list);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, List<String>> entry : pageAnswers.entrySet()) {
			sb.append("Page: " + entry.getKey());
			for (String item : entry.getValue()) {
				sb.append("Q: " + item);	
			}
		}
		return sb.toString();
	}

	public Map<String, List<String>> getPageAnswers() {
		return pageAnswers;
	}

	public void setPageAnswers(Map<String, List<String>> pageAnswers) {
		this.pageAnswers = pageAnswers;
	}
	
//	public List<String> getAnswerList() {
//		return answerList;
//	}
//
//	public void setAnswerList(List<String> answerList) {
//		this.answerList = answerList;
//	}
	
}
