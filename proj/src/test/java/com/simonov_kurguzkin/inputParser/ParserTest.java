/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.inputParser;

import com.simonov_kurguzkin.aquathor.inputParser.DOMParser;
import com.simonov_kurguzkin.aquathor.inputParser.JAXBParser;
import com.simonov_kurguzkin.aquathor.inputParser.Parser;
import com.simonov_kurguzkin.aquathor.inputParser.STAXParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import AuxiliaryClasses.FilesLinks;

/**
 *
 * @author Евгений, Vladimir
 */
public class ParserTest {

    protected void testParseInput(Parser parser) {
        Map<String, Object> me = createDefaultInputMap();
        Map<String, Object> parserResult = null;
        try {
            parserResult = parser.parseInput(FilesLinks.INPUT_FILE);
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    protected void testParseInputMissingParameter(Parser parser) {
        Map<String, Object> me = createInputMapWithoutStreams();
        Map<String, Object> parserResult = null;
        try {
            parserResult = parser.parseInput(FilesLinks.INPUT_WITHOUTSTREAMS_FILE);
        } catch (IOException e) {
            assertTrue(false);
        }
        assertEquals(me, parserResult);
    }

    protected void testParseInputIncorrecttag(Parser parser) {
        Map<String, Object> me = createDefaultInputMap();
        Map<String, Object> parserResult = null;
        try {
            parserResult = parser.parseInput(FilesLinks.INPUT_VALUEERROR_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    protected void testParseInputValueError(Parser parser) {
        Map<String, Object> me = createDefaultInputMap();
        Map<String, Object> parserResult = null;
        try {
            parserResult = parser.parseInput(FilesLinks.INPUT_WITHOUTFISHES_FILE);
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    protected void testParseFileNotFound(Parser parser) {
        try {
            Map<String, Object> parserResult = parser.parseInput("nonexistent file");
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    protected Map<String, Object> createDefaultConfigureMap() {
        Map<String, Object> resut = new LinkedHashMap<>();
        resut.put("in_parser", "DOM");
        resut.put("out_parser", "DOM");
        resut.put("game_time", "15");
        resut.put("enclosed", "false");
        resut.put("width", "100");
        resut.put("height", "50");
        return resut;
    }

    protected Map<String, Object> createDefaultInputMap() {
        Map<String, Object> resut = new LinkedHashMap<>();
        resut.put("stream_speed0", 1);
        resut.put("stream_start0", 0);
        resut.put("stream_end0", 24);
        resut.put("stream_speed1", -1);
        resut.put("stream_start1", 25);
        resut.put("stream_end1", 49);
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
