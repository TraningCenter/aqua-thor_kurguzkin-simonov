/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor;

import com.simonov_kurguzkin.aquathor.dataHandler.DataHandler;
import com.simonov_kurguzkin.aquathor.inputParser.DOMParser;
import com.simonov_kurguzkin.aquathor.inputParser.Parser;
import com.simonov_kurguzkin.aquathor.outputWriter.DOMWriter;
import com.simonov_kurguzkin.aquathor.outputWriter.Writer;
import com.simonov_kurguzkin.aquathor.visualizer.Visualizer;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Евгений
 */
public class Controller {

    private final String configFile = "src/main/resources/configure.xml";
    private final String inputFile = "src/main/resources/input.xml";
    private boolean configureFileChanged;
    private boolean inputFileChanged;
    private Parser parser;
    private Field field;
    private int gameTime;
    private DataHandler dataHandler;
    private Visualizer visualizer;
    private Writer writer;

    public Controller() {
        parser = new DOMParser();
        configureFileChanged = true;
        inputFileChanged = true;
    }

    public void work() {
        while (configureFileChanged || inputFileChanged) {
            initialize();
            int iteration = 0;
            while (iteration++ < gameTime) {
//            dataHandler.processNextStep();
//            visualizer.visualize(dataHandler.generateSnapshot());
//            writer.write(dataHandler.generateStatistics());
            }
            checkFilesChanged();
        }
    }

    private void initialize() {
        if (configureFileChanged) {
            try {
                Map<String, Object> config = parser.parseConfigure(configFile);
                initializeParser((String) config.get("in_parser"));
                initializeWriter((String) config.get("out_parser"));
                initilizeGameTime(Integer.parseInt((String) config.get("game_time")));
                field = new Field(Boolean.valueOf((String) config.get("enclosed")),
                        Integer.parseInt((String) config.get("width")),
                        Integer.parseInt((String) config.get("height")));
                configureFileChanged = false;
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (inputFileChanged) {
            try {
                Map<String, Object> entities = parser.parseInput(inputFile);
                dataHandler = new DataHandler();
                //отдаем entities в datahandler

            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            inputFileChanged = false;
        }
    }

    private void initializeParser(String parserName) {
        parserName = parserName.toUpperCase();
        switch (parserName) {
            case "DOM":
            default:
                parser = new DOMParser();
                break;
//            case "SAX"
//                parser = new SAXParser();
//                break;
//            case "STAX"
//                parser = new STAXParser();
//                break;
//            case "JAXB"
//                parser = new JAXBParser();
//                break;
        }
    }

    private void initializeWriter(String parserName) {
        parserName = parserName.toUpperCase();
        switch (parserName) {
            case "DOM":
            default:
                writer = new DOMWriter();
                break;
//            case "JAXB"
//                writer = new JAXBWritter();
//                break;
        }
    }

    private void initilizeGameTime(int time) {
        if (time < 0 || time > 100) {
            time = 20;
        } else {
            this.gameTime = time;
        }
    }

    private void checkFilesChanged() {
        //проверяем изменения в обоих файлах
        //если какой-то файл изменился, ставим соответсвтующую переменную в true

    }

}
