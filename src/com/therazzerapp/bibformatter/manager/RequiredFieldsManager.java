package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.content.ContentObserver;
import com.therazzerapp.bibformatter.content.loader.RequiredFieldsLoader;
import com.therazzerapp.bibformatter.content.saver.RequiredFieldsSaver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class RequiredFieldsManager {
    private static Map<String, ArrayList<KeyType>> requiredFieldsMap = new HashMap<>();

    public static void load(){
        requiredFieldsMap = RequiredFieldsLoader.load();
        ContentObserver.update(2);
    }

    public static void save(){
        RequiredFieldsSaver.save(requiredFieldsMap);
        ContentObserver.update(2);
    }

    public static Object getRequiredFields(String entryTyp){
        return requiredFieldsMap.get(entryTyp);
    }

    public static void setConfigProperty(String entryTyp, ArrayList<KeyType> keys){
        requiredFieldsMap.put(entryTyp,keys);
        save();
    }
}
