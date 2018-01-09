package com.simonov_kurguzkin.aquathor.visualizer;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.AnimalView;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.EntityView;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Snapshot;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.StreamView;
import com.simonov_kurguzkin.aquathor.dataHandler.AnimalCode;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Vladimir
 */
public class VisualizerTest {

    private Visualizer vis;

    @Before
    public void init() {
        try {
            vis = new Visualizer(new Field(true, 10, 20));
        } catch (IOException ex) {
            Logger logger = LoggerFactory.getLogger(VisualizerTest.class);
            logger.error("Error during trying to visualize picture in test");
        }
    }

    /**
     * Test of visualize method, of class Visualizer.
     */
    @Test
    public void testVisualize() {
        EntityView av1 = new AnimalView(AnimalCode.SHARK, 1, 1);
        EntityView av2 = new AnimalView(AnimalCode.FISH, 5, 3);
        EntityView av3 = new AnimalView(AnimalCode.FISH, 7, 8);
        EntityView sv1 = new StreamView(0, 1, 3);
        EntityView sv2 = new StreamView(1, 4, -2);
        Snapshot snapshot = new Snapshot(Arrays.asList(av1, av2, av3, sv1, sv2), 5);
        try {
            vis.visualize(snapshot);
            Thread.sleep(100);
            vis.closeScreen();
        } catch (Exception ex) {
            fail("Wrong");
        }
        assertTrue(true);
    }

}
