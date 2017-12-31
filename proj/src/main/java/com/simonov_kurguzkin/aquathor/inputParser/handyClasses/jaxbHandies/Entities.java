package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

/**
 *
 * @author Vladimir
 */
import javax.xml.bind.annotation.*;

//@XmlType(propOrder = { "streams", "animals" })
@XmlRootElement(name = "entities")
@XmlAccessorType (XmlAccessType.FIELD)
//@XmlRootElement
public class Entities {
    @XmlElement(name = "streams")
    private Streams listStreams;
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