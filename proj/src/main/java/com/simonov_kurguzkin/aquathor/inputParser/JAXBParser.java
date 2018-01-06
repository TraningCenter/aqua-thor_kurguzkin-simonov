package com.simonov_kurguzkin.aquathor.inputParser;

import com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies.Entities;
import com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies.Fish;
import com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies.Stream;
import com.simonov_kurguzkin.aquathor.inputParser.handyClasses.jaxbHandies.Shark;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * XML JAXB parsing functionality
 * @author Vladimir
 */
public class JAXBParser extends Parser {

    @Override
    public Map<String, Object> parseInput(String fileLink, String xsdFileLink) throws IOException {
        if (!validateXML(fileLink, xsdFileLink))
            throw new IOException("input file does not meet the requirements");
        try {
            File file = new File(fileLink);
            JAXBContext jaxbContext = JAXBContext.newInstance(Entities.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Entities entities = (Entities) jaxbUnmarshaller.unmarshal(file);
            List<Object> listToReturn = new ArrayList<Object>();
            if (entities.getListStreams() == null) {
                listToReturn.add(0);
            } else {
                listToReturn.add(entities.getListStreams().getStreams().size());
                for (Stream s : entities.getListStreams().getStreams()) {
                    listToReturn.addAll(Arrays.asList(s.getSpeed(), s.getY_start(), s.getY_end()));
                }
            }
            Fish fish = entities.getAnimals().getFish();
            Shark shark = entities.getAnimals().getShark();
            listToReturn.addAll(Arrays.asList(fish.getQuantity(), fish.getReproduction_frequency(), fish.getTime_to_live(),
                    fish.getSpeed(), fish.getFeel_radius()));
            listToReturn.addAll(Arrays.asList(shark.getQuantity(), shark.getTime_to_live(), shark.getTime_to_hungry_live(),
                    shark.getSpeed(), shark.getFeel_radius()));
            return listTomap(listToReturn);
        } catch (NullPointerException | JAXBException ex) {
            System.out.println("Exception ex while JAXB parsing");
            Logger.getLogger(STAXParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex.getCause());
        }
    }
}
