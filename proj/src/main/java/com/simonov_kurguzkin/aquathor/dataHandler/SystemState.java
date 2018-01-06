package com.simonov_kurguzkin.aquathor.dataHandler;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing system state
 *
 * @author Eugene
 */
public class SystemState {

    /**
     * Is the playing field closed
     */
    private final boolean fieldClosed;
    /**
     * Game field height
     */
    private final int height;
    /**
     * Game field width
     */
    private final int width;
    /**
     * List of streams
     */
    private final List<Stream> streams;
    /**
     * List of animals
     */
    private final List<Animal> animals;
    /**
     * List of dead animals
     */
    private final List<Animal> deadAnimals;
    /**
     * List of dorn animals
     */
    private final List<Animal> bornAnimals;

    /**
     * SystemState constructor
     *
     * @param streams List of all streams
     * @param animals List of all animals
     * @param field Game field
     */
    public SystemState(List<Stream> streams, List<Animal> animals, Field field) {
        this.streams = streams;
        this.animals = animals;
        this.fieldClosed = field.getClosed();
        this.height = field.getHeight();
        this.width = field.getWidth();
        this.deadAnimals = new ArrayList<>();
        this.bornAnimals = new ArrayList<>();
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

    public void addDeadedAnimal(Animal animal) {
        deadAnimals.add(animal);
    }

    public void addBornAnimal(Animal animal) {
        bornAnimals.add(animal);
    }

    /**
     * Method for updating all parameters of system state
     */
    public void updateSystemState() {
        animals.removeAll(deadAnimals);
        deadAnimals.removeAll(deadAnimals);

        for (Animal a : bornAnimals) {
            if (a.checkCanBeBorn(animals))
                animals.add(a);
        }
        bornAnimals.removeAll(bornAnimals);
    }

}
