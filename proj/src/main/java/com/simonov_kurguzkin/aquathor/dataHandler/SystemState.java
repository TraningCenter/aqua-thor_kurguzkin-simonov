/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.dataHandler;

import java.util.List;

/**
 *
 * @author Евгений
 */
public class SystemState {

    private final boolean fieldClosed;
    private final int height;
    private final int width;
    List<Animal> animals;
    List<Stream> streams;
    private int time;

    public SystemState(boolean fieldClosed, int height, int width,
            List<Animal> animals, List<Stream> streams) {
        this.fieldClosed = fieldClosed;
        this.height = height;
        this.width = width;
        this.animals = animals;
        this.streams = streams;
    }

    public boolean isFieldClosed() {
        return fieldClosed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public int getTime() {
        return time;
    }

}
