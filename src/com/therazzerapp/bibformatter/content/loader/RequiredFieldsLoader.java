package com.therazzerapp.bibformatter.content.loader;

import com.google.gson.JsonElement;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.4.3
 */
public class RequiredFieldsLoader {

    /**
     *
     * @param file
     * @return
     */
    public static Map<TypeType, ArrayList<KeyType>> load(File file){
        Map<TypeType, ArrayList<KeyType>> reqFieldsMap = new HashMap<>();
        JSONConfigSection root = new JSONConfig().load(file);
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : root.getObject().entrySet()) {
            ArrayList<KeyType> list = new ArrayList<>();
            if (stringJsonElementEntry.getValue() != null) {
                for (int i=0;i<stringJsonElementEntry.getValue().getAsJsonArray().size();i++){
                    list.add(KeyType.valueOf(stringJsonElementEntry.getValue().getAsJsonArray().get(i).toString().toUpperCase().replaceAll("\"","")));
                }
            }
            reqFieldsMap.put(TypeType.valueOf(stringJsonElementEntry.getKey().toUpperCase()),list);
        }
        return reqFieldsMap;
    }
}
