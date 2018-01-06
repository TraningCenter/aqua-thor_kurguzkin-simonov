package com.simonov_kurguzkin.aquathor.inputParser;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class that converts input data with SAX parser
 *
 * @author Eugene
 */
public class SAXParser extends Parser {

    /**
     * Auxiliary parser
     */
    private javax.xml.parsers.SAXParser parser;
    /**
     * Tag handler
     */
    private SAXParserHandler handler;

    /**
     * SAXParser constructor
     */
    public SAXParser() {
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
            handler = new SAXParserHandler();
        } catch (ParserConfigurationException | SAXException ex) {
            //Logger.getLogger(SAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method for parsing config file
     *
     * @param fileLink Config file name
     * @param xsdFileLink XSD file name for config file
     * @return Data dictionary and labels for them
     * @throws IOException Throws when errors occur in working with files
     */
    public Map<String, Object> parseConfigure(String fileLink, String xsdFileLink) throws IOException {
        if (!validateXML(fileLink, xsdFileLink))
            throw new IOException("config file does not meet the requirements");
        try {
            parser.parse(fileLink, handler);
            Map<String, Object> result = handler.getResultMap();
            return result;
        } catch (SAXException ex) {
            //Logger.getLogger(SAXParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public Map<String, Object> parseInput(String fileLink, String xsdFileLink) throws IOException {
        if (!validateXML(fileLink, xsdFileLink))
            throw new IOException("input file does not meet the requirements");
        try {
            parser.parse(fileLink, handler);
            List<Object> tmp = handler.getResultList();
            return listTomap(tmp);
        } catch (IndexOutOfBoundsException | SAXException ex) {
            //Logger.getLogger(SAXParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Auxiliary class for parsing
     */
    private class SAXParserHandler extends DefaultHandler {

        /**
         * Result dictionary
         */
        private final Map<String, Object> resultMap;
        /**
         * Result list
         */
        private final List<Object> resultList;
        /**
         * Temporary string
         */
        private String tmp;
        /**
         * Variable for the signal that the config is being processed
         */
        private boolean configFlag;
        /**
         * Variable for the signal that the entities is being processed
         */
        private boolean entitiesFlag;
        private boolean streamsFlag;
        /**
         * Variable for the signal that there was opening fish tag
         */
        private boolean fishFlag;
        /**
         * Variable for the signal that there was opening shark tag
         */
        private boolean sharkFlag;
        /**
         * Variable for the signal that there was opening stream tag
         */
        private int streamsNum;
        /**
         * Variable for checking the availability of data on flows
         */
        private boolean wasStreams;

        /**
         * SAXParserHandler constructor
         */
        public SAXParserHandler() {
            resultMap = new LinkedHashMap<>();
            resultList = new LinkedList<>();
            streamsNum = 0;
        }

        public Map<String, Object> getResultMap() {
            return resultMap;
        }

        public List<Object> getResultList() {
            return resultList;
        }

        @Override
        public void characters(char[] buffer, int start, int length) {
            tmp = new String(buffer, start, length);
        }

        @Override
        public void startElement(String uri, String localName,
                String qName, Attributes attributes) throws SAXException {
            tmp = "";
            if (qName.equalsIgnoreCase("configuration"))
                configFlag = true;
            if (qName.equalsIgnoreCase("entities"))
                entitiesFlag = true;
            if (entitiesFlag && qName.equalsIgnoreCase("stream")) {
                streamsFlag = true;
                streamsNum++;
                wasStreams = true;
            }
            if (entitiesFlag && qName.equalsIgnoreCase("fish"))
                fishFlag = true;
            if (entitiesFlag && qName.equalsIgnoreCase("shark"))
                sharkFlag = true;
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equalsIgnoreCase("configuration"))
                configFlag = false;
            if (qName.equalsIgnoreCase("entities")) {
                entitiesFlag = false;
                //если у нас не было потоков, вносим запись, что потоков было 0 штук
                if (!wasStreams)
                    resultList.add(0, 0);
            }
            if (qName.equalsIgnoreCase("streams")) {
                streamsFlag = false;
                resultList.add(0, streamsNum);
            }
            if (qName.equalsIgnoreCase("fish"))
                fishFlag = false;
            if (qName.equalsIgnoreCase("shark"))
                sharkFlag = false;
            handleTag(qName);
        }

        /**
         * Method for handling tags
         *
         * @param tagName Handling tag name
         * @throws NumberFormatException Throws when an error occurs converting
         * a string to a number
         */
        private void handleTag(String tagName) throws NumberFormatException {
            if (configFlag) {
                if (tagName.equalsIgnoreCase("configuration")
                        || tagName.equalsIgnoreCase("field")) {
                    return;
                }
                resultMap.put(tagName, tmp);
            }
            if (entitiesFlag) {
                if (tagName.equalsIgnoreCase("entities")
                        || tagName.equalsIgnoreCase("streams")
                        || tagName.equalsIgnoreCase("stream")
                        || tagName.equalsIgnoreCase("animals")
                        || tagName.equalsIgnoreCase("fish")
                        || tagName.equalsIgnoreCase("shark")) {
                    return;
                }
                int tmpInt;
                try {
                    tmpInt = Integer.parseInt(tmp);
                } catch (NumberFormatException ex) {
                    resultList.add(tmp);
                    return;
                }
                if (streamsFlag)
                    resultList.add(tmpInt);
                if (fishFlag || sharkFlag) {
                    if (tagName.equalsIgnoreCase("quantity"))
                        resultList.add(resultList.size() - 4, tmpInt);
                    else
                        resultList.add(tmpInt);
                }
            }
        }
    }

}
