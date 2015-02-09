package de.morpheus.chatbot.service;

public class ChatbotServiceProxy implements de.morpheus.chatbot.service.ChatbotService {
  private String _endpoint = null;
  private de.morpheus.chatbot.service.ChatbotService chatbotService = null;
  
  public ChatbotServiceProxy() {
    _initChatbotServiceProxy();
  }
  
  public ChatbotServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initChatbotServiceProxy();
  }
  
  private void _initChatbotServiceProxy() {
    try {
      chatbotService = (new de.morpheus.chatbot.service.ChatbotServiceServiceLocator()).getChatbotService();
      if (chatbotService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)chatbotService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)chatbotService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (chatbotService != null)
      ((javax.xml.rpc.Stub)chatbotService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public de.morpheus.chatbot.service.ChatbotService getChatbotService() {
    if (chatbotService == null)
      _initChatbotServiceProxy();
    return chatbotService;
  }
  
  public java.lang.String communicate(java.lang.String input) throws java.rmi.RemoteException{
    if (chatbotService == null)
      _initChatbotServiceProxy();
    return chatbotService.communicate(input);
  }
  
  
}