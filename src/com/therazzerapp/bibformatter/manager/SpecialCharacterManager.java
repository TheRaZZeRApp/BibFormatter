package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.ContentObserver;
import com.therazzerapp.bibformatter.content.loader.SpecialCharacterLoader;
import com.therazzerapp.bibformatter.content.saver.SpecialCharacterSaver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class SpecialCharacterManager {
    private static Map<String, String> characterMap = new HashMap<>();

    public static void load(File file){
        characterMap = SpecialCharacterLoader.load(file);
        ContentObserver.update(2);
    }

    public static void save(String filename){
        SpecialCharacterSaver.save(characterMap, filename);
        ContentObserver.update(2);
    }

    public static void initiate(){
        File file = new File("./Data/unicode2latex.txt");
        if(!file.exists()){
            SpecialCharacterSaver.createDefaultUnicode2Latex();
        }
        File defaultMap = new File("./Data/" + ConfigManager.getConfigProperty(ConfigType.DEFAULTCHARACTERMAP)+".txt");
        if (defaultMap.exists()){
            load(defaultMap);
        } else {
            LogManager.writeError("Error: Default character map not found!");
        }
    }

    public static Map<String, String> getCharacterMap() {
        return characterMap;
    }

    public static Object getConfigProperty(String specialCharacter){
        return characterMap.get(specialCharacter);
    }

    public static void setConfigProperty(String specialCharacter, String replacement, String filename){
        characterMap.put(specialCharacter,replacement);
        save(filename);
    }
}
