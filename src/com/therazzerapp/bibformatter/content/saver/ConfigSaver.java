package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;
import com.therazzerapp.bibformatter.content.ConfigType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <VERSION>
 */
public class ConfigSaver {
    public static void save(Map<ConfigType, Object> configMap){
        File file = new File("./Data/config.json");
        JSONConfig config = new JSONConfig();
        JSONConfigSection root = config.newRootSection();
        for (ConfigType value : ConfigType.values()) {
            switch (value.getTyp()){
                case "string":
                    root.setString(value.getName(),(String) configMap.get(value));
                    break;
                case "int":
                    root.setInt(value.getName(),(int) configMap.get(value));
                    break;
                case "boolean":
                    root.setBoolean(value.getName(),(boolean) configMap.get(value));
                    break;
            }
        }
        config.save(root,file);
    }

    public static void createDefaultConfig(){
        Map<ConfigType, Object> configMap = new HashMap<>();
        configMap.put(ConfigType.ENTRYORDER,"title shorttitle author year month day journal booktitle location on publisher address series volume number pages doi isbn issn url urldate copyright category note metadata");
        configMap.put(ConfigType.DEFAULTMONTHFORMAT,"name");
        configMap.put(ConfigType.DEFAULTPAGESFORMAT,"double");
        configMap.put(ConfigType.ENCLOSENUMERICS,false);
        configMap.put(ConfigType.INDENTSTYLE,"tabs");
        configMap.put(ConfigType.INDENTSPACESAMOUNT,3);
        configMap.put(ConfigType.INDENTVALUECOLUMM,12);
        configMap.put(ConfigType.WRITEEMPTYENTRIES,false);
        configMap.put(ConfigType.DEFAULTCHARACTERMAP,"unicode2latex");
        save(configMap);
    }
}
