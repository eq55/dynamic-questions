package answers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.webflow.execution.FlowExecutionKey;

public class Answers implements Serializable {
	private static final long serialVersionUID = -5410693825269016621L;
	
	private Map<String, List<String>> pageAnswers;
	private Map<FlowExecutionKey, String> flowKeysToFindDynamicPages;
		
	public Answers() {
		this.pageAnswers = new TreeMap<String, List<String>>();
		this.flowKeysToFindDynamicPages = new HashMap<FlowExecutionKey, String>();
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
	
	public void storeKeyAndDynamicPage(FlowExecutionKey key, String pageName) {
		this.flowKeysToFindDynamicPages.put(key, pageName);
	}
	
	public String lookupPageByFlowKey(FlowExecutionKey key) {
		return this.flowKeysToFindDynamicPages.get(key);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("Ps and Qs");
		for (Map.Entry<String, List<String>> entry : pageAnswers.entrySet()) {
			sb.append("Page: " + entry.getKey());
			for (String item : entry.getValue()) {
				sb.append(" Q: " + item);	
			}
		}
		for (Map.Entry<FlowExecutionKey, String> entry : flowKeysToFindDynamicPages.entrySet()) {
			sb.append(" Key:" + entry.getKey().toString());
			sb.append(" Page: " + entry.getValue());
		}
		return sb.toString();
	}

	public Map<String, List<String>> getPageAnswers() {
		return pageAnswers;
	}

	public void setPageAnswers(Map<String, List<String>> pageAnswers) {
		this.pageAnswers = pageAnswers;
	}
	
	
}
