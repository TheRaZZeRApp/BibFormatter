package com.therazzerapp.bibformatter.bibliographie;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents the entry of a bib file. Containing the type (i.e. book), the BibKey (i.e. chomsky:1990a)
 * and every entry (i.e. title/author/year)
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.0.0
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

    /**
     * Returns an entry formatted in the way its saved in a bib file.
     * @return
     */
    public String getRawEntry(){

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
            if(stringStringEntry.getValue().matches("^[0-9]*$") && Boolean.getBoolean((String) ConfigManager.getConfigProperty(ConfigType.ENTRYORDER))){
                sb.append(String.format(indent+"%-" + valueColumm +"s %s",stringStringEntry.getKey(),"= " + stringStringEntry.getValue() + ",\n"));
            } else {
                sb.append(String.format(indent+"%-" + valueColumm +"s %s",stringStringEntry.getKey(),"= {" + stringStringEntry.getValue() + "},\n"));
            }
        }
        sb.replace(sb.length()-2,sb.length()-1,"");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Returns the value of a {@link KeyType} in the current entry.
     * @param key
     * @return
     */
    public String getValue(KeyType key){
        for (Map.Entry<String, String> stringStringEntry : keys.entrySet()) {
            if (stringStringEntry.getKey().equalsIgnoreCase(key.toString())){
                return stringStringEntry.getValue();
            }
        }
        return "null";
    }

    /**
     * Returns the type of the current Entry i.e. article
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the BibKey of the current entry i.e. chomsky:1990a
     * @return
     */
    public String getBibtexkey() {
        return bibtexkey;
    }

    /**
     * Returns every key.
     * @return
     */
    public LinkedHashMap<String,String> getKeys() {
        return keys;
    }
}
