package com.simonov_kurguzkin.aquathor.auxiliaryUnits;

/**
 * Statistics of the current state of the system
 *
 * @author Eugene
 */
public class Statistics {

    /**
     * Iteration number
     */
    private final int iterationStep;
    /**
     * Number of live fish
     */
    private final int fishAmount;
    /**
     * Number of live sharks
     */
    private final int sharksAmount;

    /**
     * Statistics constructor
     * @param iterationStep Iteration number
     * @param fishAmount Number of live fish
     * @param sharksAmount Number of live sharks
     */
    public Statistics(int iterationStep, int fishAmount, int sharksAmount) {
        this.iterationStep = iterationStep;
        this.fishAmount = fishAmount;
        this.sharksAmount = sharksAmount;
    }

    public int getIterationStep() {
        return iterationStep;
    }

    public int getFishAmount() {
        return fishAmount;
    }

    public int getSharksAmount() {
        return sharksAmount;
    }

}
