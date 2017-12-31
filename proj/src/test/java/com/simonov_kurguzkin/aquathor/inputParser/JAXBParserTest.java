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
public class JAXBParserTest extends ParserTest{
    
    private JAXBParser jaxbParser; 
    
    @Before
    public void init() {
        jaxbParser = new JAXBParser();
    }
    
    @Test
    public void testParseInput() {
        testParseInput(jaxbParser);
    }

//    @Test
//    public void testParseInputMissingParameter() {
//        testParseInputMissingParameter(jaxbParser);
//    }
//
//    @Test
//    public void testParseInputIncorrecttag() {
//        testParseInputIncorrecttag(jaxbParser);
//    }
//
//    @Test
//    public void testParseInputValueError() {
//        testParseInputValueError(jaxbParser);
//    }
//
//    @Test
//    public void testParseFileNotFound() {
//        testParseFileNotFound(jaxbParser);
//    }
    
}
