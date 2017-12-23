/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.dataHandler;

/**
 *
 * @author Евгений
 */
public class Shark extends Animal {

    private int timeHungryDeath;

    public Shark(FishCode code, int lifetime, int speed,
            int feelDistance, int reproductFrequency, int timeHungryDeath) {
        super(code, lifetime, speed, feelDistance);
        this.timeHungryDeath = timeHungryDeath;
    }

    @Override
    public void nextAction() {

    }

    @Override
    protected void move() {

    }

    @Override
    protected void reproduct() {

    }

    @Override
    public void due() {

    }

}
