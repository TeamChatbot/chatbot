package de.morpheus.chatbot.model.brain;

import java.util.LinkedHashMap;
import java.util.List;

import de.morpheus.chatbot.brain.io.datasource.DataSource;
import de.morpheus.chatbot.exception.ExceptionInvalidDataModel;

public class ModelChatbotBrain extends LinkedHashMap<String, ModelChatbotBrainTopic> {

	public static final String BOT_NAME = "Andre";
	
	private static ModelChatbotBrain modelChatbotBrain = new ModelChatbotBrain();
	
	private DataSource datasource;
	
	private static final long serialVersionUID = 1L;
	
	private ModelChatbotBrain(){}
	
	public static ModelChatbotBrain getInstance(){
		return modelChatbotBrain;
	}
	
	public ModelChatbotBrain getEmptyModel(){
		return new ModelChatbotBrain();
	}
	
	public void init(DataSource datasource){
		this.datasource = datasource;
		ModelChatbotBrain.getInstance().putAll(datasource.readAll());
	}
	
	public void add(String category, String key, String value, Boolean multiple){
		ModelChatbotBrainTopic lst = new ModelChatbotBrainTopic();
		String content = new String(value.trim());
		if(!super.containsKey(category)){
			super.put(category, lst);
		}
		super.get(category).add(key, content, multiple);
	}
	
	
	public List<String> get(String category, String key){
		if(!super.containsKey(category)){
			ModelChatbotBrainTopic lst = new ModelChatbotBrainTopic();
			super.put(category, lst);
		}
		return super.get(category).get(key);
	}
	
	public String getContentByCategoryAndKeyAndValue(String category, String key, String value){
		List<String> lstContent = this.get(category, key);
		if(lstContent != null){
			for(String content : lstContent){
				if(content.equals(value)){
					return content;
				}
			}
		}
		return null;
	}

	public boolean containsKeyByCategory(String category, Object key) {
		if(this.containsKey(category)){
			if(this.get(category).containsKey(key)){
				return true;
			}
		}
		return false;
	}

	public String getCategoryByIndex(int index){
		return (String) this.keySet().toArray()[index];
	}

	public DataSource getDatasource() {
		if(this.datasource == null){
			throw new ExceptionInvalidDataModel("das datenmodell wurde noch nicht initialisiert");
		}
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
}
