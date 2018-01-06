package com.simonov_kurguzkin.aquathor.outputWriter;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

/**
 * Class for convert XML data to CSV
 *
 * @author Eugene
 */
public abstract class CSVWriter extends Writer {

    /**
     * Header of CSV
     */
    protected final String header = "iteration, alive fishes, alive sharks";
    /**
     * Separator symbol
     */
    protected final String separator = ", ";
    /**
     * Line separator symbol
     */
    protected final String lineSeparator = "\n";

    /**
     * CSVWriter constructor
     *
     * @param fileNameSting XML file name
     * @param overwritePermission Permission to overwrite file
     */
    public CSVWriter(String fileNameSting, boolean overwritePermission) {
        super(fileNameSting, overwritePermission);
    }

    /**
     * Method for checking the correctness of XML file
     *
     * @param xmlFileName XML file name
     * @param xsdFileName XSD for XML file name
     * @return
     */
    protected boolean validateXML(String xmlFileName, String xsdFileName) {
        try {
            File xsd = new File(xsdFileName);
            File xml = new File(xmlFileName);
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(xsd)
                    .newValidator()
                    .validate(new StreamSource(xml));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Method for convert XML to CSV
     *
     * @param fileXML
     * @param fileXSD
     */
    public abstract void createCSV(String fileXML, String fileXSD);

}
