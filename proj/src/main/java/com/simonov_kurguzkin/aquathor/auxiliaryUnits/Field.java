package com.simonov_kurguzkin.aquathor.auxiliaryUnits;

import com.simonov_kurguzkin.aquathor.Controller;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final int MAX_WIDTH;
    /**
     * Maximum allowed height of the playing field
     */
    private final int MAX_HEIGHT;
    /**
     * Maximum number of free cells (used in the case, if the crooks are larger
     * than the cells on the playing field)
     */
    public final int MAX_FREE_CELLS = 3;

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
        //определяем размеры экрана
        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice();
        MAX_WIDTH = gd.getDisplayMode().getWidth() / 10-5;
        MAX_HEIGHT = gd.getDisplayMode().getHeight() / 22-5;
        if (width > MAX_WIDTH) {
            Logger logger = LoggerFactory.getLogger(Controller.class);
            logger.info("The field width is too large in the configuration file. "
                    + "So it was reduced to the maximum possible: " + MAX_WIDTH);
            width = MAX_WIDTH;
        }
        this.width = width;
        if (height > MAX_HEIGHT) {
            Logger logger = LoggerFactory.getLogger(Controller.class);
            logger.info("The field height is too large in the configuration file. "
                    + "So it was reduced to the maximum possible: " + MAX_HEIGHT);
            height = MAX_HEIGHT;
        }
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
