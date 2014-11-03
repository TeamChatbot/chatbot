package de.morpheus.chatbot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModelChatbotBrain extends LinkedHashMap<String, Map<String, List<Content>>> {

	private static final long serialVersionUID = 1L;
	
	public void add(String category, String key, String value, Boolean multiple){
		Map<String, List<Content>> lst = new HashMap<String, List<Content>>();
		Content content = new Content(value.trim());
		if(!super.containsKey(category)){
			super.put(category, lst);
		}
		if(!super.get(category).containsKey(key)){
			super.get(category).put(key, new ArrayList<Content>());
		}
		if(!hasValue(category, key, content.getValue())){
			if(multiple || super.get(category).get(key).size() == 0){
				super.get(category).get(key).add(content);
			}else{
				super.get(category).get(key).set(0,content);	
			}
		}
		/*if(super.get(category).get(key).size() > 0){
			super.get(category).get(key).set(0,content);
		}else{
			super.get(category).get(key).add(0,content);
		}*/
	}
	
	public Boolean hasValue(String category, String topic, String text){
		for(Content value : super.get(category).get(topic)){
			if(value.getValue().equals(text)){
				return true;
			}
		}
		return false;
	}
	
	public List<Content> get(String category, String key){
		if(!super.containsKey(category)){
			Map<String, List<Content>> lst = new HashMap<String, List<Content>>();
			super.put(category, lst);
		}
		return super.get(category).get(key);
	}
	
	public Content getContentByCategoryAndKeyAndValue(String category, String key, String value){
		List<Content> lstContent = this.get(category, key);
		for(Content content : lstContent){
			if(content.getValue().equals(value)){
				return content;
			}
		}
		return null;
	}

	public boolean containsKeyByCategory(String category, Object key) {
		if(this.get(category).containsKey(key)){
			return true;
		}
		return false;
	}

	public String getCategoryByIndex(int index){
		return (String) Brain.getInstance().keySet().toArray()[index];
	}
}
