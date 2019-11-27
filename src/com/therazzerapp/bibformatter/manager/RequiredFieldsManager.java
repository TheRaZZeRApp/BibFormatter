package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.content.RequiredFields;
import com.therazzerapp.bibformatter.content.saver.RequiredFieldsSaver;

import java.io.File;
import java.util.*;

/**
 * Manages the currently loaded required fields maps.
 * {@link RequiredFields} contains a list of every key a type needs to count a s complete.
 * (i.e. type article always needs a title and an author)
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.4.3
 */
public class RequiredFieldsManager {

    /**
     * Set of all currently loaded required fields.
     */
    private static Set<RequiredFields> requiredFields = new HashSet<>();

    /**
     *  Loads a new required fields map file. File needs extension .json
     * @param file the required field file
     * @return the new required fields map
     */
    public static RequiredFields load(File file){
        RequiredFields r = new RequiredFields(file);
        requiredFields.add(r);
        return r;
    }

    /**
     * Gets the required fields map by a specified name.<br>The name should be the file name of the required fields without extension.
     * @param name the name of the map
     * @return the required fields map with the given name<br>null if no map with this name was found
     */
    public static RequiredFields getRequiredFieldsMap(String name){
        for (RequiredFields r : requiredFields) {
            if (r.getName().equals(name)){
                return r;
            }
        }
        return null;
    }

    /**
     * Check if the default required fields map exists, if not generate it. Then load every json file inside the checkfiles path described in {@link Constants}
     */
    public static void init(){
        if(!new File(Constants.PATH_EXT_CHECKFILES + Constants.FILE_EXT_DEFAULT_CHECKFILE + Constants.EXTENSION_JSON).exists()){
            RequiredFieldsSaver.createDefaultRequiredFields();
        }
        File[] d = new File(Constants.PATH_EXT_CHECKFILES).listFiles();
        if (d != null) {
            for (File f : d) {
                if (f.getName().endsWith(Constants.EXTENSION_JSON)){
                    load(f);
                }
            }
        }
    }

    /**
     * Saves every currently loaded required fields map as a file in ./Data/CheckFiles/filename
     * <br> the file name will be requiredfields.getName
     */
    public static void save(){
        for (RequiredFields r : requiredFields) {
            RequiredFieldsSaver.save(r);
        }
    }

    /**
     * Returns an {@link ArrayList<KeyType>} of every key needed by the specified type.
     * @param type the type to get the required fields for (i.e. {@link TypeType}.ARTICLE for type article)
     * @param mapName the required fields map name (should be the file name without extension)
     * @return a list of every required field needed
     */
    public static ArrayList<KeyType> getRequiredFields(TypeType type, String mapName){
        for (RequiredFields r : requiredFields) {
            if (r.getName().equals(mapName)){
                return r.getRequiredFieldsMap().get(type);
            }
        }
        return null;
    }

    /**
     * Returns the default required fields map<br>
     * which is generated in {@link RequiredFieldsSaver}
     * @return the default required fields map
     */
    public static RequiredFields getDefaultMap(){
        for (RequiredFields r : requiredFields) {
            if (r.getName().equals(Constants.FILE_EXT_DEFAULT_CHECKFILE)){
                return r;
            }
        }
        init();
        return getDefaultMap();
    }

    /**
     * Returns true if the manager contains a map with the given name.
     * @param mapName
     * @return
     */
    public static boolean hasMap(String mapName){
        for (RequiredFields requiredField : requiredFields) {
            if (requiredField.getName().equals(mapName)){
                return true;
            }
        }
        return false;
    }
}
