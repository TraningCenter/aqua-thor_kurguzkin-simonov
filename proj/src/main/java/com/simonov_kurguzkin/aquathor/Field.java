/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor;

/**
 *
 * @author Евгений
 */
public class Field {

    private final int height;
    private final int width;
    private final boolean isClosed;
    private final int MAX_WIDTH = 100;
    private final int MAX_HEIGHT = 50;

    public Field(boolean isClosed, int width, int height) {
        this.isClosed = isClosed;
        if (width > MAX_WIDTH) {
            width = MAX_WIDTH;
        }
        this.width = width;
        if (height > MAX_HEIGHT) {
            height = MAX_HEIGHT;
        }
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isIsClosed() {
        return isClosed;
    }

}
