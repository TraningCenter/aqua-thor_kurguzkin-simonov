package com.simonov_kurguzkin.aquathor.outputWriter.jaxbWritingAdds;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Statistics;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB handy class for work with correspond tags
 * @author Vladimir
 */
@XmlRootElement(name = "step")
@XmlAccessorType(XmlAccessType.FIELD)
public class Step {

    /**
     * Iteration number get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int iteration;
    /**
     * Quantity of alive fish get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int fishes_alive;
    /**
     * Quantity of alive sharks get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int sharks_alive;

    /**
     * Basic default constructor
     */
    public Step() {
        iteration = 0;
        fishes_alive = 0;
        sharks_alive = 0;
    }
    /**
     * Constructor which sets all properties with the correspond statistics values
     * @param stats 
     */
    public Step(Statistics stats) {
        this.iteration = stats.getIterationStep();
        this.fishes_alive = stats.getFishAmount();
        this.sharks_alive = stats.getSharksAmount();
    }

    public int getFishes_alive() {
        return fishes_alive;
    }

    public void setFishes_alive(int fishes_alive) {
        this.fishes_alive = fishes_alive;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public int getSharks_alive() {
        return sharks_alive;
    }

    public void setSharks_alive(int sharks_alive) {
        this.sharks_alive = sharks_alive;
    }
}
