package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

/**
 * Handy class for JAXB work with appropriate tag
 * @author Vladimir
 */
public class Animal {
    /**
     * Fish get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private Fish fish;
    /**
     * Shark get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private Shark shark;

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public Shark getShark() {
        return shark;
    }

    public void setShark(Shark shark) {
        this.shark = shark;
    }
}