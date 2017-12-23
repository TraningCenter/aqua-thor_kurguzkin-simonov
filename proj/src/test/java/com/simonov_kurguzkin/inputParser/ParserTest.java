/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.inputParser;

import com.simonov_kurguzkin.aquathor.inputParser.DOMParser;
import com.simonov_kurguzkin.aquathor.inputParser.Parser;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Евгений
 */
public class ParserTest {

    Parser domParser;
    Parser saxParser;
    Parser jaxbParser;
    Parser staxParser;

    @Before
    public void init() {
        domParser = new DOMParser();
        //other parsers
    }

    @Test
    public void DOMparseConfigureTest() {
        Map<String, Object> me = createDefaultConfigureMap();
        Map<String, Object> dom = null;
        try {
            dom = domParser.parseConfigure("src/main/resources/test_config.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            assertTrue(false);
        }
        assertEquals(me, dom);
    }

    @Test
    public void DOMparseInputTest() {
        Map<String, Object> me = createDefaultInputMap();
        Map<String, Object> dom = null;
        try {
            dom = domParser.parseInput("src/main/resources/test_input.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            assertEquals(me, dom);
        }
        assertEquals(me, dom);
    }
    
    

    @Test
    public void parseExceptionTest() {
        try {
            domParser.parseConfigure("configure.xml");
            domParser.parseConfigure("src/main/resources/noup.xml");
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private Map<String, Object> createDefaultConfigureMap() {
        Map<String, Object> resut = new LinkedHashMap<>();
        resut.put("in_parser", "DOM");
        resut.put("out_parser", "DOM");
        resut.put("game_time", "15");
        resut.put("enclosed", "false");
        resut.put("width", "100");
        resut.put("height", "50");
        return resut;
    }

    private Map<String, Object> createDefaultInputMap() {
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
}
