package com.simonov_kurguzkin.aquathor.visualizer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.simonov_kurguzkin.aquathor.Field;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.AnimalView;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.EntityView;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.Snapshot;
import com.simonov_kurguzkin.aquathor.visualizer.visHandies.StreamView;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;


/**
 *
 * @author Vladimir
 */
public class Visualizer {
    private Field field = new Field(true, 10, 5);
    
    public void setField(Field field) {
        this.field = field;
    }
    
    public void visualize(Snapshot snapshot) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        
        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            
            final Window window = new BasicWindow("Aqua-Tor");
            
            LinkedList<AnimalView> animals = new LinkedList<AnimalView>();
            int fishCounter = 0;
            int sharkCounter = 0;
            int flows[] = new int[field.getHeight()];
            for (EntityView ev : snapshot.getViews()) {
                if (ev.getClass().getName().equals("com.simonov_kurguzkin.aquathor.visualizer.visHandies.AnimalView")) {
                    AnimalView av = (AnimalView)ev;
                    animals.add(av);
                    if (av.getIsShark()) sharkCounter++; 
                    else fishCounter++;
                }
                if (ev.getClass().getName().equals("com.simonov_kurguzkin.aquathor.visualizer.visHandies.StreamView")) {
                    StreamView sv = (StreamView)ev;
                    for (int t = sv.getStart(); t < sv.getEnd() + 1; t++) {
                        flows[flows.length - 1 - t] += sv.getSpeed();
                    }
                }
            }
            Collections.sort(animals);
            

            //Stream Panel
            Panel streamPanel = new Panel();
            streamPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
            for (int str = 0; str < field.getHeight(); str++) {
                StringBuilder sb = new StringBuilder();
                int curFlow = flows[str];
                if (curFlow < 0) sb.append("<-");
                else if (curFlow > 0) sb.append("->");
                else sb.append(" -");
                
                double curFlowAbs = Math.abs(curFlow);
                int tenDigit = (int) (curFlowAbs%100/10);
                if (tenDigit == 0) sb.append(" ");
                else sb.append(tenDigit);
                sb.append(curFlowAbs%10);
                
                streamPanel.addComponent(new Label(sb.toString()));
            }
            
            //Grid Panel
            Panel gridPanel = new Panel();
            gridPanel.withBorder(Borders.singleLineReverseBevel());
            gridPanel.setLayoutManager(new GridLayout(field.getWidth()));
            
            for (int i = 0; i < field.getHeight(); i++) {
                for (int j = 0; j < field.getWidth(); j++) {
                    
                    AnimalView curAnimal = animals.peekFirst();
                    if (curAnimal != null) {
                        if (curAnimal.getxCoordinate() == j)
                            if (curAnimal.getyCoordinate() == (field.getHeight() - 1 - i)) {
                                Panel elemPanel = new Panel();
                                gridPanel.addComponent(elemPanel);
                                if (curAnimal.getIsShark()) elemPanel.addComponent(new Label("#"));
                                else elemPanel.addComponent(new Label("+"));
                                animals.pollFirst();
                                continue;
                            }
                    }                        
                    Panel elemPanel = new Panel();
                    gridPanel.addComponent(elemPanel);
                    elemPanel.addComponent(new Label("_"));
                }
            }
            
            
            //Top Panel
            Panel topPanel = new Panel();
            topPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
            topPanel.addComponent(streamPanel.withBorder(Borders.singleLine()));
            topPanel.addComponent(gridPanel.withBorder(Borders.doubleLine()));
            
            //Information Panel
            Panel infoPanel = new Panel();
            infoPanel.setLayoutManager(new GridLayout(2));
            infoPanel.addComponent(new Label("Fish alive:"));
            infoPanel.addComponent(new Label(((Integer)fishCounter).toString()));
            infoPanel.addComponent(new Label("Sharks alive:"));
            infoPanel.addComponent(new Label(((Integer)sharkCounter).toString()));
            infoPanel.addComponent(new Label("Step:"));
            infoPanel.addComponent(new Label(((Integer)snapshot.getIterationNum()).toString()));
            
            //OverAll Panel
            Panel overallPanel = new Panel();
            overallPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
            overallPanel.addComponent(topPanel);
            overallPanel.addComponent(infoPanel);
            
            window.setComponent(overallPanel.withBorder(Borders.doubleLineBevel()));
            
            textGUI.addWindowAndWait(window);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if(screen != null) {
                try {
                    screen.stopScreen();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
