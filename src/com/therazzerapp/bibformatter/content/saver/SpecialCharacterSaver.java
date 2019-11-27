package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.content.CharacterMap;
import com.therazzerapp.bibformatter.manager.FileManager;


/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.4.2
 */
public class SpecialCharacterSaver {

    /**
     *
     * @param characterMap
     * @param filename
     */
    public static void save(CharacterMap characterMap, String filename){
        StringBuilder sb = new StringBuilder();
        for (String s : characterMap.getCharacterMap().keySet()) {
            sb.append(s).append("\n").append(characterMap.getCharacterMap().get(s)).append("\n");
        }
        FileManager.exportFile(sb.toString(),"./Data/"+filename);
    }

    /**
     *
     */
    public static void createDefaultUnicode2Latex(){
        createDefaultSpecialCharacterMap("data/unicode2latex.txt", "./Data/unicode2latex.txt");
    }

    /**
     *
     * @param path
     * @param dest
     */
    private static void createDefaultSpecialCharacterMap(String path, String dest){
        StringBuilder sb = new StringBuilder();
        for (String s : FileManager.getFileContentJar(path)) {
            sb.append(s).append("\n");
        }
        FileManager.exportFile(sb.toString(),dest);
    }
}
