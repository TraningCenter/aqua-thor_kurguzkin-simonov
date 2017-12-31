/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.visualizer.visHandies;

import com.simonov_kurguzkin.aquathor.Field;
import java.util.List;

/**
 *
 * @author q
 */
public class Snapshot {
    private final List<EntityView> views;
    private final int iterationNum;

    public Snapshot(List<EntityView> views, int iterationNum) {
        this.views = views;
        this.iterationNum = iterationNum;
    }

    public List<EntityView> getViews() {
        return views;
    }

    public int getIterationNum() {
        return iterationNum;
    }
}
