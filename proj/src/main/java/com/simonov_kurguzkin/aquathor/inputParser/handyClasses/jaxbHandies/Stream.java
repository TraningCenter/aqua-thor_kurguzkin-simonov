package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Handy class for JAXB work with appropriate tag
 * @author Vladimir
 */
@XmlRootElement(name = "stream")
@XmlAccessorType (XmlAccessType.FIELD)
public class Stream {
    /**
     * Stream speed get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int speed;
    /**
     * Stream starts at this horizontal line, it's get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int y_start;
    /**
     * Stream ends at this horizontal line, it's get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    private int y_end;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getY_end() {
        return y_end;
    }

    public void setY_end(int y_end) {
        this.y_end = y_end;
    }

    public int getY_start() {
        return y_start;
    }

    public void setY_start(int y_start) {
        this.y_start = y_start;
    }
}
