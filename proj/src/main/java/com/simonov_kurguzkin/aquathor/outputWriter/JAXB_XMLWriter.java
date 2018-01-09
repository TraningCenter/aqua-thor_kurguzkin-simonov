package com.simonov_kurguzkin.aquathor.outputWriter;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Statistics;
import com.simonov_kurguzkin.aquathor.outputWriter.jaxbWritingAdds.AStatistics;
import com.simonov_kurguzkin.aquathor.outputWriter.jaxbWritingAdds.Step;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class writes statistics information every simulation step into XML file 
 * from statistics object with JAXB methods 
 * @author Vladimir
 */
public class JAXB_XMLWriter extends XMLWriter {

    /**
     * Handy statistics file which acсumulates information about all steps 
     * every step
     */
    private AStatistics stats;
    /**
     * Constant XML header. To make it standalone=no instead of standalone=yes
     * which returns from JAXB marshaller
     */
    private final String XML_TITLE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";

    /**
     * Using super constructor and also sets new accumulating statistics object
     * @param fileNameSting
     * @param overwritePermission 
     */
    public JAXB_XMLWriter(String fileNameSting, boolean overwritePermission) {
        super(fileNameSting, overwritePermission);
        stats = new AStatistics();
    }

    @Override
    public void addRecord(Statistics statistics) {
        try {
            if (stats == null)
                stats = new AStatistics();
            stats.getSteps().add(new Step(statistics));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JAXBContext jaxbContext = JAXBContext.newInstance(AStatistics.class);
            Marshaller marsh = jaxbContext.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.marshal(stats, baos);
            StringBuilder sb = new StringBuilder();

            sb.append(XML_TITLE);
            sb.append(baos.toString());

            content = sb;
        } catch (JAXBException ex) {
            Logger logger = LoggerFactory.getLogger(JAXB_XMLWriter.class);
            logger.error("Error occurred during adding JAXB statistics record");
        }
    }

        

}
