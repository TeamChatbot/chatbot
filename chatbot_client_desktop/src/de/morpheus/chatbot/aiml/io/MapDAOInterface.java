package de.morpheus.chatbot.aiml.io;

import java.util.Map;

public interface MapDAOInterface {
	public int store(String key, String value) throws Exception;
	public String restoreByID(final int id) throws Exception;
	public String restoreByKey(final String key) throws Exception;
	public Map<String, String> restoreAll() throws Exception;
	public int getStoredCount() throws Exception;
	public void clearStorage() throws Exception;
	void deleteByKey(String key) throws Exception;
}
