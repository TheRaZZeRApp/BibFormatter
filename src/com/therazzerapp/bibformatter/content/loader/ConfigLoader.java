package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.Constants;
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
     * Loads the config json file and converts the data into a map.
     * Keys can be found in {@link com.therazzerapp.bibformatter.KeyType}
     * @return the config map.
     */
    public static Map<ConfigType, Object> load(){
        Map<ConfigType, Object> configMap = new HashMap<>();
        File file = new File(Constants.PATH_EXT_DATA+Constants.FILE_EXT_CONFIG+Constants.EXTENSION_JSON);
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
