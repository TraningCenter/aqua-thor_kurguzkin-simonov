package com.simonov_kurguzkin.aquathor.inputParser;

import AuxiliaryClasses.FilesLinks;
import java.io.IOException;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class for DOM parser tests
 *
 * @author Eugene
 */
public class DOMParserTest extends ParserTest {

    /**
     * Object of DOM parser
     */
    private DOMParser domParser;

    /**
     * Initialize method
     */
    @Before
    public void setUp() {
        domParser = new DOMParser();
    }

    /**
     * Test of parsing correct config data
     */
    @Test
    public void testParseConfigure() {
        Map<String, Object> me = createDefaultConfigureMap();
        Map<String, Object> dom = null;
        try {
            dom = domParser.parseConfigure(FilesLinks.CONFIG_FILE, FilesLinks.CONFIGXSD_FILE);
        } catch (IOException e) {
            assertTrue(false);
        }
        assertEquals(me, dom);
    }

    /**
     * Test of parsing incorrect config data (tag error)
     */
    @Test
    public void testParseConfigureIncorrectTag() {
        Map<String, Object> me = createDefaultConfigureMap();
        Map<String, Object> dom = null;
        try {
            dom = domParser.parseConfigure(FilesLinks.CONFIG_TAGERROR_FILE, FilesLinks.CONFIGXSD_FILE);
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
        Map<String, Object> dom = null;
        try {
            dom = domParser.parseConfigure(FilesLinks.CONFIG_WITHOUTTAGERROR_FILE, FilesLinks.CONFIGXSD_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testParseInput() {
        testParseInput(domParser);
    }

    @Test
    public void testParseInputMissingParameter() {
        testParseInputMissingParameter(domParser);
    }

    @Test
    public void testParseInputIncorrecttag() {
        testParseInputIncorrecttag(domParser);
    }

    @Test
    public void testParseInputValueError() {
        testParseInputValueError(domParser);
    }

    @Test
    public void testParseFileNotFound() {
        testParseFileNotFound(domParser);
    }

}
