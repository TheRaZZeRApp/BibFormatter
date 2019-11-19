package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.manager.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class SpecialCharacterLoader {
    public static Map<String,String> load(File file){
        Map<String,String> unicodeMap = new HashMap<>();
        ArrayList<String> content = FileManager.getFileContent(file);
        for (int i = 0; i < (content.size()-1); i+=2) {
             unicodeMap.put("([^\\\\]|^)(\\x{" + content.get(i) + "})",content.get(i+1));
        }
        return unicodeMap;
    }

}
