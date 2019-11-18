package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.ContentObserver;
import com.therazzerapp.bibformatter.content.loader.ConfigLoader;
import com.therazzerapp.bibformatter.content.saver.ConfigSaver;

import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class ConfigManager {
    private static Map<ConfigType, Object> configMap = new HashMap<>();

    public static void load(){
        configMap = ConfigLoader.load();
        ContentObserver.update(2);
    }

    public static void save(){
        ConfigSaver.save(configMap);
        ContentObserver.update(2);
    }

    public static Object getConfigProperty(ConfigType configProperty){
        return configMap.get(configProperty);
    }

    public static void setConfigProperty(ConfigType configProperty, Object value){
        configMap.put(configProperty,value);
        save();
    }
}
