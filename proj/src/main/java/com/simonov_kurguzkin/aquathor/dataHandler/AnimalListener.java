package com.simonov_kurguzkin.aquathor.dataHandler;

/**
 * Class that tracks the change in some animal parameters
 * 
 * @author Eugene
 */
public class AnimalListener implements IAnimalListener {

    /**
     * Class that we notify about changes
     */
    private final DataHandler listener;

    /**
     * AnimalListener constructor
     *
     * @param dataHandler object of class that waits for notifications from this
     * class
     */
    public AnimalListener(DataHandler dataHandler) {
        this.listener = dataHandler;
    }

    @Override
    public void animalDie(Animal animal) {
        listener.animalDie(animal);
    }

    @Override
    public void animalReproduct(Animal animal) {
        listener.animalReproduct(animal);
    }

}
