package com.simonov_kurguzkin.aquathor;

import AuxiliaryClasses.FilesLinks;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Class for testing the controller class
 *
 * @author Eugene
 */
public class ControllerTest {

//    @Test
//    public void testWork() {
//        test(FilesLinks.CONFIG_FILE, FilesLinks.INPUT_FILE);
//    }
//
//    @Test
//    public void testWorkWithoutStreamsFile() {
//        test(FilesLinks.CONFIG_FILE, FilesLinks.INPUT_WITHOUTSTREAMS_FILE);
//    }
    /**
     * Test to check the correctness of the class with incorrect input data
     */
    @Test
    public void testWorkWithIncorrectFiles() {
        test(FilesLinks.CONFIG_TAGERROR_FILE, FilesLinks.INPUT_FILE);
        test(FilesLinks.CONFIG_WITHOUTTAGERROR_FILE, FilesLinks.INPUT_FILE);

        test(FilesLinks.CONFIG_FILE, FilesLinks.INPUT_TAGERROR_FILE);
        test(FilesLinks.CONFIG_FILE, FilesLinks.INPUT_VALUEERROR_FILE);
        test(FilesLinks.CONFIG_FILE, FilesLinks.INPUT_WITHOUTFISHES_FILE);
    }

    /**
     * Auxiliary test method
     *
     * @param config Config file name
     * @param input Input file name
     */
    private void test(String config, String input) {
        //проверяем, чтобы контроллер нормально реагировал на некорректные данные
        Controller controller = new Controller(config, input, "", "");
        try {
            controller.work();
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

}
