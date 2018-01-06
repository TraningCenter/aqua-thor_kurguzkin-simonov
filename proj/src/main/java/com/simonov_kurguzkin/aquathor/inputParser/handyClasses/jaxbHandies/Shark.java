package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

/**
 * Handy class for JAXB work with appropriate tag
 * @author Vladimir
 */
public class Shark {
    /**
     * Sharks' time to live parameter and it's get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int time_to_live;
    /**
     * Sharks' speed get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int speed;
    /**
     * Sharks' feeling radius get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int feel_radius;
    /**
     * Sharks' quantity get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int quantity;
    /**
     * Sharks' time of hungry parameter and it's get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int time_to_hungry_live;

    public int getTime_to_hungry_live() {
        return time_to_hungry_live;
    }

    public void setTime_to_hungry_live(int time_to_hungry_live) {
        this.time_to_hungry_live = time_to_hungry_live;
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
