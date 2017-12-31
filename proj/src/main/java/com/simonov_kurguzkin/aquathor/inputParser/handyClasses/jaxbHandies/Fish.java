/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

/**
 *
 * @author Vladimir
 */
public class Fish {
    private int time_to_live;
    private int speed;
    private int feel_radius;
    private int quantity;
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
