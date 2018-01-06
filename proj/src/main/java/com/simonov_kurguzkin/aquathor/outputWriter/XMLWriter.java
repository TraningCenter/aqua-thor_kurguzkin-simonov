package com.simonov_kurguzkin.aquathor.outputWriter;

import com.simonov_kurguzkin.aquathor.auxiliaryUnits.Statistics;

/**
 * Class for writing XML file and adding content to it
 *
 * @author Eugene
 */
public abstract class XMLWriter extends Writer {

    /**
     * XMLWriter constructor
     *
     * @param fileNameSting XML file name
     * @param overwritePermission Permission to overwrite file
     */
    public XMLWriter(String fileNameSting, boolean overwritePermission) {
        super(fileNameSting, overwritePermission);
    }

    /**
     * Method for adding a content based on statistics
     *
     * @param statistics Statistics about the specific stage of the simulation
     */
    public abstract void addRecord(Statistics statistics);

}
