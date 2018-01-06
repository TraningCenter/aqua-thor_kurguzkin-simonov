package com.simonov_kurguzkin.aquathor.outputWriter.jaxbWritingAdds;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB handy class for work with correspond tags
 * @author Vladimir
 */
@XmlRootElement(name = "statistics")
public class AStatistics {
    /**
     * List of statistics get/set-property which corresponds to all XML-tags with the same title
     * or with the name which is set in name property of annotation
     */
    @XmlElement(name = "step")
    private List<Step> listSteps;

    /**
     * Constructor sets up list of steps 
     */
    public AStatistics() {
        listSteps = new LinkedList<>();
    }

    public List<Step> getSteps() {
        return listSteps;
    }

    public void setListSteps(List<Step> listSteps) {
        this.listSteps = listSteps;
    }
}
