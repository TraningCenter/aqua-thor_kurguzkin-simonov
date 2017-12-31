/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.inputParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Евгений
 */
public class DOMParser extends Parser {
    
    public Map<String, Object> parseConfigure(String fileLink) throws IOException {
        try {
            Map<String, Object> result = new LinkedHashMap<>();
            Document doc;
            doc = openDocument(fileLink);
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
        } catch (Exception ex) {
            System.out.println("Exception ex while DOM parsing config-file");
            Logger.getLogger(STAXParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public Map<String, Object> parseInput(String fileLink) throws IOException {
        List<Object> tmp=new ArrayList<>();
        Document doc;
        try {
            doc = openDocument(fileLink);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex.getCause());
        } catch (SAXException ex) {
            Logger.getLogger(DOMParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex.getCause());
        }

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
    }

    private Node getNode(Document doc, String tagName) {
        Node result = null;
        try {
            result = doc.getElementsByTagName(tagName).item(0);
        } finally {
            return result;
        }
    }

    private int getIntValue(Element context, String tagName) {
        String s = (String) context.getElementsByTagName(tagName)
                .item(0).getTextContent();
        return Integer.parseInt(s);
    }

    private Document openDocument(String fileLink) throws ParserConfigurationException, SAXException, IOException {
        Document doc;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(fileLink);
        return doc;
    }

}
