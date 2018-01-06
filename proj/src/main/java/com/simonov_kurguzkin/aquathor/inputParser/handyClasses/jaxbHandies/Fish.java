package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

/**
 * Handy class for JAXB work with appropriate tag
 * @author Vladimir
 */
public class Fish {
    /**
     * Lifetime parameter and it's get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int time_to_live;
    /**
     * Fish speed get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int speed;
    /**
     * Fish radius of feelings get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int feel_radius;
    /**
     * Fish quantity get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int quantity;
    /**
     * Fish reproduction frequency get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int reproduction_frequency;

    public int getReproduction_frequency() {
        return reproduction_frequency;
    }

    public void setReproduction_frequency(int reproduction_frequency) {
        this.reproduction_frequency = reproduction_frequency;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFeel_radius() {
        return feel_radius;
    }

    public void setFeel_radius(int feel_radius) {
        this.feel_radius = feel_radius;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTime_to_live() {
        return time_to_live;
    }

    public void setTime_to_live(int time_to_live) {
        this.time_to_live = time_to_live;
    }
}

