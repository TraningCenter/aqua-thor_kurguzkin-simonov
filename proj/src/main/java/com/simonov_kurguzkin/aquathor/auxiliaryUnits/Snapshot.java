package com.simonov_kurguzkin.aquathor.auxiliaryUnits;

import java.util.List;

/**
 * Class for displaying the current state of the system
 *
 * @author Eugene
 */
public class Snapshot {

    /**
     * Representation of each system unit
     */
    private final List<EntityView> views;
    /**
     * Number of the current iteration
     */
    private final int iterationNum;

    /**
     * Snapshot constructor
     *
     * @param views List of system unit views
     * @param iterationNum Number of iteration
     */
    public Snapshot(List<EntityView> views, int iterationNum) {
        this.views = views;
        this.iterationNum = iterationNum;
    }

    public List<EntityView> getViews() {
        return views;
    }

    public int getIterationNum() {
        return iterationNum;
    }

}
