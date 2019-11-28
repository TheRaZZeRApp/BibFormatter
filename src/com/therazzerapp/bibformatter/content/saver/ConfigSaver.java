package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;
import com.therazzerapp.bibformatter.content.ConfigType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles saving and default creation of the config json file.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.3.0
 */
public class ConfigSaver {

    /**
     * Saves the specified config map to the default config save location (Can be found in {@link Constants})
     * @param configMap the config map to save
     */
    public static void save(Map<ConfigType, Object> configMap){
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
        config.save(root,new File(Constants.PATH_EXT_DATA+Constants.FILE_EXT_CONFIG+Constants.EXTENSION_JSON));
    }

    /**
     * Puts the default values in the config map.
     */
    public static void createDefaultConfig(){
        Map<ConfigType, Object> configMap = new HashMap<>();
        configMap.put(ConfigType.ENTRYORDER,"title shorttitle author year month day journal booktitle location on publisher address series volume number pages doi isbn issn url urldate copyright category note metadata");
        configMap.put(ConfigType.TYPEORDER,"article book inbook incollection collection inproceedings proceedings phdthesis mastersthesis thesis conference unpublished techreport booklet misc");
        configMap.put(ConfigType.DEFAULTMONTHFORMAT,"name");
        configMap.put(ConfigType.DEFAULTPAGESFORMAT,"double");
        configMap.put(ConfigType.ENCLOSENUMERICS,false);
        configMap.put(ConfigType.INDENTSTYLE,"tabs");
        configMap.put(ConfigType.INDENTSPACESAMOUNT,3);
        configMap.put(ConfigType.INDENTVALUECOLUMM,12);
        configMap.put(ConfigType.WRITEEMPTYENTRIES,false);
        configMap.put(ConfigType.OVERRIDEPUBLISHER,false);
        configMap.put(ConfigType.DEFAULTCHARACTERMAP,"unicode2latex");
        configMap.put(ConfigType.DOISTYLE,"raw");
        save(configMap);
    }
}
