package com.simonov_kurguzkin.aquathor.inputParser;

import AuxiliaryClasses.FilesLinks;
import java.io.IOException;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class for SAX parser tests
 *
 * @author Eugene
 */
public class SAXParserTest extends ParserTest {

    /**
     * Object of SAX parser
     */
    private SAXParser saxParser;

    /**
     * Initialize method
     */
    @Before
    public void setUp() {
        saxParser = new SAXParser();
    }

    /**
     * Test of parsing correct config data
     */
    @Test
    public void testParseConfigure() {
        Map<String, Object> me = createDefaultConfigureMap();
        Map<String, Object> sax = null;
        try {
            sax = saxParser.parseConfigure(FilesLinks.CONFIG_FILE, FilesLinks.CONFIGXSD_FILE);
        } catch (IOException e) {
            assertTrue(false);
        }
        assertEquals(me, sax);
    }

    /**
     * Test of parsing incorrect config data (tag error)
     */
    @Test
    public void testParseConfigureIncorrectTag() {
        Map<String, Object> me = createDefaultConfigureMap();
        Map<String, Object> sax = null;
        try {
            sax = saxParser.parseConfigure(FilesLinks.CONFIG_TAGERROR_FILE, FilesLinks.CONFIGXSD_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    /**
     * Test of parsing incorrect config data (without tag)
     */
    @Test
    public void testParseConfigureWithoutTag() {
        Map<String, Object> me = createDefaultConfigureMap();
        Map<String, Object> sax = null;
        try {
            sax = saxParser.parseConfigure(FilesLinks.CONFIG_WITHOUTTAGERROR_FILE, FilesLinks.CONFIGXSD_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testParseInput() {
        testParseInput(saxParser);
    }

    @Test
    public void testParseInputMissingParameter() {
        testParseInputMissingParameter(saxParser);
    }

    @Test
    public void testParseInputIncorrecttag() {
        testParseInputIncorrecttag(saxParser);
    }

    @Test
    public void testParseInputValueError() {
        testParseInputValueError(saxParser);
    }

    @Test
    public void testParseFileNotFound() {
        testParseFileNotFound(saxParser);
    }

}
