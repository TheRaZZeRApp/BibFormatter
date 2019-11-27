package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.content.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.4.2
 */
public class SpecialCharacterLoader {

    /**
     * Returns a special character {@link Map}.
     * <br>First line in the file gets skipped since it contains the regExPattern.
     * <br>FAfter the first line in the file the first line contains the map key and the following line contains the map value.
     * <br>FKey stands here for the special character and value for the replacement.
     * @param file
     * @return
     */
    public static Map<String,String> load(File file){
        Map<String,String> unicodeMap = new HashMap<>();
        ArrayList<String> content = FileUtils.getFileContent(file);
        for (int i = 1; i < (content.size()-1); i+=2) {
             unicodeMap.put(content.get(i),content.get(i+1));
             //"([^\\\\]|^)(\\x{" + content.get(i) + "})"
        }
        return unicodeMap;
    }

    /**
     * Only returns the first line of a character map.
     * @param file
     * @return
     */
    public static String loadRegExPatten(File file){
        return FileUtils.getFileContent(file).get(0);
    }

}
