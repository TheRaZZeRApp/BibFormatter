package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.content.CharacterMap;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.ContentObserver;
import com.therazzerapp.bibformatter.content.loader.SpecialCharacterLoader;
import com.therazzerapp.bibformatter.content.saver.SpecialCharacterSaver;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class SpecialCharacterManager {
    private static Set<CharacterMap> characterMaps = new HashSet<>();

    public static void load(File file){
        characterMaps.add(new CharacterMap(file));
        ContentObserver.update(2);
    }

    public static void save(String filename, String characterMapName){
        for (CharacterMap map : characterMaps) {
            if (map.getName().equalsIgnoreCase(characterMapName)){
                SpecialCharacterSaver.save(map,filename);
            }
        }
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

    public static CharacterMap getCharacterMap(String characterMapName) {
        for (CharacterMap map : characterMaps) {
            if (map.getName().equalsIgnoreCase(characterMapName)){
                return map;
            }
        }
        return null;
    }

    public static Set<CharacterMap> getCharacterMaps() {
        return characterMaps;
    }
}
