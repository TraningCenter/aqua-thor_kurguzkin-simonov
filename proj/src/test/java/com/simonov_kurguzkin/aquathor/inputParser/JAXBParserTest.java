package com.simonov_kurguzkin.aquathor.inputParser;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for JAXB parser tests
 *
 * @author Vladimir
 */
public class JAXBParserTest extends ParserTest {

    private JAXBParser jaxbParser;

    @Before
    public void init() {
        jaxbParser = new JAXBParser();
    }

    @Test
    public void testParseInput() {
        testParseInput(jaxbParser);
    }

    @Test
    public void testParseInputMissingParameter() {
        testParseInputMissingParameter(jaxbParser);
    }

    @Test
    public void testParseInputIncorrecttag() {
        testParseInputIncorrecttag(jaxbParser);
    }

    @Test
    public void testParseInputValueError() {
        testParseInputValueError(jaxbParser);
    }

    @Test
    public void testParseFileNotFound() {
        testParseFileNotFound(jaxbParser);
    }

}
