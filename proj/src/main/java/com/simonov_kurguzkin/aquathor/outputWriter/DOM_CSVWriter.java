package com.simonov_kurguzkin.aquathor.outputWriter;

import java.io.IOException;
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
 * Class writes statistics information after simulation into CSV file from
 * existing XML with DOM methods
 *
 * @author Eugene
 */
public class DOM_CSVWriter extends CSVWriter {

    /**
     * '
     * DOM_CSVWriter constructor
     *
     * @param fileNameSting File name
     * @param overwritePermission Permission to overwrite file
     */
    public DOM_CSVWriter(String fileNameSting, boolean overwritePermission) {
        super(fileNameSting, overwritePermission);
    }

    @Override
    public void createCSV(String fileXML, String fileXSD) {
        if (!validateXML(fileXML, fileXSD))
            Logger.getLogger("could not write output .csv file");

        content.append(header);
        content.append(lineSeparator);
        StringBuilder tmp = new StringBuilder();
        try {
            Document doc = openDocument(fileXML);
            NodeList steps = doc.getElementsByTagName("step");
            for (int i = 0; i < steps.getLength(); i++) {
                Node thisStep = steps.item(i);
                if (thisStep.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList thisStepInfo = thisStep.getChildNodes();
                    Element e = (Element) thisStepInfo;
                    int iteration = getIntValue(e, "iteration");
                    int fish = getIntValue(e, "fishes_alive");
                    int shark = getIntValue(e, "sharks_alive");
                    tmp.append(iteration);
                    tmp.append(separator);
                    tmp.append(fish);
                    tmp.append(separator);
                    tmp.append(shark);
                    tmp.append(lineSeparator);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOM_CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        content.append(tmp);
    }

    /**
     * Method for open document
     *
     * @param fileLink File name
     * @return Opened document
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

    /**
     * Auxiliary metod for convert data from tag to int
     *
     * @param context Element
     * @param tagName Name of tag
     * @return Data in int
     */
    private int getIntValue(Element context, String tagName) {
        String s = (String) context.getElementsByTagName(tagName)
                .item(0).getTextContent();
        return Integer.parseInt(s);
    }

}
