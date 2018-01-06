package com.simonov_kurguzkin.aquathor.outputWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for writing output files
 *
 * @author Eugene
 */
public abstract class Writer {

    /**
     * Output file name
     */
    private final String fileNameSting;
    /**
     * Permission to overwrite file
     */
    private final boolean overwritePermission;
    /**
     * The content that will be written to the file
     */
    protected StringBuilder content;

    /**
     * Writer constructor
     *
     * @param fileNameSting Output file name
     * @param overwritePermission Permission to overwrite file
     */
    public Writer(String fileNameSting, boolean overwritePermission) {
        this.fileNameSting = fileNameSting;
        this.overwritePermission = overwritePermission;
        content = new StringBuilder();
    }

    /**
     * Method for creating a file
     *
     * @throws IOException Throws when a file creation error occurs
     */
    public void createFile() throws IOException {
        File file = new File(fileNameSting);
        if (file.exists()) {
            if (overwritePermission)
                file.delete();
            else
                throw new IOException("attempt to overwrite a file without permission");
        }
        file.createNewFile();
    }

    /**
     * Method for writing content to the file
     */
    public void writeFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileNameSting);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Auxiliary method for delete existing file
     *
     * @param fileName File name
     */
    public void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
    }

}
