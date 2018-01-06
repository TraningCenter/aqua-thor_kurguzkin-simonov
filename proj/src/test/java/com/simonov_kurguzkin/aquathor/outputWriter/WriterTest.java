package com.simonov_kurguzkin.aquathor.outputWriter;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Statistics;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class for testing Writer class
 *
 * @author Eugene
 */
public class WriterTest {

    /**
     * Test to verify the correctness of file
     */
    @Test
    public void testCorrectCreateFile() {
        String domFileName = "src/test/resources/DOM_XMLWriter_test.xml";
        DOM_XMLWriter dom = null;
        try {
            dom = new DOM_XMLWriter(domFileName, true);
            //перезаписываем файл, имея нужные права прав
            dom.createFile();
            dom.createFile();
        } catch (IOException | NullPointerException ex) {
            assertTrue(false);
        } finally {
            dom.deleteFile(domFileName);
        }
    }

    /**
     * Test to verify the incorrectness of incorrect file
     */
    @Test(expected = IOException.class)
    public void testIncorrectCreateFile() throws IOException {
        String jaxbFileName = "src/test/resources/JAXB_XMLWriter_test.xml";
        JAXB_XMLWriter jaxb = null;
        try {
            //пытаемся перезаписать файл, не имея соответствующих прав
            jaxb = new JAXB_XMLWriter(jaxbFileName, false);
            jaxb.createFile();
            jaxb.createFile();
        } finally {
            jaxb.deleteFile(jaxbFileName);
        }
    }

    /**
     * Auxiliary method for write file
     *
     * @param writer Objcet of XMLWriter
     * @param fileName File name
     * @throws IOException
     */
    protected void writerWork(XMLWriter writer, String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
        writer.createFile();
        Statistics statistics = new Statistics(5, 20, 5);
        writer.addRecord(statistics);
        writer.writeFile();
    }

    /**
     * Auxiliary method for read existing file
     *
     * @param fileName File name
     * @return File content
     * @throws IOException
     */
    protected String readFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        StringBuilder result = new StringBuilder();
        for (String s : lines)
            result.append(s);
        return result.toString();
    }

}
