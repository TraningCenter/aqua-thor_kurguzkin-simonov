package com.simonov_kurguzkin.aquathor.auxiliaryUnits;

/**
 * Stream representation
 *
 * @author Eugene
 */
public class StreamView implements EntityView {

    /**
     * Stream start coordinate
     */
    private final int start;
    /**
     * Stream end coordinate
     */
    private final int end;
    /**
     * Stream speed
     */
    private final int speed;

    /**
     * StreamView constructor
     *
     * @param start Stream start coordinate
     * @param end Stream end coordinate
     * @param speed Stream speed
     */
    public StreamView(int start, int end, int speed) {
        this.start = start;
        this.end = end;
        this.speed = speed;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getSpeed() {
        return speed;
    }

}
