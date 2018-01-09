package com.simonov_kurguzkin.aquathor;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import com.simonov_kurguzkin.aquathor.dataHandler.DataHandler;
import com.simonov_kurguzkin.aquathor.inputParser.DOMParser;
import com.simonov_kurguzkin.aquathor.inputParser.JAXBParser;
import com.simonov_kurguzkin.aquathor.inputParser.Parser;
import com.simonov_kurguzkin.aquathor.inputParser.SAXParser;
import com.simonov_kurguzkin.aquathor.inputParser.STAXParser;
import com.simonov_kurguzkin.aquathor.outputWriter.CSVWriter;
import com.simonov_kurguzkin.aquathor.outputWriter.DOM_CSVWriter;
import com.simonov_kurguzkin.aquathor.outputWriter.DOM_XMLWriter;
import com.simonov_kurguzkin.aquathor.outputWriter.JAXB_CSVWriter;
import com.simonov_kurguzkin.aquathor.outputWriter.JAXB_XMLWriter;
import com.simonov_kurguzkin.aquathor.outputWriter.XMLWriter;
import com.simonov_kurguzkin.aquathor.visualizer.Visualizer;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that stores basics classes and controls their interaction
 *
 * @author Eugene
 */
public class Controller {

    /**
     * Path and name of configuration file
     */
    private final String configFileName;
    /**
     * Path and name of XSD for configuration file
     */
    private final String xsdConfigFileName;
    /**
     * Path and name of input file
     */
    private final String inputFileName;
    /**
     * Path and name of XSD for input file
     */
    private final String xsdInputFileName;
    /**
     * Path and name of output file
     */
    private final String outputXMLFileName;
    /**
     * Path and name of XSD for output file
     */
    private final String xsdOutputXMLFileName;
    /**
     * Path and name of configuration file
     */
    private final String outputCSVFileName;
    /**
     * Config parser that will parse configure file
     */
    private final DOMParser configParser;
    /**
     * Config parser that will parse input file
     */
    private Parser inputParser;
    /**
     * Auxiliary variable to check for changes in input file
     */
    private long lastFilesCheck;
    /**
     * Game field
     */
    private Field field;
    /**
     * Game time
     */
    private int gameTime;
    /**
     * Variable of class that is responsible for the logic of the program
     */
    private DataHandler dataHandler;
    /**
     * Variable, which is responsible for the visualization of the program
     */
    private Visualizer visualizer;
    /**
     * Parser that writes an output XML file
     */
    private XMLWriter xmlWriter;
    /**
     * Parser that writes an output CSV file
     */
    private CSVWriter csvWriter;
    /**
     * Time of sleeping in work
     */
    private final int pause;

    /**
     * Controller constructor
     *
     * @param configFile Path and name of the configuration file
     * @param inputFile Path and name of the input file
     * @param outputXMLFileName Path and name of the output XML file
     * @param outputCSVFileName Path and name of the output CSV file
     */
    public Controller(String configFile, String inputFile,
            String outputXMLFileName, String outputCSVFileName) {
        this.configFileName = configFile;
        //Console
        this.xsdConfigFileName = "classes/configure.xsd";
        this.xsdInputFileName = "classes/input.xsd";
        this.xsdOutputXMLFileName = "classes/statistics.xsd";
        //IDE
//        this.xsdConfigFileName = "src/main/resources/configure.xsd";
//        this.xsdInputFileName = "src/main/resources/input.xsd";
//        this.xsdOutputXMLFileName = "src/main/resources/statistics.xsd";

        this.inputFileName = inputFile;
        this.outputXMLFileName = outputXMLFileName;
        this.outputCSVFileName = outputCSVFileName;
        configParser = new DOMParser();
        updateLastCheckFiles();
        this.pause = 1000;
    }

    /**
     * Method, in which all the basics parts works
     */
    public void work() {
        do {
            try {
                initialize();
            } catch (IllegalArgumentException | IOException ex) {
                Logger logger = LoggerFactory.getLogger(Controller.class);
                logger.error("When reading the input files, an error occurred. "
                        + "\nPlease check the presence of files and the correctness of the records in them. "
                        + "\nAfter that restart this program");
                return;
            }

            xmlWriter.deleteFile(outputXMLFileName);
            int iteration = 0;
            while (iteration++ <= gameTime) {
                visualizer.visualize(dataHandler.generateSnapshot());
                xmlWriter.addRecord(dataHandler.generateStatistics());
                xmlWriter.writeFile();
                dataHandler.processNextStep();
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException ex) {
                    Logger logger = LoggerFactory.getLogger(Controller.class);
                    logger.error("Some error during thread sleeping in controller");
                }
            }
            csvWriter.deleteFile(outputCSVFileName);
            csvWriter.createCSV(outputXMLFileName, xsdOutputXMLFileName);
            csvWriter.writeFile();
            try {
                visualizer.closeScreen();
            } catch (IOException ex) {
                Logger logger = LoggerFactory.getLogger(Controller.class);
                logger.error("Error while trying to close the screen in controller");
            }
        } while (checkFilesChanged());
    }

    /**
     * Method for initializing the field with data obtained from input files
     *
     * @throws IOException Generates if there are errors when working with input
     * data
     */
    private void initialize() throws IOException {
        Map<String, Object> config = configParser.parseConfigure(configFileName, xsdConfigFileName);
        initializeParser((String) config.get("in_parser"));
        initializeWriter((String) config.get("out_parser"));
        initilizeGameTime(Integer.parseInt((String) config.get("game_time")));
        field = new Field(Boolean.valueOf((String) config.get("enclosed")),
                Integer.parseInt((String) config.get("width")),
                Integer.parseInt((String) config.get("height")));

        Map<String, Object> entities = inputParser.parseInput(inputFileName, xsdInputFileName);
        dataHandler = new DataHandler(entities, field);
        visualizer = new Visualizer(field);
    }

    /**
     * Method for initializing field of input data parser
     *
     * @param parserName The name of the parser to be used
     */
    private void initializeParser(String parserName) {
        parserName = parserName.toUpperCase();
        switch (parserName) {
            case "DOM":
            default:
                inputParser = new DOMParser();
                break;
            case "SAX":
                inputParser = new SAXParser();
                break;
            case "STAX":
                inputParser = new STAXParser();
                break;
            case "JAXB":
                inputParser = new JAXBParser();
                break;
        }
    }

    /**
     * Method for initializing tools for recording output data
     *
     * @param parserName The name of the parser to be used
     * @throws IOException Generated on errors at work with files
     */
    private void initializeWriter(String parserName) throws IOException {
        parserName = parserName.toUpperCase();
        switch (parserName) {
            case "DOM":
            default:
                xmlWriter = new DOM_XMLWriter(outputXMLFileName, true);
                csvWriter = new DOM_CSVWriter(outputCSVFileName, true);
                break;
            case "JAXB":
                xmlWriter = new JAXB_XMLWriter(outputXMLFileName, true);
                csvWriter = new JAXB_CSVWriter(outputCSVFileName, true);
                break;
        }
    }

    /**
     * Method for initializing the game time
     *
     * @param time
     */
    private void initilizeGameTime(int time) {
        if (time < 0 || time > 50) {
            time = 20;
        }
        this.gameTime = time;
    }

    /**
     * Method for checking for changes in input files
     */
    private boolean checkFilesChanged() {
        File config = new File(configFileName);
        File input = new File(inputFileName);
        long configLastModified = config.lastModified();
        long inputLastModified = input.lastModified();

        boolean result = (lastFilesCheck < configLastModified
                || lastFilesCheck < inputLastModified);
        updateLastCheckFiles();
        return result;
    }

    private void updateLastCheckFiles() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        lastFilesCheck = timestamp.getTime();
    }

}
// fixing problems
