package com.simonov_kurguzkin.aquathor.dataHandler;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for describing fishes
 *
 * @author Eugene
 */
public class Fish extends Animal {

    /**
     * Fish frequency of reproduction
     */
    private final int reproductFrequency;

    /**
     * Fish constructor
     *
     * @param reproductFrequency Fish frequency of reproduction
     * @param lifetime Maximum fish life time
     * @param speed Fish movement speed
     * @param feelDistance Distance at which the fish feels sharks
     * @param x X coordinate
     * @param y Y coordinate
     * @param iAnimalListener Object of the class that we will notify you of a
     * change in state
     */
    public Fish(int reproductFrequency, int lifetime,
            int speed, int feelDistance, int x, int y, IAnimalListener iAnimalListener) {
        super(AnimalCode.FISH, lifetime, speed, feelDistance, x, y, iAnimalListener);
        this.reproductFrequency = reproductFrequency;
    }

    @Override
    public void nextAction(List<Animal> animals, List<Stream> streams, Field field) {
        if (age >= lifetime) {
            die();
            return;
        }
        if (age % reproductFrequency == 0 && age != 0)
            if (alive)
                reproduct();
        move(animals, streams, field);
        age++;
    }

    @Override
    public void reproduct() {
        listener.animalReproduct(new Fish(reproductFrequency, lifetime,
                speed, feelDistance, x, y, listener));
    }

    /**
     * Fish movement method
     *
     * @param animals List of all animals
     * @param streams List of all streams
     * @param field Game field
     */
    private void move(List<Animal> animals, List<Stream> streams, Field field) {
        for (int i = 0; i < speed; i++) {
            List<Animal> nears = findAnimalsNear(animals, field);
            List<Animal> sharks = findOpponentsNear(animals, AnimalCode.SHARK);
            //если рядом нет акул, просто плывем
            if (sharks.isEmpty())
                simpleMove(animals, field, generateDirections());
            //если рядом акула, уплываем
            else
                simpleMove(animals, field, generateRunawayDirection(sharks, field));
        }
        //добавляем движение по течению
        moveOnStream(animals, streams, field);
    }

    /**
     * Method for the movement of fish on stream
     *
     * @param animals List of all animals
     * @param streams List of all streams
     * @param field Game field
     */
    private void moveOnStream(List<Animal> animals, List<Stream> streams, Field field) {
        List<Stream> activeStreams = streams.stream()
                .filter(s -> y >= s.getStartCoordinate() && y <= s.getEndCoordinate())
                .collect(Collectors.toList());
        for (Stream s : activeStreams) {
            List<Integer> cellCoordinates = getCellCoordinates(x + s.getSpeed(), y, field);
            if (cellCoordinates == null)
                break;
            int newX = cellCoordinates.get(0);
            if (checkCellFree(animals, newX, y))
                x = newX;
        }
    }

    /**
     * Method for determining the directions for run away from sharks
     *
     * @param sharks List of sharks from which the fish escapes
     * @param field Game field
     * @return List directions
     */
    private List<Integer> generateRunawayDirection(List<Animal> sharks, Field field) {
        List<Integer> directions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        //удаляем направления, в которых находятся акулы
        for (Animal s : sharks)
            directions.removeAll(getDirections(s.getX(), s.getY()));

        //если акулы со всех сторон, движемся в любую сторону
        if (directions.isEmpty())
            directions = generateDirections();
        else
            Collections.shuffle(directions);
        return directions;
    }

    /**
     * Method for generate directions in which sharks are located
     *
     * @param sharkX Shark X coordinate
     * @param sharkY Shark Y coordinate
     * @return List of directions
     */
    private List<Integer> getDirections(int sharkX, int sharkY) {
        List<Integer> directions = new ArrayList<>();
        if (y < sharkY)
            directions.add(0);
        if (x < sharkX)
            directions.add(1);
        if (y > sharkY)
            directions.add(2);
        if (x > sharkX)
            directions.add(3);
        return directions;
    }

}
