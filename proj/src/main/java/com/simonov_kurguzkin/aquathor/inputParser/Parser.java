/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simonov_kurguzkin.aquathor.inputParser;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Евгений
 */
public abstract class Parser {

    public abstract Map<String, Object> parseInput(String fileLink) throws IOException;

    protected Map<String, Object> listTomap(List<Object> list) {
        Map<String, Object> result = new LinkedHashMap<>();
        int streamNum = (Integer) list.get(0);
        int position = 1;
        int currentStreamNum = 0;
        for (int i = 0; i < streamNum; i++) {
            result.put("stream_speed" + currentStreamNum, list.get(position++));
            result.put("stream_start" + currentStreamNum, list.get(position++));
            result.put("stream_end" + currentStreamNum, list.get(position++));
            currentStreamNum++;
        }

        result.put("fish_quantity", list.get(position++));
        result.put("fish_reproduction", list.get(position++));
        result.put("fish_live", list.get(position++));
        result.put("fish_speed", list.get(position++));
        result.put("fish_radius", list.get(position++));

        result.put("shark_quantity", list.get(position++));
        result.put("shark_live", list.get(position++));
        result.put("shark_hungry", list.get(position++));
        result.put("shark_speed", list.get(position++));
        result.put("shark_radius", list.get(position++));

        return result;
    }

}
