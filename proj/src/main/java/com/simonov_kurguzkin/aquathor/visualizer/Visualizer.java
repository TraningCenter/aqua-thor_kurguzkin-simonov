package com.simonov_kurguzkin.aquathor.visualizer;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.AnimalView;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.EntityView;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Snapshot;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.StreamView;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Whole functionality for simulation demonstration
 *
 * @author Vladimir
 */
public class Visualizer {

    /**
     * Basic gamefield Fields's size is 10*5 by default
     */
    private Field field = new Field(true, 10, 5);
    /**
     * Screen to show simulation
     */
    private Screen screen = null;
    /**
     * Lanterna's factory, producing new screen which size is set in this
     * factory
     */
    private DefaultTerminalFactory terminalFactory;
    /**
     * Lanterna's TextGraphics which construct graphics before screen refreshing
     * Must be connected to screen in use
     */
    private TextGraphics tg;
    /**
     * Animal's (Fish and Sharks) list after Animal/Stream filtering
     */
    private LinkedList<AnimalView> animals;
    /**
     * Stream's array with exact stream speed for each after Animal/Stream
     * filtering
     */
    private int flows[];
    /**
     * Fish quantity during one game iteration
     */
    private int fishCounter;
    /**
     * Sharks quantity during one game iteration
     */
    private int sharkCounter;

    /**
     * Constructor sets obligatory field (from parameters), set's size of
     * terminal, creates appropriate screen, starts it
     *
     * @param field
     * @throws IOException
     */
    public Visualizer(Field field) throws IOException {
        this.field = field;

        terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(13 + field.getWidth(), 12 + field.getHeight()));
        screen = terminalFactory.createScreen();
        screen.startScreen();
    }

    /**
     * Visualize method calls methods to show one-iteration-situation, which is
     * sent in parameters as snapshot
     *
     * @param snapshot
     */
    public void visualize(Snapshot snapshot) {

        try {
            collectionsHandler(snapshot);
            tg = screen.newTextGraphics();
            drawTitle();
            drawStreamFrame();
            setAquariumGrid();
            setFishNSharks();
            drawInfoSection(snapshot);
            screen.refresh();
        } catch (Exception ex) {
            Logger logger = LoggerFactory.getLogger(Visualizer.class);
            logger.error("Error occurred during visualizing snapshot");
            ex.printStackTrace();
            if (screen != null) {
                try {
                    screen.close();
                } catch (Exception inner_ex) {
                    logger.error("Error occurred while trying to close screen after exception in visualizer");
                    inner_ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Method which closes screen if it presents or throws an IOException
     *
     * @throws IOException
     */
    public void closeScreen() throws IOException {
        if (screen != null)
            screen.close();
        else
            throw new IOException("No screen to close");
    }

    /**
     * Execute Stream/Animal filtering Counts fish and sharks, sets appropriate
     * values into the counters Sets correct stream speed values for every
     * stream It gets all information from snapshot
     *
     * @param snapshot
     */
    private void collectionsHandler(Snapshot snapshot) {
        animals = new LinkedList<>();
        fishCounter = 0;
        sharkCounter = 0;
        flows = new int[field.getHeight()];
        for (EntityView ev : snapshot.getViews()) {
            if (ev.getClass().getSimpleName().equals("AnimalView")) {
                AnimalView av = (AnimalView) ev;
                animals.add(av);
                if (av.getIsShark()) {
                    sharkCounter++;
                } else {
                    fishCounter++;
                }
            }
            if (ev.getClass().getSimpleName().equals("StreamView")) {
                StreamView sv = (StreamView) ev;
                for (int t = sv.getStart(); t < sv.getEnd() + 1; t++) {
                    flows[flows.length - 1 - t] += sv.getSpeed();
                }
            }
        }
        Collections.sort(animals);
    }

    /**
     * Method draws the title board
     */
    private void drawTitle() {
        tg.putString(new TerminalPosition(16, 8 + field.getHeight()), "           ");
        tg.putString(new TerminalPosition(16, 9 + field.getHeight()), "           ");
        tg.drawRectangle(new TerminalPosition(5, 0), new TerminalSize(12, 3), new TextCharacter('*'));
        tg.putString(new TerminalPosition(7, 1), "Aqua-Tor");
    }

    /**
     * Method draws frame with streams with correct directions and digits
     */
    private void drawStreamFrame() {
        tg.drawRectangle(new TerminalPosition(2, 4), new TerminalSize(4, 1), '-');
        tg.drawRectangle(new TerminalPosition(2, 5 + field.getHeight()), new TerminalSize(4, 1), '-');
        tg.drawRectangle(new TerminalPosition(1, 5), new TerminalSize(1, field.getHeight()), '|');
        tg.drawRectangle(new TerminalPosition(6, 5), new TerminalSize(1, field.getHeight()), '|');

        for (int str = 0; str < field.getHeight(); str++) {
            StringBuilder sb = new StringBuilder();
            int curFlow = flows[str];
            if (curFlow < 0) {
                sb.append("<-");
            } else if (curFlow > 0) {
                sb.append("->");
            } else {
                sb.append(" -");
            }

            double curFlowAbs = Math.abs(curFlow);
            int tenDigit = (int) (curFlowAbs % 100 / 10);
            int oneDigit = (int) (curFlowAbs % 10);
            if (tenDigit == 0) {
                sb.append(" ");
            } else {
                sb.append(tenDigit);
            }
            sb.append(oneDigit);

            tg.putString(new TerminalPosition(2, 5 + str), sb.toString());
        }
    }

    /**
     * Method draws aquarium frame and fills it with water
     */
    private void setAquariumGrid() {
        tg.drawRectangle(new TerminalPosition(9, 4), new TerminalSize(field.getWidth(), 1), '-');
        tg.drawRectangle(new TerminalPosition(9, 5 + field.getHeight()), new TerminalSize(field.getWidth(), 1), '-');
        tg.drawRectangle(new TerminalPosition(8, 5), new TerminalSize(1, field.getHeight()), '|');
        tg.drawRectangle(new TerminalPosition(9 + field.getWidth(), 5), new TerminalSize(1, field.getHeight()), '|');
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.fillRectangle(new TerminalPosition(9, 5), new TerminalSize(field.getWidth(), field.getHeight()), '~');
    }

    /**
     * Method puts all fish and sharks into right places
     */
    private void setFishNSharks() {
        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                if (!animals.isEmpty()) {
                    AnimalView curAnimal = animals.peekFirst();
                    if (curAnimal.getxCoordinate() == j) {
                        if (curAnimal.getyCoordinate() == (field.getHeight() - 1 - i)) {
                            if (curAnimal.getIsShark()) {
                                tg.setForegroundColor(TextColor.ANSI.RED);
                                tg.putString(new TerminalPosition(9 + j, 5 + i), "#");
                            } else {
                                tg.setForegroundColor(TextColor.ANSI.GREEN);
                                tg.putString(new TerminalPosition(9 + j, 5 + i), "+");
                            }
                            animals.pollFirst();
                            while (!animals.isEmpty()) {
                                curAnimal = animals.peekFirst();
                                if ((curAnimal.getxCoordinate() == j) && (curAnimal.getyCoordinate() == (field.getHeight() - 1 - i)))
                                    animals.pollFirst();
                                else
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method show information about current step under stream & aquarium
     * frames. It gets the correct step number from the snapshot parameter
     *
     * @param snapshot
     */
    private void drawInfoSection(Snapshot snapshot) {
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString(new TerminalPosition(2, 8 + field.getHeight()), "Fish alive:");
        tg.putString(new TerminalPosition(2, 9 + field.getHeight()), "Sharks alive:");
        tg.putString(new TerminalPosition(2, 10 + field.getHeight()), "Step:");
        tg.putString(new TerminalPosition(16, 8 + field.getHeight()), ((Integer) fishCounter).toString());
        tg.putString(new TerminalPosition(16, 9 + field.getHeight()), ((Integer) sharkCounter).toString());
        tg.putString(new TerminalPosition(16, 10 + field.getHeight()), ((Integer) snapshot.getIterationNum()).toString());
    }
}
