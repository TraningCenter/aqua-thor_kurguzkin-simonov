/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.inputParser;

import com.simonov_kurguzkin.inputParser.ParserTest;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vladimir
 */
public class STAXParserTest extends ParserTest {
    private STAXParser staxParser; 
    
    @Before
    public void init() {
        staxParser = new STAXParser();
    }
    
    @Test
    public void testParseInput() {
        testParseInput(staxParser);
    }

//    @Test
//    public void testParseInputMissingParameter() {
//        testParseInputMissingParameter(staxParser);
//    }
//
//    @Test
//    public void testParseInputIncorrecttag() {
//        testParseInputIncorrecttag(staxParser);
//    }
//
//    @Test
//    public void testParseInputValueError() {
//        testParseInputValueError(staxParser);
//    }
//
//    @Test
//    public void testParseFileNotFound() {
//        testParseFileNotFound(staxParser);
//    }
    
}
