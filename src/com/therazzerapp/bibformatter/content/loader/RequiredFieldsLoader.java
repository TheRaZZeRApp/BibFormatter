package com.therazzerapp.bibformatter.content.loader;

import com.google.gson.JsonElement;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;
import com.therazzerapp.bibformatter.content.saver.RequiredFieldsSaver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class RequiredFieldsLoader {
    public static Map<String, ArrayList<KeyType>> load(){
        Map<String, ArrayList<KeyType>> reqFieldsMap = new HashMap<>();

        File file = new File("./Data/valRequiredFields.json");
        if(!file.exists()){
            RequiredFieldsSaver.createDefaultRequiredFields();
        }
        JSONConfigSection root = new JSONConfig().load(file);
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : root.getObject().entrySet()) {
            ArrayList<KeyType> list = new ArrayList<>();
            if (stringJsonElementEntry.getValue() != null) {
                for (int i=0;i<stringJsonElementEntry.getValue().getAsJsonArray().size();i++){
                    list.add(KeyType.valueOf(stringJsonElementEntry.getValue().getAsJsonArray().get(i).toString().toUpperCase().replaceAll("\"","")));
                }
            }
            reqFieldsMap.put(stringJsonElementEntry.getKey(),list);
        }
        return reqFieldsMap;
    }
}
