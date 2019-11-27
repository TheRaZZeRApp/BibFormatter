package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.manager.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <rezzer101@googlemail.com>
 * @since 0.16.12
 */
public class DoiPrefixLoader {

    /**
     * Returns a doi prefix {@link Map}.
     * <br>The first line contains the map value and the following line contains the map key.
     * <br>Key stands here for the doi prefix number (without 10.) and value for publisher name.
     * @param file
     * @return
     */
    public static Map<Integer,String> load(File file){
        Map<Integer,String> unicodeMap = new HashMap<>();
        ArrayList<String> content = FileManager.getFileContent(file);
        for (int i = 0; i < (content.size()-1); i+=2) {
            unicodeMap.put(Integer.parseInt(content.get(i+1)),content.get(i));
        }
        return unicodeMap;
    }
}
