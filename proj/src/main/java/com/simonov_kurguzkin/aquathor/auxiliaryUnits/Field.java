package com.simonov_kurguzkin.aquathor.auxiliaryUnits;

/**
 * Class that contains game field data
 *
 * @author Eugene
 */
public class Field {

    /**
     * Height of the playing field
     */
    private final int height;
    /**
     * Width of the playing field
     */
    private final int width;
    /**
     * Is the field open or closed on the sides
     */
    private final boolean isClosed;
    /**
     * Maximum allowed width of the playing field
     */
    private final int MAX_WIDTH = 50;
    /**
     * Maximum allowed height of the playing field
     */
    private final int MAX_HEIGHT = 50;

    /**
     * Field constructor
     *
     * @param isClosed Shows an open field on the sides or closed (true-open,
     * false-close)
     * @param width Width of the playing field
     * @param height Height of the playing field
     */
    public Field(boolean isClosed, int width, int height) {
        this.isClosed = isClosed;
        if (width > MAX_WIDTH)
            width = MAX_WIDTH;
        this.width = width;
        if (height > MAX_HEIGHT)
            height = MAX_HEIGHT;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean getClosed() {
        return isClosed;
    }

}
