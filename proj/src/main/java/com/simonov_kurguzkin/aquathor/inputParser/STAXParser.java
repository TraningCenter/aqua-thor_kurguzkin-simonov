package com.simonov_kurguzkin.aquathor.inputParser;

import com.simonov_kurguzkin.aquathor.dataHandler.Entity;
import com.simonov_kurguzkin.aquathor.inputParser.handyClasses.StaxStreamProcessor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;

/**
 *
 * @author Vladimir
 */
public class STAXParser extends Parser {
    @Override
    public Map<String, Object> parseInput(String fileLink) throws IOException {
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(fileLink)))) {
            XMLStreamReader reader = processor.getReader();
            int streamCounter = 0;
            List<Object> listToReturn = new ArrayList<Object>();
            while (processor.startElement("entities", null)) {
                List<List<Integer>> tempLists = new ArrayList<List<Integer>>();
                while (processor.startElement("streams", "entities")){
                    while (processor.startElement("stream", "streams")) {
                        streamCounter++;
                        int speed = 0;
                        int start = 0;
                        int end = 0;
                        while (processor.startElement("speed", "stream")) {
                            speed = Integer.parseInt(processor.getText());
                            if (reader.isEndElement()) break;
                        }
                        while (processor.startElement("y_start", "stream")) {
                            start = Integer.parseInt(processor.getText());
                            if (reader.isEndElement()) break;
                        }
                        while (processor.startElement("y_end", "stream")) {
                            end = Integer.parseInt(processor.getText());
                            if (reader.isEndElement()) break;
                        }
                        tempLists.add(Arrays.asList(speed, start, end));
                    }
                    listToReturn.add(streamCounter);
                    for (List<Integer> curList : tempLists){
                        listToReturn.addAll(curList);
                    }
                    if (reader.isEndElement()) break;
                }
                while (processor.startElement("animals", "entities")){
                    int reproduction = 0;
                    int time = 0;
                    int speed = 0;
                    int feel = 0;
                    int quantity = 0;
                    int hungry = 0;
                    while (processor.startElement("fish", "animals")){
                        while (processor.startElement("reproduction_frequency", "fish")) { reproduction = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("time_to_live", "fish")) { time = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("speed", "fish")) { speed = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("feel_radius", "fish")) { feel = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("quantity", "fish")) { quantity = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        listToReturn.addAll(Arrays.asList(quantity, reproduction, time, speed, feel));
                        if (reader.isEndElement()) break;
                    }
                    while (processor.startElement("shark", "animals")){
                        while (processor.startElement("time_to_live", "fish")) { time = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("time_to_hungry_live", "fish")) { hungry = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("speed", "fish")) { speed = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("feel_radius", "fish")) { feel = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        while (processor.startElement("quantity", "fish")) { quantity = Integer.parseInt(processor.getText()); if (reader.isEndElement()) break; }
                        listToReturn.addAll(Arrays.asList(quantity, time, hungry, speed, feel));
                        if (reader.isEndElement()) break;
                    }
                    if (reader.isEndElement()) break;
                }
                if (reader.isEndElement()) break;
            }
            return listTomap(listToReturn);
        } catch (Exception ex) {
            System.out.println("Exception ex while STAX parsing");
            Logger.getLogger(STAXParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }    
}
