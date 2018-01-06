package com.simonov_kurguzkin.aquathor.dataHandler;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class for describing animals: fish and sharks
 *
 * @author Eugene
 */
public abstract class Animal extends Entity {

    /**
     * Animal mark: fish or shark
     */
    protected AnimalCode code;
    /**
     * Is a fish alive (true) or dead (false)
     */
    protected boolean alive;
    /**
     * Maximum life time
     */
    protected int lifetime;
    /**
     * Animal speed
     */
    protected int speed;
    /**
     * Maximum distance on which the animal feels enemies
     */
    protected int feelDistance;
    /**
     * Animal X coordinate
     */
    protected int x;
    /**
     * Animal Y coordinate
     */
    protected int y;
    /**
     * Animal age
     */
    protected int age;
    /**
     * Class that reacts to certain changes in the state of the animal
     */
    protected IAnimalListener listener;
    /**
     * Variable to generate a random number
     */
    protected Random rnd;

    public AnimalCode getCode() {
        return code;
    }

    public boolean getAlive() {
        return alive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Animal constructor
     *
     * @param code Mark: fish or shark
     * @param lifetime Maximum life time
     * @param speed Animal speed
     * @param feelDistance Maximum distance on which the animal feels enemies
     * @param x Animal X coordinate
     * @param y Animal Y coordinate
     * @param iAnimalListener object of class that reacts to certain changes in
     * the state of the animal
     */
    public Animal(AnimalCode code, int lifetime, int speed,
            int feelDistance, int x, int y, IAnimalListener iAnimalListener) {
        this.code = code;
        this.lifetime = lifetime;
        this.speed = speed;
        this.feelDistance = feelDistance;
        this.alive = true;
        this.x = x;
        this.y = y;
        this.age = 0;
        this.listener = iAnimalListener;
        rnd = new Random();
    }

    /**
     * Method for the implementation of animal following the action
     *
     * @param animals List of all available animals (needed, since the actions
     * of each animal depend on the parameters of the otasimal animals)
     * @param streams List of streams in aquarium
     * @param field Game field
     */
    public abstract void nextAction(List<Animal> animals, List<Stream> streams, Field field);

    /**
     * Method for reproduction of animal
     */
    protected abstract void reproduct();

    /**
     * Method for die of animal
     */
    protected void die() {
        alive = false;
        listener.animalDie(this);
    }

    /**
     * Method for searching for all animals located in the zone of visibility
     *
     * @param animals List of all animals
     * @param field Game field
     * @return List of animals nearby
     */
    protected List<Animal> findAnimalsNear(List<Animal> animals, Field field) {
        List<Animal> result = new ArrayList<>();
        for (Animal a : animals) {
            if (this.equals(a))
                continue;
            int distX = Math.abs(x - a.x);
            if (!field.getClosed()) {
                if (distX >= field.getWidth() - distX)
                    distX = Math.abs(distX - field.getWidth());
            }
            int distY = Math.abs(y - a.y);
            if (distX + distY <= feelDistance)
                result.add(a);
        }
        return result;
    }

    /**
     * Method for generating four directions of movement in random order
     *
     * @return List of directions
     */
    protected List<Integer> generateDirections() {
        List<Integer> directions = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(directions);
        return directions;
    }

    /**
     * Move to one cell
     *
     * @param animals List of all animalы
     * @param field Game field
     * @param directions Available destinations
     */
    protected void simpleMove(List<Animal> animals, Field field, List<Integer> directions) {
        List<Integer> tmp;
        for (Integer i : directions) {
            switch (i) {
                case 0: //up
                default:
                    tmp = getCellCoordinates(x, y + 1, field);
                    break;
                case 1: //right
                    tmp = getCellCoordinates(x + 1, y, field);
                    break;
                case 2: //down
                    tmp = getCellCoordinates(x, y - 1, field);
                    break;
                case 3: //left
                    tmp = getCellCoordinates(x - 1, y, field);
                    break;
            }
            if (tmp != null) {
                int destinationX = tmp.get(0), destinationY = tmp.get(1);
                if (checkCellFree(animals, destinationX, destinationY)) {
                    x = destinationX;
                    y = destinationY;
                    break;
                }
            }
        }
    }

    /**
     * Method for determining the coordinates on the field, depending on the
     * properties of the field
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param field Game field
     * @return A list that consists of two numbers: X and Y coordinates
     */
    protected List<Integer> getCellCoordinates(int x, int y, Field field) {
        if (y < 0 || y >= field.getHeight())
            return null;
        if (x < 0 || x >= field.getWidth()) {
            if (field.getClosed())
                return null;
            else if (x < 0)
                x = field.getWidth() - 1;
            else
                x = 0;
        }
        return new ArrayList<>(Arrays.asList(x, y));
    }

    /**
     * Method that checks the field is free or busy
     *
     * @param animals List of all animalы
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if field is freee and false if field is busy
     */
    protected boolean checkCellFree(List<Animal> animals, Integer x, Integer y) {
        if (x == null || y == null) {
            return false;
        }
        int size;
        if (code == AnimalCode.FISH)
            size = animals.stream()
                    .filter(f -> (f.getX() == x && f.getY() == y) && f != this)
                    .collect(Collectors.toList()).size();
        else
            size = animals.stream()
                    .filter(s -> (s.getCode() == AnimalCode.SHARK
                            && x.equals(s.getX()) & y.equals(s.getY()) && s != this))
                    .collect(Collectors.toList()).size();
        return size == 0;
    }

    /**
     * Method for finding enemies nearby (for fishes-sharks, for sharks-fishes)
     *
     * @param animals List of all animals
     * @param opponentCode Opponent mark
     * @return List of nearby opponents
     */
    protected List<Animal> findOpponentsNear(List<Animal> animals, AnimalCode opponentCode) {
        return animals.stream()
                .filter(a -> a.code == opponentCode
                        && (Math.abs(x - a.x) <= feelDistance && Math.abs(y - a.y) <= feelDistance))
                .collect(Collectors.toList());
    }

    /**
     * Method to check whether an animal can be born
     *
     * @param animals List of all animals
     * @return True if amimal can be born and false differently
     */
    protected boolean checkCanBeBorn(List<Animal> animals) {
        return !animals.stream()
                .anyMatch(a -> a != this && a.getAlive()
                        && a.getX() == x && a.getY() == y);
    }

}
