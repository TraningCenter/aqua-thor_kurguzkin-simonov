package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Handy class for JAXB work with appropriate tag
 * @author Vladimir
 */
@XmlRootElement(name = "streams")
public class Streams {
    /**
     * List of all streams get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    @XmlElement(name = "stream")
    private List<Stream> listStreams = null;

    public List<Stream> getStreams() {
        return listStreams;
    }

    public void setStreams(List<Stream> streams) {
        this.listStreams = streams;
    }
}