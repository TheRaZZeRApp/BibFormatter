package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.loader.ConfigLoader;
import com.therazzerapp.bibformatter.content.saver.ConfigSaver;

import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.3.0
 */
public class ConfigManager {

    /**
     *
     */
    private static Map<ConfigType, Object> configMap = new HashMap<>();

    /**
     *
     */
    public static void load(){
        configMap = ConfigLoader.load();
    }

    /**
     *
     */
    public static void save(){
        ConfigSaver.save(configMap);
    }

    /**
     *
     * @param configProperty
     * @return
     */
    public static Object getConfigProperty(ConfigType configProperty){
        return configMap.get(configProperty);
    }

    /**
     *
     * @param configProperty
     * @param value
     */
    public static void setConfigProperty(ConfigType configProperty, Object value){
        configMap.put(configProperty,value);
        save();
    }
}
