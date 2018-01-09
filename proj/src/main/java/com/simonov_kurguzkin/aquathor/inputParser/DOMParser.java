package com.simonov_kurguzkin.aquathor.inputParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class that converts input data with DOM parser
 *
 * @author Eugene
 */
public class DOMParser extends Parser {

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
            Map<String, Object> result = new LinkedHashMap<>();
            Document doc = openDocument(fileLink);

            Node tmp = getNode(doc, "in_parser");
            result.put(tmp.getNodeName(), tmp.getTextContent());
            tmp = getNode(doc, "out_parser");
            result.put(tmp.getNodeName(), tmp.getTextContent());
            tmp = getNode(doc, "game_time");
            result.put(tmp.getNodeName(), tmp.getTextContent());
            tmp = getNode(doc, "enclosed");
            result.put(tmp.getNodeName(), tmp.getTextContent());
            tmp = getNode(doc, "width");
            result.put(tmp.getNodeName(), tmp.getTextContent());
            tmp = getNode(doc, "height");
            result.put(tmp.getNodeName(), tmp.getTextContent());

            return result;
        } catch (NullPointerException | ParserConfigurationException | SAXException ex) {
            Logger logger = LoggerFactory.getLogger(DOMParser.class);
            logger.error("Something wrong in DOMParsing configure file");
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public Map<String, Object> parseInput(String fileLink, String xsdFileLink) throws IOException {
        if (!validateXML(fileLink, xsdFileLink))
            throw new IOException("input file does not meet the requirements");
        try {
            List<Object> tmp = new ArrayList<>();
            Document doc = openDocument(fileLink);

            NodeList streams = doc.getElementsByTagName("stream");
            tmp.add(streams.getLength());
            for (int i = 0; i < streams.getLength(); i++) {
                Node thisStream = streams.item(i);
                if (thisStream.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList thisStreamInfo = thisStream.getChildNodes();
                    Element e = (Element) thisStreamInfo;
                    int speed = getIntValue(e, "speed");
                    int start = getIntValue(e, "y_start");
                    int end = getIntValue(e, "y_end");
                    tmp.addAll(Arrays.asList(speed, start, end));
                }
            }

            NodeList fishInfo = doc.getElementsByTagName("fish").item(0).getChildNodes();
            Element e = (Element) fishInfo;
            int reproduction = getIntValue(e, "reproduction_frequency");
            int time = getIntValue(e, "time_to_live");
            int speed = getIntValue(e, "speed");
            int feel = getIntValue(e, "feel_radius");
            int quantity = getIntValue(e, "quantity");
            tmp.addAll(Arrays.asList(quantity, reproduction, time, speed, feel));

            NodeList sharkInfo = doc.getElementsByTagName("shark").item(0).getChildNodes();
            e = (Element) sharkInfo;
            time = getIntValue(e, "time_to_live");
            int hungry = getIntValue(e, "time_to_hungry_live");
            speed = getIntValue(e, "speed");
            feel = getIntValue(e, "feel_radius");
            quantity = getIntValue(e, "quantity");
            tmp.addAll(Arrays.asList(quantity, time, hungry, speed, feel));

            return listTomap(tmp);
        } catch (NullPointerException | NumberFormatException | ParserConfigurationException | SAXException ex) {
            Logger logger = LoggerFactory.getLogger(DOMParser.class);
            logger.error("Something wrong in DOMParsing input file");
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Method for get necessary node by name from document
     *
     * @param doc Current document
     * @param tagName Name of tag
     * @return Necessary node
     * @throws IOException Throws in case the tag is not found
     */
    private Node getNode(Document doc, String tagName) throws IOException {
        Node result = doc.getElementsByTagName(tagName).item(0);
        return result;
    }

    /**
     * Method for converting data from a dictionary into String to int
     *
     * @param context Context with data
     * @param tagName Name of tag
     * @return Integer value
     */
    private int getIntValue(Element context, String tagName) {
        String s = (String) context.getElementsByTagName(tagName)
                .item(0).getTextContent();
        return Integer.parseInt(s);
    }

    /**
     * Method for opening document
     *
     * @param fileLink File name
     * @return Document you are requesting to open
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Document openDocument(String fileLink) throws ParserConfigurationException, SAXException, IOException {
        Document doc;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(fileLink);
        return doc;
    }

}
