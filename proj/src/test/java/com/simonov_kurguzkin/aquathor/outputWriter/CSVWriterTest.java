package com.simonov_kurguzkin.aquathor.outputWriter;

import AuxiliaryClasses.FilesLinks;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class for testing CSVWriter class
 *
 * @author Eugene
 */
public class CSVWriterTest extends WriterTest {

    /**
     * Test to verify the equivalence of the transformation XML to CSV
     */
    @Test
    public void testEqualsConvertingCSV() {
        //проверяем, что dom и jaxb дают одинаковые результаты
        DOM_CSVWriter dom = new DOM_CSVWriter("", false);
        dom.createCSV(FilesLinks.STATISTICS_FILE, FilesLinks.STATISTICSXSD_FILE);
        String domContent = dom.content.toString();

        JAXB_CSVWriter jaxb = new JAXB_CSVWriter("", false);
        jaxb.createCSV(FilesLinks.STATISTICS_FILE, FilesLinks.STATISTICSXSD_FILE);
        String jaxbContent = dom.content.toString();

        assertEquals(domContent, jaxbContent);
    }

    /**
     * Auxiliary method to check the correctness of the conversion of XML to CSV
     *
     * @param csvWriter Verifiable CSVWriter
     */
    protected void createCSV(CSVWriter csvWriter) {
        csvWriter.createCSV(FilesLinks.STATISTICS_FILE, FilesLinks.STATISTICSXSD_FILE);
        String domCsv = csvWriter.content.toString();
        String defaultCsv = createDefaultCSV();
        assertEquals(domCsv, defaultCsv);
    }

    /**
     * Auxiliary method to create default CSV data
     *
     * @return CSV String
     */
    private String createDefaultCSV() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("iteration, alive fishes, alive sharks\n");
        tmp.append("0, 20, 15\n");
        tmp.append("1, 17, 15\n");
        tmp.append("2, 14, 15\n");
        tmp.append("3, 11, 16\n");
        return tmp.toString();
    }

}
