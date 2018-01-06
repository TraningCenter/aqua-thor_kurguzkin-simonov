package com.simonov_kurguzkin.aquathor.outputWriter;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class for testing XMLWriter class
 *
 * @author Eugene
 */
public class XMLWriterTest extends WriterTest {

    /**
     * Test for checking the equivalence of records
     */
    @Test
    public void testWritingResultEquals() {

        String domFileName = "src/test/resources/DOM_XMLWriter_test.xml";
        String jaxbFileName = "src/test/resources/JAXB_XMLWriter_test.xml";
        DOM_XMLWriter dom = null;
        JAXB_XMLWriter jaxb = null;
        try {
            dom = new DOM_XMLWriter(domFileName, false);
            jaxb = new JAXB_XMLWriter(jaxbFileName, false);

            writerWork(dom, domFileName);
            writerWork(jaxb, jaxbFileName);
            String domContent = readFile(domFileName);
            String jaxbContent = readFile(jaxbFileName);

            assertEquals(domContent, jaxbContent);
        } catch (IOException | NullPointerException ex) {
            assertTrue(false);
        } finally {
            dom.deleteFile(domFileName);
            jaxb.deleteFile(jaxbFileName);
        }
    }

}
