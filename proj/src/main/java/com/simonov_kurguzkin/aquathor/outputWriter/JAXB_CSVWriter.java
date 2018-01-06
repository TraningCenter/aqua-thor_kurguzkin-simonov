package com.simonov_kurguzkin.aquathor.outputWriter;

import com.simonov_kurguzkin.aquathor.outputWriter.jaxbWritingAdds.AStatistics;
import com.simonov_kurguzkin.aquathor.outputWriter.jaxbWritingAdds.Step;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Class writes statistics information after simulation into CSV file 
 * from existing XML with JAXB methods 
 * @author Vladimir
 */
public class JAXB_CSVWriter extends CSVWriter{
    
    /**
     * Using super constructor
     * @param fileNameSting
     * @param overwritePermission 
     */
    public JAXB_CSVWriter(String fileNameSting, boolean overwritePermission) {
        super(fileNameSting, overwritePermission);
    }

    @Override
    public void createCSV(String fileXML, String fileXSD) {
        if (!validateXML(fileXML, fileXSD))
            Logger.getLogger("Couldn't write output .csv file");
        content.append(header);
        content.append(lineSeparator);
        StringBuilder tmp = new StringBuilder();
        try {
            File file = new File(fileXML);
            JAXBContext jaxbContext = JAXBContext.newInstance(AStatistics.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            AStatistics stats = (AStatistics)unmarshaller.unmarshal(file);
            for (Step step : stats.getSteps()) {
                tmp.append(step.getIteration());
                tmp.append(separator);
                tmp.append(step.getFishes_alive());
                tmp.append(separator);
                tmp.append(step.getSharks_alive());
                tmp.append(lineSeparator);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(JAXB_CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("OK");
        }
        content.append(tmp);
    }
    
}
