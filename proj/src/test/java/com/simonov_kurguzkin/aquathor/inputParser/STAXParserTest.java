package com.simonov_kurguzkin.aquathor.inputParser;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for STAX parser tests
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

    @Test
    public void testParseInputMissingParameter() {
        testParseInputMissingParameter(staxParser);
    }

    @Test
    public void testParseInputIncorrecttag() {
        testParseInputIncorrecttag(staxParser);
    }

    @Test
    public void testParseInputValueError() {
        testParseInputValueError(staxParser);
    }

    @Test
    public void testParseFileNotFound() {
        testParseFileNotFound(staxParser);
    }

}
