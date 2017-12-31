/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.inputParser.handyClasses;

import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Vladimir
 */
public class StaxStreamProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    
    private final XMLStreamReader reader;
    
    public StaxStreamProcessor(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }
    
    public XMLStreamReader getReader() {
        return reader;
    }
    
    public boolean doUntil(int stopEvent, String value) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == stopEvent && value.equals(reader.getLocalName())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean startElement(String element, String parent) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (parent != null && event == XMLEvent.END_ELEMENT &&
                  parent.equals(reader.getLocalName())) {
                return false;
            }
            if (event == XMLEvent.START_ELEMENT && 
                  element.equals(reader.getLocalName())) {
                return true;
            }
        }
        return false;
    }
    
    public String getText() throws XMLStreamException {
        return reader.getElementText();
    }
    
    @Override
    public void close() throws Exception {
        if (reader != null) {
          try {
             reader.close();
          } catch (XMLStreamException e) { 
              System.out.print(e.getMessage());
              e.printStackTrace();
          }
       }
    }    
}
