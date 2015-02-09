/**
 * ChatbotServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.morpheus.chatbot.service;

public class ChatbotServiceServiceLocator extends org.apache.axis.client.Service implements de.morpheus.chatbot.service.ChatbotServiceService {

    public ChatbotServiceServiceLocator() {
    }


    public ChatbotServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ChatbotServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ChatbotService
    private java.lang.String ChatbotService_address = "http://localhost:8080/chatbot_service_tomcat/services/ChatbotService";

    public java.lang.String getChatbotServiceAddress() {
        return ChatbotService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ChatbotServiceWSDDServiceName = "ChatbotService";

    public java.lang.String getChatbotServiceWSDDServiceName() {
        return ChatbotServiceWSDDServiceName;
    }

    public void setChatbotServiceWSDDServiceName(java.lang.String name) {
        ChatbotServiceWSDDServiceName = name;
    }

    public de.morpheus.chatbot.service.ChatbotService getChatbotService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ChatbotService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getChatbotService(endpoint);
    }

    public de.morpheus.chatbot.service.ChatbotService getChatbotService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            de.morpheus.chatbot.service.ChatbotServiceSoapBindingStub _stub = new de.morpheus.chatbot.service.ChatbotServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getChatbotServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setChatbotServiceEndpointAddress(java.lang.String address) {
        ChatbotService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (de.morpheus.chatbot.service.ChatbotService.class.isAssignableFrom(serviceEndpointInterface)) {
                de.morpheus.chatbot.service.ChatbotServiceSoapBindingStub _stub = new de.morpheus.chatbot.service.ChatbotServiceSoapBindingStub(new java.net.URL(ChatbotService_address), this);
                _stub.setPortName(getChatbotServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ChatbotService".equals(inputPortName)) {
            return getChatbotService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.chatbot.morpheus.de", "ChatbotServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.chatbot.morpheus.de", "ChatbotService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ChatbotService".equals(portName)) {
            setChatbotServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
