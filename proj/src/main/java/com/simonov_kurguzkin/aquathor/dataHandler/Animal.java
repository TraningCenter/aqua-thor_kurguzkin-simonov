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
public abstract class Animal {

    protected FishCode code;
    protected boolean alive;
    protected int lifetime;
    protected int speed;
    protected int feelDistance;
    protected int x;
    protected int y;

    public Animal(FishCode code, int lifetime, int speed, int feelDistance) {
        this.code = code;
        this.lifetime = lifetime;
        this.speed = speed;
        this.feelDistance = feelDistance;
        this.alive = true;
//        this.x=random;
//        this.y=random;        
    }

    public abstract void nextAction();

    protected abstract void move();

    protected abstract void reproduct();

    protected abstract void due();

}
