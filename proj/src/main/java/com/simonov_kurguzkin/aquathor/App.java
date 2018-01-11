package com.simonov_kurguzkin.aquathor;

/**
 * Main application class
 *
 * @author Eugene
 */
public class App {

    /**
     * App entry point
     *
     * @param args Parameters that can be passed through the console
     */
    public static void main(String[] args) {
        // Console
//        String configFile = "classes/configure.xml";
//        String inputFile = "classes/input.xml";
//        String outputXMLFile = "classes/statistics.xml";
//        String outputCSVFile = "classes/statistics.csv";
        // IDE
        String configFile = "src/main/resources/configure.xml";
        String inputFile = "src/main/resources/input.xml";
        String outputXMLFile = "src/main/resources/statistics.xml";
        String outputCSVFile = "src/main/resources/statistics.csv";
        Controller controller = new Controller(configFile, inputFile, outputXMLFile, outputCSVFile);
        controller.work();
    }

}
