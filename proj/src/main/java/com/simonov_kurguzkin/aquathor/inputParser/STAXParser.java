package com.simonov_kurguzkin.aquathor.inputParser;

import com.simonov_kurguzkin.aquathor.inputParser.handyClasses.StaxStreamProcessor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML STAX parsing functionality
 *
 * @author Vladimir
 */
public class STAXParser extends Parser {

    @Override
    public Map<String, Object> parseInput(String fileLink, String xsdFileLink) throws IOException {
        if (!validateXML(fileLink, xsdFileLink))
            throw new IOException("input file does not meet the requirements");
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(fileLink)))) {
            XMLStreamReader reader = processor.getReader();
            int streamCounter = 0;
            List<Object> listToReturn = new ArrayList<>();

            while (processor.startElement("entities", null)) {

                reader.nextTag();
                if (!reader.isStartElement())
                    throw new IOException("No start streams or animals tag");
                String switchTag = reader.getLocalName();
                if (!switchTag.equals("streams") && !(switchTag.equals("animals")))
                    throw new IOException("No streams OR animals tag");
                if (switchTag.equals("streams")) {
                    List<List<Integer>> tempLists = new ArrayList<>();
                    while (processor.startElement("stream", "streams")) {
                        streamCounter++;
                        int speed = 0;
                        int start = 0;
                        int end = 0;
                        while (processor.startElement("speed", "stream")) {
                            speed = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("y_start", "stream")) {
                            start = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("y_end", "stream")) {
                            end = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        tempLists.add(Arrays.asList(speed, start, end));
                    }
                    listToReturn.add(streamCounter);
                    for (List<Integer> curList : tempLists) {
                        listToReturn.addAll(curList);
                    }
                    if (!reader.isEndElement())
                        throw new IOException("No close tag of Streams");
                    else
                        reader.nextTag();
                } else if (switchTag.equals("animals")) {
                    listToReturn.add(0);
                }

                switchTag = reader.getLocalName();

                if (switchTag.equals("animals") && reader.isStartElement()) {
                    int reproduction = 0;
                    int time = 0;
                    int speed = 0;
                    int feel = 0;
                    int quantity = 0;
                    int hungry = 0;
                    while (processor.startElement("fish", "animals")) {
                        while (processor.startElement("reproduction_frequency", "fish")) {
                            reproduction = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("time_to_live", "fish")) {
                            time = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("speed", "fish")) {
                            speed = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("feel_radius", "fish")) {
                            feel = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("quantity", "fish")) {
                            quantity = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        listToReturn.addAll(Arrays.asList(quantity, reproduction, time, speed, feel));
                        if (reader.isEndElement())
                            break;
                    }
                    while (processor.startElement("shark", "animals")) {
                        while (processor.startElement("time_to_live", "fish")) {
                            time = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("time_to_hungry_live", "fish")) {
                            hungry = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("speed", "fish")) {
                            speed = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("feel_radius", "fish")) {
                            feel = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        while (processor.startElement("quantity", "fish")) {
                            quantity = Integer.parseInt(processor.getText());
                            if (reader.isEndElement())
                                break;
                        }
                        listToReturn.addAll(Arrays.asList(quantity, time, hungry, speed, feel));
                        if (reader.isEndElement())
                            break;
                    }
                } else
                    throw new IOException("No start tag of animals");

                while (reader.isEndElement() && !(reader.getLocalName().equals("entities"))) {
                    reader.nextTag();
                }
                if (reader.isEndElement())
                    break;
                else
                    throw new IOException("No closing tag for entities");
            }
            return listTomap(listToReturn);
        } catch (Exception ex) {
            Logger logger = LoggerFactory.getLogger(STAXParser.class);
            logger.error("Some error with STAX input parsing");
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }
}
