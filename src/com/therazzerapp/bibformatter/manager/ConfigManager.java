package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.KeyType;
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
     * Returns the config value for the specified {@link ConfigType}
     * @param configProperty the key to check.
     * @return the config value as String, empty String if key with the give {@link ConfigType} was found.
     * @since 0.19.12
     */
    public static String getAsString(ConfigType configProperty){
        try {
            return (String) configMap.get(configProperty);
        } catch (ClassCastException | NullPointerException ignored){
            return "";
        }
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
