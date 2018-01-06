package com.simonov_kurguzkin.aquathor.inputParser;

import AuxiliaryClasses.FilesLinks;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Main class for testing parsers
 *
 * @author Eugene
 */
public class ParserTest {

    /**
     * Test to verify the equivalence of parsing results
     */
    @Test
    public void testParsingResultEquals() {
        try {
            Parser parser = new DOMParser();
            Map<String, Object> dom = parser.parseInput(FilesLinks.INPUT_FILE, FilesLinks.INPUTXSD_FILE);
            parser = new SAXParser();
            Map<String, Object> sax = parser.parseInput(FilesLinks.INPUT_FILE, FilesLinks.INPUTXSD_FILE);
            parser = new STAXParser();
            Map<String, Object> stax = parser.parseInput(FilesLinks.INPUT_FILE, FilesLinks.INPUTXSD_FILE);
            parser = new JAXBParser();
            Map<String, Object> jaxb = parser.parseInput(FilesLinks.INPUT_FILE, FilesLinks.INPUTXSD_FILE);

            assertEquals(dom, sax);
            assertEquals(dom, stax);
            assertEquals(dom, jaxb);
        } catch (IOException ex) {
            assertTrue(false);
        }
    }

    /**
     * Test to check the correctness of parsing
     *
     * @param parser Parser you want to test
     */
    protected void testParseInput(Parser parser) {
        Map<String, Object> me = createDefaultInputMap();
        Map<String, Object> parserResult = null;
        try {
            parserResult = parser.parseInput(FilesLinks.INPUT_FILE, FilesLinks.INPUTXSD_FILE);
        } catch (IOException e) {
            assertTrue(false);
        }
        assertEquals(me, parserResult);
    }

    /**
     * Test to verify the correct operation of the parser with an input file
     * without streams data
     *
     * @param parser Parser you want to test
     */
    protected void testParseInputMissingParameter(Parser parser) {
        Map<String, Object> me = createInputMapWithoutStreams();
        Map<String, Object> parserResult = null;
        try {
            parserResult = parser.parseInput(FilesLinks.INPUT_WITHOUTSTREAMS_FILE, FilesLinks.INPUTXSD_FILE);
        } catch (IOException e) {
            assertTrue(false);
        }
        assertEquals(me, parserResult);
    }

    /**
     * Test to check the correctness of the parser with an input file with
     * incorrect syntax
     *
     * @param parser Parser you want to test
     */
    protected void testParseInputIncorrecttag(Parser parser) {
        try {
            Map<String, Object> parserResult = parser.parseInput(FilesLinks.INPUT_VALUEERROR_FILE, FilesLinks.INPUTXSD_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    /**
     * Test to check the correctness of the parser with an input file with
     * incorrect data
     *
     * @param parser Parser you want to test
     */
    protected void testParseInputValueError(Parser parser) {
        try {
            Map<String, Object> parserResult = parser.parseInput(FilesLinks.INPUT_WITHOUTFISHES_FILE, FilesLinks.INPUTXSD_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    /**
     * Test for checking the correctness of the parser with a nonexistent file
     *
     * @param parser Parser you want to test
     */
    protected void testParseFileNotFound(Parser parser) {
        try {
            Map<String, Object> parserResult = parser.parseInput("nonexistent file", FilesLinks.INPUTXSD_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    /**
     * Method for generating default config data
     *
     * @return Map of default config data
     */
    protected Map<String, Object> createDefaultConfigureMap() {
        Map<String, Object> resut = new LinkedHashMap<>();
        resut.put("in_parser", "DOM");
        resut.put("out_parser", "DOM");
        resut.put("game_time", "15");
        resut.put("enclosed", "false");
        resut.put("width", "35");
        resut.put("height", "35");
        return resut;
    }

    /**
     * Method for generating default input data
     *
     * @return Map of default input data
     */
    protected Map<String, Object> createDefaultInputMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("stream_speed0", 1);
        result.put("stream_start0", 0);
        result.put("stream_end0", 10);
        result.put("stream_speed1", -1);
        result.put("stream_start1", 25);
        result.put("stream_end1", 34);

        result.put("fish_quantity", 25);
        result.put("fish_reproduction", 3);
        result.put("fish_live", 10);
        result.put("fish_speed", 2);
        result.put("fish_radius", 4);

        result.put("shark_quantity", 5);
        result.put("shark_live", 20);
        result.put("shark_hungry", 5);
        result.put("shark_speed", 2);
        result.put("shark_radius", 6);
        return result;
    }

    /**
     * Method for generating input data without streams
     *
     * @return Map of input data without streams
     */
    protected Map<String, Object> createInputMapWithoutStreams() {
        Map<String, Object> resut = new LinkedHashMap<>();
        resut.put("fish_quantity", 25);
        resut.put("fish_reproduction", 3);
        resut.put("fish_live", 10);
        resut.put("fish_speed", 2);
        resut.put("fish_radius", 4);

        resut.put("shark_quantity", 5);
        resut.put("shark_live", 20);
        resut.put("shark_hungry", 5);
        resut.put("shark_speed", 2);
        resut.put("shark_radius", 6);
        return resut;
    }

}
