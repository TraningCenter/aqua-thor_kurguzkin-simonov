package com.simonov_kurguzkin.aquathor.dataHandler;

/**
 * Class for describing streams
 *
 * @author Eugene
 */
public class Stream extends Entity {

    /**
     * Stream speed (positive speed: left to right, negative speed: right to
     * left)
     */
    private final int speed;
    /**
     * Stream start coordinate
     */
    private final int startCoordinate;
    /**
     * Stream end coordinate
     */
    private final int endCoordinate;

    /**
     * Stream constructor
     *
     * @param speed Stream speed
     * @param startCoordinate Stream start coordinate
     * @param endCoordinate Stream end coordinate
     */
    public Stream(int speed, int startCoordinate, int endCoordinate) {
        this.speed = speed;
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStartCoordinate() {
        return startCoordinate;
    }

    public int getEndCoordinate() {
        return endCoordinate;
    }

}
