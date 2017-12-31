/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vladimir
 */
@XmlRootElement(name = "streams")
public class Streams {
    @XmlElement(name = "stream")
    private List<Stream> listStreams = null;

    public List<Stream> getStreams() {
        return listStreams;
    }

    public void setStreams(List<Stream> streams) {
        this.listStreams = streams;
    }
}