package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.saver.ConfigSaver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.3.0
 */
public class ConfigLoader {

    /**
     *
     * @return
     */
    public static Map<ConfigType, Object> load(){
        Map<ConfigType, Object> configMap = new HashMap<>();

        File file = new File("./Data/config.json");
        if(!file.exists()){
            ConfigSaver.createDefaultConfig();
        }

        JSONConfigSection root = new JSONConfig().load(file);
        for (ConfigType value : ConfigType.values()) {
            switch (value.getTyp()){
                case "string":
                    configMap.put(value, root.getString(value.getName(), ""));
                    break;
                case "int":
                    configMap.put(value, root.getInt(value.getName(), -1));
                    break;
                case "boolean":
                    configMap.put(value, root.getBoolean(value.getName(), false));
                    break;
            }
        }
        return configMap;
    }
}
