/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.dataHandler;

/**
 * Class that receives notifications from animals and passes them on to the
 * listener
 *
 * @author Eugene
 */
public interface IAnimalListener {

    /**
     * Method for reporting death of animal
     *
     * @param animal Dead animal
     */
    void animalDie(Animal animal);

    /**
     * Method for reporting birth of animal
     *
     * @param animal Born animal
     */
    void animalReproduct(Animal animal);

}
