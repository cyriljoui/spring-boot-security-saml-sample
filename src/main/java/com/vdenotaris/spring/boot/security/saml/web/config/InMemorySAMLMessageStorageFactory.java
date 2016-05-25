package com.vdenotaris.spring.boot.security.saml.web.config;

import org.opensaml.xml.XMLObject;
import org.springframework.security.saml.storage.SAMLMessageStorage;
import org.springframework.security.saml.storage.SAMLMessageStorageFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by popom on 24/05/16.
 */
public class InMemorySAMLMessageStorageFactory implements SAMLMessageStorageFactory, SAMLMessageStorage {

    private Map<String, XMLObject> messageMap = new HashMap<>();

    @Override
    public void storeMessage(String messageId, XMLObject message) {
        messageMap.put(messageId, message);
    }

    @Override
    public XMLObject retrieveMessage(String messageID) {
        return messageMap.get(messageID);
    }

    @Override
    public SAMLMessageStorage getMessageStorage(HttpServletRequest request) {
        return this;
    }
}
