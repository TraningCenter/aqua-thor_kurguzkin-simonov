/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor;

import com.simonov_kurguzkin.aquathor.visualizer.Visualizer;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.AnimalView;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.EntityView;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.Snapshot;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.StreamView;
import java.util.Arrays;

/**
 *
 * @author Евгений
 */
public class App {
    
    public static void main(String[] args) {        
        System.out.println("Hello world!");
//        Controller controller=new Controller();
//        controller.work();
        Visualizer vis = new Visualizer();
        vis.setField(new Field(true, 10, 10));
        EntityView av1 = new AnimalView(true, 1, 1);
        EntityView av2 = new AnimalView(false, 5, 3);
        EntityView av3 = new AnimalView(false, 7, 8);
        EntityView sv1 = new StreamView(0, 1, 3); 
        EntityView sv2 = new StreamView(1, 4, -2);
        vis.visualize(new Snapshot(Arrays.asList(av1, av2,av3, sv1, sv2), 3));
    }
    
}
