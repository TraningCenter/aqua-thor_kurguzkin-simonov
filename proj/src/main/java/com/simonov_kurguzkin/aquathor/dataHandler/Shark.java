package com.simonov_kurguzkin.aquathor.dataHandler;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for describing sharks
 *
 * @author Eugene
 */
public class Shark extends Animal {

    /**
     * The maximum time that a shark can not eat
     */
    private final int timeHungryDeath;
    /**
     * Current time that the shark did not eat
     */
    private int timeHungry;
    /**
     * The age at which a shark leaves child
     */
    private final int reproductAge;

    /**
     * Shark constructor
     *
     * @param lifetime Maximum shark life time
     * @param timeHungryDeath Maximum time without food
     * @param speed Shark speed
     * @param feelDistance Maximum distance at which a shark feels fish
     * @param x X coordinate
     * @param y Y coordinate
     * @param iAnimalListener Object of the class that we will notify you of a
     * change in state
     */
    public Shark(int lifetime, int timeHungryDeath, int speed,
            int feelDistance, int x, int y, IAnimalListener iAnimalListener) {
        super(AnimalCode.SHARK, lifetime, speed, feelDistance, x, y, iAnimalListener);
        this.timeHungryDeath = timeHungryDeath;
        this.timeHungry = 0;
        this.reproductAge = Math.abs(rnd.nextInt() % (lifetime - 2)) + 1;
    }

    @Override
    public void nextAction(List<Animal> animals, List<Stream> streams, Field field) {
        if (age >= lifetime || timeHungry >= timeHungryDeath) {
            die();
            return;
        }
        if (age == reproductAge)
            if (alive)
                reproduct();
        move(animals, field);
        age++;
    }

    @Override
    protected void reproduct() {
        listener.animalReproduct(new Shark(lifetime, timeHungryDeath,
                speed, feelDistance, x, y, listener));
    }

    /**
     * Shark movement method
     *
     * @param animals List of all animals
     * @param field Game field
     */
    private void move(List<Animal> animals, Field field) {
        for (int i = 0; i < speed; i++) {
            List<Animal> nears = findAnimalsNear(animals, field);
            List<Animal> fishes = findOpponentsNear(animals, AnimalCode.FISH);
            //если рядом нет рыб, просто плывем
            if (fishes.isEmpty())
                simpleMove(animals, field, generateDirections());
            else {
                //плывем к рыбе
                simpleMove(animals, field, generateHuntDirection(fishes, field));
                // если можем, съедаем рыбу
                eat(animals);
            }
        }
    }

    /**
     * Method for determining the directions of hunting for fish
     *
     * @param fishes List of nearby fishes
     * @param field Game field
     * @return List of directions
     */
    private List<Integer> generateHuntDirection(List<Animal> fishes, Field field) {
        List<Integer> directions = new ArrayList<>();
        //находим ближайшую рыбу
        Animal fish = fishes.get(0);
        int minDist = Math.abs(x - fish.getX()) + Math.abs(y - fish.getY());
        int num = 0;
        for (int i = 0; i < fishes.size(); i++) {
            int tmpDist = Math.abs(x - fish.x) + Math.abs(y - fish.y);
            if (tmpDist < minDist) {
                minDist = tmpDist;
                num = i;
            }
        }
        fish = fishes.get(num);
        return getDirections(fish.getX(), fish.getY());
    }

    /**
     * Method for generating directions of motion for the nearest fish
     *
     * @param fishX Fish X coordinate
     * @param fishY Fish Y coordinate
     * @return List of directions
     */
    private List<Integer> getDirections(int fishX, int fishY) {
        List<Integer> directions = new ArrayList<>();
        int distX = x - fishX;
        int distY = y - fishY;
        if (distX != 0) {
            if (distX < 0)
                directions.add(1);
            else
                directions.add(3);

        }
        if (distY != 0) {
            if (distY < 0)
                directions.add(0);
            else
                directions.add(2);
        }
        return directions;
    }

    /**
     * Method for eating fish
     *
     * @param animals List of fish from which the shark chooses the one that can
     * eat
     */
    private void eat(List<Animal> animals) {
        List<Animal> tmp = animals.stream()
                .filter(f -> f != this && f.getX() == x && f.getY() == y)
                .collect(Collectors.toList());
        if (tmp.isEmpty())
            timeHungry++;
        else {
            timeHungry = 0;
            listener.animalDie(tmp.get(0));
        }
    }

}
