package de.morpheus.chatbot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Content {

	private String value = null;
	private List<String> response = new ArrayList<String>();
	private Date date = new Date();
	
	public Content(){
		
	}
	
	public Content(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<String> getResponse() {
		return response;
	}
	public void setResponse(List<String> response) {
		this.response = response;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void addResponse(String response){
		if(!this.response.contains(response)){
			this.response.add(response);
		}
	}
}
