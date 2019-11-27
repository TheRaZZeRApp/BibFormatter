package com.therazzerapp.bibformatter.content;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.content.loader.RequiredFieldsLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.9.8
 */
public class RequiredFields {

    private String name;
    private Map<TypeType, ArrayList<KeyType>> requiredFieldsMap;

    /**
     *
     * @param file
     */
    public RequiredFields(File file) {
        this.requiredFieldsMap = RequiredFieldsLoader.load(file);
        this.name = Utils.replaceLast(file.getName(),".json","");
    }

    /**
     *
     * @param map
     * @param name
     */
    public RequiredFields(Map<TypeType, ArrayList<KeyType>> map, String name) {
        this.requiredFieldsMap = map;
        this.name = name;
    }

    /**
     *
     * @param type
     * @return
     */
    public ArrayList<KeyType> getRequiredFields (TypeType type){
        return requiredFieldsMap.get(type) != null ? requiredFieldsMap.get(type) : requiredFieldsMap.get(TypeType.DEFAULT);
    }

    /**
     *
     * @return
     */
    public Map<TypeType, ArrayList<KeyType>> getRequiredFieldsMap() {
        return requiredFieldsMap;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }
}
