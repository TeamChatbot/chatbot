package de.morpheus.chatbot.aiml.io;

import java.util.List;

public interface DAOInterface<InputType> {
	public int store(InputType input) throws Exception;
	public InputType restoreByID(final int id) throws Exception;
	public List<InputType> restoreAll() throws Exception;
	public int getStoredCount() throws Exception;
	public void clearStorage() throws Exception;
}
