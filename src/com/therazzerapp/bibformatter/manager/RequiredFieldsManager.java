package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.content.ContentObserver;
import com.therazzerapp.bibformatter.content.RequiredFields;
import com.therazzerapp.bibformatter.content.saver.RequiredFieldsSaver;

import java.io.File;
import java.util.*;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.4.3
 */
public class RequiredFieldsManager {
    private static Set<RequiredFields> requiredFields = new HashSet<>();

    /**
     *
     * @param file
     * @return
     */
    public static RequiredFields load(File file){
        RequiredFields rF = new RequiredFields(file);
        requiredFields.add(rF);
        ContentObserver.update(2);
        return rF;
    }

    /**
     *
     * @param mapName
     * @return
     */
    public static RequiredFields getRequiredFieldsMap(String mapName){
        for (RequiredFields requiredField : requiredFields) {
            if (requiredField.getName().equals(mapName)){
                return requiredField;
            }
        }
        return null;
    }

    /**
     *
     */
    public static void init(){
        File file = new File("./Data/CheckFiles/valRequiredFields.json");
        if(!file.exists()){
            RequiredFieldsSaver.createDefaultRequiredFields();
        }
        File dir = new File("./Data/CheckFiles/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().endsWith(".json")){
                    load(child);
                }
            }
        }
    }

    /**
     *
     */
    public static void save(){
        for (RequiredFields requiredField : requiredFields) {
            RequiredFieldsSaver.save(requiredField);
        }
        ContentObserver.update(2);
    }

    /**
     *
     * @param type
     * @param mapName
     * @return
     */
    public static ArrayList<KeyType> getRequiredFields(TypeType type, String mapName){
        for (RequiredFields requiredField : requiredFields) {
            if (requiredField.getName().equals(mapName)){
                return requiredField.getRequiredFieldsMap().get(type);
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static RequiredFields getDefaultMap(){
        for (RequiredFields requiredField : requiredFields) {
            if (requiredField.getName().equals("valRequiredFields"))
                return requiredField;
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
