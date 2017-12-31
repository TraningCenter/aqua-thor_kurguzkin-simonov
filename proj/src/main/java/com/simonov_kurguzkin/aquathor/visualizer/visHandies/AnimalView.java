package com.simonov_kurguzkin.aquathor.visualizer.visHandies;

/**
 *
 * @author q
 */
public class AnimalView implements  EntityView, Comparable<AnimalView> {
    private final boolean isShark;
    private final int xCoordinate;
    private final int yCoordinate;

    public AnimalView(boolean isShark, int xCoordinate, int yCoordinate) {
        this.isShark = isShark;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public boolean getIsShark() {
        return isShark;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public int compareTo(AnimalView that) {
        if (this.getyCoordinate() > that.getyCoordinate()) return -1;
        if (this.getyCoordinate() == that.getyCoordinate()) {
            if (this.getxCoordinate() < that.getxCoordinate()) return -1;
            if (this.getxCoordinate() == that.getxCoordinate()) return 0; 
        }
        return 1;
    }
}
