package com.simonov_kurguzkin.aquathor.inputParser.handyClasses;

import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 * Class contains additional methods for STAX processing
 * @author Vladimir
 */
public class StaxStreamProcessor implements AutoCloseable {
    /**
     * Factory which constructs XMLStreamReaders
     */
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    
    /**
     * Basic XMLStreamReader. Get-property
     */
    private final XMLStreamReader reader;
    
    /**
     * Constructor creates basic XMLStreamReader from the set inputStream
     * @param inputStream
     * @throws XMLStreamException 
     */
    public StaxStreamProcessor(InputStream inputStream) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(inputStream);
    }
    
    public XMLStreamReader getReader() {
        return reader;
    }
    
    /**
     * Method works with XML, reads it while it's not a start element tag
     * @param element
     * @param parent
     * @return true if it's start tag with element title
     * @return false if it's end of file, or end element of not root tag
     * @throws XMLStreamException 
     */
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
    
    /**
     * Method returns text which is contained in current tag
     * @return String with current tag text
     * @throws XMLStreamException 
     */
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
