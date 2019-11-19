package com.therazzerapp.bibformatter.bibliographie;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class Entry {
    private String type;
    private String bibtexkey;
    private LinkedHashMap<String,String> keys;

    public Entry(String type, String bibtexkey, LinkedHashMap<String,String> keys) {
        this.type = type;
        this.bibtexkey = bibtexkey;
        this.keys = keys;
    }

    public String getRawEntry(boolean encloseNumerals){

        String indent = "\t";
        if (ConfigManager.getConfigProperty(ConfigType.INDENTSTYLE).equals("spaces")){
            indent = "";
            for (int i = 0; i < (int) ConfigManager.getConfigProperty(ConfigType.INDENTSPACESAMOUNT); i++) {
                indent+=" ";
            }
        }

        int valueColumm = (int) ConfigManager.getConfigProperty(ConfigType.INDENTVALUECOLUMM);

        StringBuilder sb = new StringBuilder();
        sb.append("@"+type+"{"+bibtexkey+",\n");
        for (Map.Entry<String, String> stringStringEntry : keys.entrySet()) {
            if (stringStringEntry.getValue().equals("") && !(boolean) ConfigManager.getConfigProperty(ConfigType.WRITEEMPTYENTRIES)){
                continue;
            }
            if(stringStringEntry.getValue().matches("^[0-9]*$") && encloseNumerals){
                sb.append(String.format(indent+"%-" + valueColumm +"s %s",stringStringEntry.getKey(),"= " + stringStringEntry.getValue() + ",\n"));
            } else {
                sb.append(String.format(indent+"%-" + valueColumm +"s %s",stringStringEntry.getKey(),"= {" + stringStringEntry.getValue() + "},\n"));
            }
        }
        sb.replace(sb.length()-2,sb.length()-1,"");
        sb.append("}\n");
        return sb.toString();
    }

    public String getRawEntry(){
        return getRawEntry(true);
    }

    public String getValue(KeyType key){
        for (Map.Entry<String, String> stringStringEntry : keys.entrySet()) {
            if (stringStringEntry.getKey().equalsIgnoreCase(key.toString())){
                return stringStringEntry.getValue();
            }
        }
        return "null";
    }

    public String getType() {
        return type;
    }

    public String getBibtexkey() {
        return bibtexkey;
    }

    public LinkedHashMap<String,String> getKeys() {
        return keys;
    }
}
