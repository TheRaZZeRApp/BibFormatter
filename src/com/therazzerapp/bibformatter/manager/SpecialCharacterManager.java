package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.content.CharacterMap;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.ContentObserver;
import com.therazzerapp.bibformatter.content.saver.SpecialCharacterSaver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages the currently load special character maps.
 *
 * @author Paul Eduard Koenig <rezzer101@googlemail.com>
 * @since 0.4.2
 */
public class SpecialCharacterManager {

    /**
     * Set of all currently loaded character maps.
     */
    private static Set<CharacterMap> characterMaps = new HashSet<>();

    /**
     * Loads a {@link File}, converts the content into a map and adds this map to
     * the {@link Set} of currently loaded character maps.
     * @param file the character map file to load
     */
    public static void load(File file){
        characterMaps.add(new CharacterMap(file));
        ContentObserver.update(2);
    }

    /**
     * Saves a specified character map into a new file by a specified path.
     * @param characterMapName the character map to save
     * @param path the path where the character map gets saved. Needs to contain an extension (def. .txt)
     */
    public static void save(String characterMapName, String path){
        for (CharacterMap map : characterMaps) {
            if (map.getName().equalsIgnoreCase(characterMapName)){
                SpecialCharacterSaver.save(map,path);
            }
        }
        ContentObserver.update(2);
    }

    /**
     * Checks if the standard character maps exist and load them. If not create them and load.
     * Default maps: <br>Unicode: "./Data/unicode2latex.txt"
     */
    public static void initiate(){
        if(!new File("./Data/unicode2latex.txt").exists()){
            SpecialCharacterSaver.createDefaultUnicode2Latex();
        }
        File defaultMap = new File("./Data/" + ConfigManager.getConfigProperty(ConfigType.DEFAULTCHARACTERMAP)+".txt");
        if (defaultMap.exists()){
            load(defaultMap);
        } else {
            LogManager.writeError(Constants.ERROR_DEFAULT_CHARACCTERMAP_NOTFOUND);
        }
    }

    /**
     * Returns the first character map from the set of currently loaded character maps with a matching name.
     * @param characterMapName the name of the character map (should be the character map file name without extension)
     * @return the first character map where map.getName matches the specified name,<br>null if no map with this name was found
     */
    public static CharacterMap getCharacterMap(String characterMapName) {
        for (CharacterMap map : characterMaps) {
            if (map.getName().equalsIgnoreCase(characterMapName)){
                return map;
            }
        }
        return null;
    }

    /**
     * Returns the set of all currently loaded character maps.
     * @return all currently loaded character maps
     */
    public static Set<CharacterMap> getCharacterMaps() {
        return characterMaps;
    }
}
