package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;


import javax.xml.bind.annotation.*;

/**
 * Handy class for JAXB work with appropriate tag
 * @author Vladimir
 */
@XmlRootElement(name = "entities")
@XmlAccessorType (XmlAccessType.FIELD)
public class Entities {
    /**
     * Streams get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    @XmlElement(name = "streams")
    private Streams listStreams;
    /**
     * Animal get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private Animal animals;

    public Streams getListStreams() {
        return listStreams;
    }

    public Animal getAnimals() {
        return animals;
    }

    public void setAnimals(Animal animals) {
        this.animals = animals;
    }

    public void setListStreams(Streams listStreams) {
        this.listStreams = listStreams;
    }
}