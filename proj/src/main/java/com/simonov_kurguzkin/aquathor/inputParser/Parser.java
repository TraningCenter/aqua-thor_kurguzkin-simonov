package com.simonov_kurguzkin.aquathor.inputParser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

/**
 * Class for converting input XML files to program data
 *
 * @author Eugene
 */
public abstract class Parser {

    /**
     * Method for initial processing of input XML file data
     *
     * @param fileLink Input XML file name
     * @param xsdFileLink File name of XSD for input file
     * @return Data dictionary and labels for them
     * @throws IOException Throws when errors occur in working with files
     */
    public abstract Map<String, Object> parseInput(String fileLink, String xsdFileLink) throws IOException;

    /**
     * Auxiliary method for convert list of objects to map
     *
     * @param list Listt of objects
     * @return Data dictionary and labels for them
     */
    protected Map<String, Object> listTomap(List<Object> list) {
        Map<String, Object> result = new LinkedHashMap<>();
        int streamNum = (Integer) list.get(0);
        int position = 1;
        int currentStreamNum = 0;
        for (int i = 0; i < streamNum; i++) {
            result.put("stream_speed" + currentStreamNum, list.get(position++));
            result.put("stream_start" + currentStreamNum, list.get(position++));
            result.put("stream_end" + currentStreamNum, list.get(position++));
            currentStreamNum++;
        }

        result.put("fish_quantity", list.get(position++));
        result.put("fish_reproduction", list.get(position++));
        result.put("fish_live", list.get(position++));
        result.put("fish_speed", list.get(position++));
        result.put("fish_radius", list.get(position++));

        result.put("shark_quantity", list.get(position++));
        result.put("shark_live", list.get(position++));
        result.put("shark_hungry", list.get(position++));
        result.put("shark_speed", list.get(position++));
        result.put("shark_radius", list.get(position++));

        return result;
    }

    /**
     * Method for syntactically checking the correctness of files
     *
     * @param xmlFileName XML file name
     * @param xsdFileName XSD file name
     * @return Returns true if file is correct and false differently
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

}
