package com.simonov_kurguzkin.aquathor.outputWriter;

import org.junit.Test;

/**
 * Class for testing DOM_CSVWriter class
 *
 * @author Eugene
 */
public class DOM_CSVWriterTest extends CSVWriterTest {

    @Test
    public void testCreateCSV() {
        CSVWriter dom = new DOM_CSVWriter("", false);
        createCSV(dom);
    }

}
