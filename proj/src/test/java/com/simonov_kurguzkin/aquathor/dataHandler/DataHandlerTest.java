package com.simonov_kurguzkin.aquathor.dataHandler;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.EntityView;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Snapshot;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Statistics;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the work of DataHandler class
 *
 * @author Eugene
 */
public class DataHandlerTest {

    /**
     * DataHandler object
     */
    DataHandler dataHandler;
    /**
     * Default number of streams
     */
    int numStreams;
    /**
     * Default number of fishes
     */
    int numFishes;
    /**
     * Default number of sharks
     */
    int numSharks;

    /**
     * Initialize method
     */
    @Before
    public void setUp() {
        numStreams = 2;
        numFishes = 20;
        numSharks = 10;
        dataHandler = new DataHandler(createDefaultMap(), new Field(true, 20, 20));
    }

    /**
     * Test for generating snapshot
     */
    @Test
    public void testGenerateSnapshot() {
        Snapshot snapshot = dataHandler.generateSnapshot();
        List<EntityView> entities = snapshot.getViews();
        int numStreams = 0, numAnimals = 0;
        for (EntityView e : entities) {
            switch (e.getClass().getSimpleName()) {
                case ("StreamView"):
                    numStreams++;
                    break;
                case ("AnimalView"):
                    numAnimals++;
                    break;
                default:
                    assertTrue(false);
            }
        }
        if (numAnimals == this.numFishes + this.numSharks
                && numStreams == this.numStreams)
            assertTrue(true);
        else
            assertTrue(false);
    }

    /**
     * Test for generating statistics
     */
    @Test
    public void testGenerateStatistics() {
        Statistics me = new Statistics(0, numFishes, numSharks);
        Statistics prog = dataHandler.generateStatistics();
        //assertEquals(me, prog);
        if (me.getIterationStep() == prog.getIterationStep()
                && me.getFishAmount() == prog.getFishAmount()
                && me.getSharksAmount() == prog.getSharksAmount()) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    /**
     * Test for method of processing next step
     */
    @Test
    public void testProcessNextStep() {
        for (int i = 0; i < 100; i++) {
            try {
                dataHandler.processNextStep();
            } catch (Exception ex) {
                assertTrue(false);
            }
            assertTrue(true);
        }
    }

    /**
     * Auxiliary method for creating default data
     *
     * @return Default data map
     */
    private Map<String, Object> createDefaultMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("stream_speed0", 1);
        result.put("stream_start0", 0);
        result.put("stream_end0", 7);
        result.put("stream_speed1", -2);
        result.put("stream_start1", 15);
        result.put("stream_end1", 19);

        result.put("fish_quantity", numFishes);
        result.put("fish_reproduction", 3);
        result.put("fish_live", 10);
        result.put("fish_speed", 2);
        result.put("fish_radius", 4);

        result.put("shark_quantity", numSharks);
        result.put("shark_live", 20);
        result.put("shark_hungry", 7);
        result.put("shark_speed", 2);
        result.put("shark_radius", 5);

        return result;
    }

}
