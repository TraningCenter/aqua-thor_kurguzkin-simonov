package com.simonov_kurguzkin.aquathor.outputWriter;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Statistics;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for writing XML file and adding content to it. Based on DOM parser
 *
 * @author Eugene
 */
public class DOM_XMLWriter extends XMLWriter {

    /**
     * Auxiliary field for create document
     */
    private DocumentBuilderFactory docFactory;
    /**
     * Auxiliary field for create document
     */
    private DocumentBuilder docBuilder;
    /**
     * The document that we will edit
     */
    private Document document;
    /**
     * Root element in the document
     */
    private Element rootElement;

    /**
     * DOM_XMLWriter constructor
     *
     * @param fileNameSting XML file name
     * @param overwritePermission Permission to overwrite file
     * @throws IOException Throws when errors occur in working with files
     */
    public DOM_XMLWriter(String fileNameSting, boolean overwritePermission) throws IOException {
        super(fileNameSting, overwritePermission);
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.newDocument();
        } catch (ParserConfigurationException ex) {
            throw new IOException();
        }
    }

    @Override
    public void addRecord(Statistics statistics) {
        if (rootElement == null) {
            rootElement = document.createElement("statistics");
            document.appendChild(rootElement);
        }
        Element stepElement = document.createElement("step");
        Element iterationElement = document.createElement("iteration");
        iterationElement.appendChild(document.createTextNode(getIteration(statistics)));
        Element fishElement = document.createElement("fishes_alive");
        fishElement.appendChild(document.createTextNode(getFishes(statistics)));
        Element sharkElement = document.createElement("sharks_alive");
        sharkElement.appendChild(document.createTextNode(getSharks(statistics)));

        stepElement.appendChild(iterationElement);
        stepElement.appendChild(fishElement);
        stepElement.appendChild(sharkElement);
        rootElement.appendChild(stepElement);

        //кладем все в content
        try {
            DOMSource domSource = new DOMSource(document);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //выравнивание файла
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            //преобразовали, а теперь кладем в content
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);
            content = new StringBuilder(sw.toString());
        } catch (TransformerException ex) {
            Logger.getLogger(DOM_XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get iteration data from statistics
     *
     * @param statistics Sumulation statistics
     * @return Iteration data String
     */
    private String getIteration(Statistics statistics) {
        return String.valueOf(statistics.getIterationStep());
    }

    /**
     * Get fish data from statistics
     *
     * @param statistics Sumulation statistics
     * @return Fish data String
     */
    private String getFishes(Statistics statistics) {
        return String.valueOf(statistics.getFishAmount());
    }

    /**
     * Get shark data from statistics
     *
     * @param statistics Sumulation statistics
     * @return Shark data String
     */
    private String getSharks(Statistics statistics) {
        return String.valueOf(statistics.getSharksAmount());
    }

}
