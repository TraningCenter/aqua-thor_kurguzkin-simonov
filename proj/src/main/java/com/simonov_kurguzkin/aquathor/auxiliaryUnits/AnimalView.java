package com.simonov_kurguzkin.aquathor.auxiliaryUnits;

import com.simonov_kurguzkin.aquathor.dataHandler.AnimalCode;

/**
 * Animal representation
 *
 * @author Vladimir
 */
public class AnimalView implements EntityView, Comparable<AnimalView> {

    /**
     * Animal mark: Fish or Shark
     */
    private final AnimalCode animalCode;
    /**
     * X ocordinate
     */
    private final int xCoordinate;
    /**
     * X ocordinate
     */
    private final int yCoordinate;

    /**
     * AnimalView constructor
     *
     * @param animalCode Animal mark: Fish or Shark
     * @param xCoordinate X ocordinate
     * @param yCoordinate X ocordinate
     */
    public AnimalView(AnimalCode animalCode, int xCoordinate, int yCoordinate) {
        this.animalCode = animalCode;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public AnimalCode getAnimalCode() {
        return animalCode;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public boolean getIsShark() {
        return animalCode == AnimalCode.SHARK;
    }

    @Override
    public int compareTo(AnimalView that) {
        if (this.getyCoordinate() > that.getyCoordinate())
            return -1;
        if (this.getyCoordinate() == that.getyCoordinate()) {
            if (this.getxCoordinate() < that.getxCoordinate())
                return -1;
            if (this.getxCoordinate() == that.getxCoordinate())
                return 0;
        }
        return 1;
    }

}
