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
public class Stream extends Entity {
    
    private final int speed;
    private final int startCoordinate;
    private final int endCoordinate;

    public Stream(int speed, int startCoordinate, int endCoordinate) {
        this.speed = speed;
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStartCoordinate() {
        return startCoordinate;
    }

    public int getEndCoordinate() {
        return endCoordinate;
    }  
    
}
