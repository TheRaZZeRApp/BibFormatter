package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.manager.FileManager;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.12.9
 */
public class AuxLoader {
    public static Set<String> getCitations(File file){
        Set<String> citations = new HashSet<>();
        Matcher matcher;
        for (String s : FileManager.getFileContent(file)) {
            matcher = Pattern.compile("\\\\abx@aux@defaultrefcontext\\{0\\}\\{([^\\}]*)").matcher(s);
            if (matcher.find()){
                citations.add(matcher.group(1));
            }
        }
        return citations;
    }
}
