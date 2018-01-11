package com.simonov_kurguzkin.aquathor.dataHandler;

import com.simonov_kurguzkin.aquathor.Controller;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.AnimalView;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.EntityView;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Field;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Snapshot;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Statistics;
import com.simonov_kurguzkin.aquathor.auxiliaryUnits.StreamView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that is responsible for the logic of the application
 *
 * @author Eugene
 */
public class DataHandler {

    /**
     * Current state of system
     */
    private final SystemState state;
    /**
     * Game field
     */
    private final Field field;
    /**
     * Number of iteration
     */
    private int iterationNum;
    /**
     * Object that will notify this class about a change in the state of the
     * animals
     */
    private final AnimalListener observer;

    /**
     * DataHandler constructor
     *
     * @param entities Map, which contains game field and their values
     * @param field Game field
     * @throws IllegalArgumentException Throws on incorrect input data
     * @throws NumberFormatException Throws on incorrect input data
     */
    public DataHandler(Map<String, Object> entities, Field field)
            throws IllegalArgumentException, NumberFormatException {
        this.observer = new AnimalListener(this);
        List<Stream> streams = new ArrayList<>();
        List<Animal> animals = new LinkedList<>();
        streams.addAll(initializeStreams(entities, field));
        animals.addAll(initializeAnimals(entities, field));
        this.state = new SystemState(streams, animals, field);
        this.field = field;
        this.iterationNum = 0;
    }

    /**
     * Method for initialize streams fields
     *
     * @param entities Map with input data
     * @param field Game field
     * @return List of streams
     */
    private List<Stream> initializeStreams(Map<String, Object> entities, Field field) {
        List<Stream> result = new ArrayList<>();
        List<Integer> speeds = new ArrayList<>();
        List<Integer> starts = new ArrayList<>();
        List<Integer> ends = new ArrayList<>();
        for (String key : entities.keySet()) {
            if (key.contains("stream_speed"))
                speeds.add((Integer) entities.get(key));
            else if (key.contains("stream_start"))
                starts.add((Integer) entities.get(key));
            else if (key.contains("stream_end"))
                ends.add((Integer) entities.get(key));
        }
        for (int i = 0; i < speeds.size(); i++) {
            int speed = speeds.get(i);
            int start = starts.get(i);
            int end = ends.get(i);
            if (start >= end || start < 0 || end <= 0)
                throw new IllegalArgumentException("data on streams are incorrect");
            if (start >= field.getHeight() || end >= field.getHeight()) {
                Logger logger = LoggerFactory.getLogger(Controller.class);
                if (start >= field.getHeight() && end >= field.getHeight()) {
                    logger.info("Both coordinates of one of the streams are too large."
                            + " Stream was not added");
                    continue;
                } else if (start < field.getHeight()) {
                    end = field.getHeight() - 1;
                    logger.info("The coordinate of the end of the stream was too important. "
                            + "Therefore, it was replaced with the maximum possible: " + end);
                }
            }
            result.add(new Stream(speed, start, end));
        }
        return result;
    }

    /**
     * Method for initialize animals fields
     *
     * @param entities Map with input data
     * @param field Game field
     * @return
     */
    private List<Animal> initializeAnimals(Map<String, Object> entities, Field field) {
        Map<String, Object> fishesMap = findNeededEntities(entities, "fish");
        int fishReproduction = (Integer) fishesMap.get("fish_reproduction");
        int fishLive = (Integer) fishesMap.get("fish_live");
        int fishSpeed = (Integer) fishesMap.get("fish_speed");
        int fishRadius = (Integer) fishesMap.get("fish_radius");
        int fishQuantity = (Integer) fishesMap.get("fish_quantity");
        if (fishReproduction < 1 || fishLive < 1 || fishLive < fishReproduction
                || fishSpeed < 0 || fishRadius < 0 || fishQuantity < 1) {
            throw new IllegalArgumentException("data on fishes are incorrect");
        }
        Map<String, Object> sharksMap = findNeededEntities(entities, "shark");
        int sharkLive = (Integer) sharksMap.get("shark_live");
        int sharkHungry = (Integer) sharksMap.get("shark_hungry");
        int sharkSpeed = (Integer) sharksMap.get("shark_speed");
        int sharkRadius = (Integer) sharksMap.get("shark_radius");
        int sharkQuantity = (Integer) sharksMap.get("shark_quantity");
        if (sharkLive < 1 || sharkHungry < 1 || sharkLive < sharkHungry
                || sharkSpeed < 0 || sharkRadius < 0 || sharkQuantity < 1)
            throw new IllegalArgumentException("data on sharks are incorrect");

        int animalQuantity = fishQuantity + sharkQuantity;
        if (field.getHeight() * field.getWidth() / field.MAX_FREE_CELLS < animalQuantity) {
            throw new IllegalArgumentException(
                    "animal data is incorrect: too many animals for this field");
        }

        List<Integer> coordinates = new LinkedList<>();
        for (int i = 0; i < field.getWidth(); i++) {
            for (int k = 0; k < field.getHeight(); k++) {
                coordinates.add(i);
                coordinates.add(k);
            }
        }
        Random rnd = new Random();
        List<Animal> fishes = new ArrayList<>();
        List<Animal> sharks = new ArrayList<>();
        for (int i = 0; i < animalQuantity; i++) {
            int index = Math.abs(rnd.nextInt() % coordinates.size() / 2) * 2;
            int x = coordinates.get(index);
            int y = coordinates.get(index + 1);
            coordinates.remove(index + 1);
            coordinates.remove(index);
            if (i < fishQuantity)
                fishes.add(new Fish(fishReproduction, fishLive,
                        fishSpeed, fishRadius, x, y, observer));
            else
                sharks.add(new Shark(sharkLive, sharkHungry,
                        sharkSpeed, sharkRadius, x, y, observer));
        }

        List<Animal> result = new ArrayList<>(fishes);
        result.addAll(sharks);
        return result;
    }

    /**
     * Method for calculating the next step
     */
    public void processNextStep() {
        iterationNum++;
        for (Animal a : state.getAnimals())
            a.nextAction(state.getAnimals(), state.getStreams(), field);
        state.updateSystemState();
    }

    /**
     * Method for generating the current snapshot of the system
     *
     * @return Snapshot of current system state
     */
    public Snapshot generateSnapshot() {
        List<Animal> fishes = getAliveAnimals(AnimalCode.FISH);
        List<Animal> sharks = getAliveAnimals(AnimalCode.SHARK);
        List<EntityView> views = new ArrayList<>();
        fishes.stream().forEach((a) -> {
            views.add(new AnimalView(a.code, a.x, a.y));
        });
        sharks.stream().forEach((a) -> {
            views.add(new AnimalView(a.code, a.x, a.y));
        });
        state.getStreams().forEach((a) -> {
            views.add(new StreamView(a.getStartCoordinate(), a.getEndCoordinate(), a.getSpeed()));
        });
        return new Snapshot(views, iterationNum);
    }

    /**
     * Method for generating the current statistics of the system
     *
     * @return Statistics of current system state
     */
    public Statistics generateStatistics() {
        List<Animal> fishes = getAliveAnimals(AnimalCode.FISH);
        List<Animal> sharks = getAliveAnimals(AnimalCode.SHARK);
        return new Statistics(iterationNum, fishes.size(), sharks.size());
    }

    private List<Animal> getAliveAnimals(AnimalCode code) {
        return state.getAnimals().stream()
                .filter(a -> a.code == code && a.alive)
                .collect(Collectors.toList());
    }

    /**
     * Method for selecting fish of specific breed from all fish
     *
     * @param entities Input data
     * @param key Mark of fish
     * @return Map of selected fishes
     */
    private Map<String, Object> findNeededEntities(Map<String, Object> entities, String key) {
        return entities.entrySet().stream()
                .filter(map -> ((String) map.getKey()).contains(key))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    /**
     * Method for processing the event of fish death
     *
     * @param animal Dead fish
     */
    protected void animalDie(Animal animal) {
        state.addDeadedAnimal(animal);
    }

    /**
     * Method for processing the event of the birth of fish
     *
     * @param animal Born fish
     */
    protected void animalReproduct(Animal animal) {
        state.addBornAnimal(animal);
    }

}
