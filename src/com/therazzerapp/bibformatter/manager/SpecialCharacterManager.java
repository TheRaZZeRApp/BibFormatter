package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.content.CharacterMap;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.saver.SpecialCharacterSaver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages the currently load special character maps.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
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
    }

    /**
     * Saves a specified character map into a new file by a specified path.
     * @param name the character map to save
     * @param path the path where the character map gets saved. Needs to contain an extension (def. .txt)
     */
    public static void save(String name, String path){
        for (CharacterMap m : characterMaps) {
            if (m.getName().equalsIgnoreCase(name)){
                SpecialCharacterSaver.save(m,path);
            }
        }
    }

    /**
     * Checks if the standard character maps exist and load them. If not create them and load.
     * Default maps: <br>Unicode: "./Data/unicode2latex.txt"
     */
    public static void initiate(){
        if(!new File(Constants.PATH_EXT_DATA +Constants.FILE_EXT_DEFAULT_CHARACTARMAP+Constants.EXTENSION_TXT).exists()){
            SpecialCharacterSaver.createDefaultUnicode2Latex();
        }
        File defaultMap = new File(Constants.PATH_EXT_DATA + ConfigManager.getConfigProperty(ConfigType.DEFAULTCHARACTERMAP)+Constants.EXTENSION_TXT);
        if (defaultMap.exists()){
            load(defaultMap);
        } else {
            LogManager.writeError(Constants.ERROR_DEFAULT_CHARACCTERMAP_NOTFOUND);
        }
    }

    /**
     * Returns the first character map from the set of currently loaded character maps with a matching name.
     * @param name the name of the character map (should be the character map file name without extension)
     * @return the first character map where map.getName matches the specified name,<br>null if no map with this name was found
     */
    public static CharacterMap getCharacterMap(String name) {
        for (CharacterMap m : characterMaps) {
            if (m.getName().equalsIgnoreCase(name)){
                return m;
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
