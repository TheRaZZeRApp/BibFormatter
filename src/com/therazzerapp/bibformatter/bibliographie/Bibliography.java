package com.therazzerapp.bibformatter.bibliographie;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.0.0
 */
public class Bibliography {
    private LinkedList<Entry> entrieList;
    private String name;
    private LinkedList<String> comments;
    private String saveLocation;

    public Bibliography(LinkedList<Entry> entrieList, String name, LinkedList<String> comments, String saveLocation) {
        this.entrieList = entrieList;
        this.name = name;
        this.comments = comments;
        this.saveLocation = saveLocation;
    }

    public Bibliography(LinkedList<Entry> entrieList, String name, LinkedList<String> comments) {
        this.entrieList = entrieList;
        this.name = name;
        this.comments = comments;
        this.saveLocation = "./" + Utils.replaceLast(name,".bib","") + "_formatted.bib";
    }

    public Bibliography(LinkedList<Entry> entrieList, String name, String saveLocation) {
        this.entrieList = entrieList;
        this.name = name;
        this.comments = null;
        this.saveLocation = saveLocation;
    }

    public Bibliography(LinkedList<Entry> entrieList, String name) {
        this.entrieList = entrieList;
        this.name = name;
        this.comments = null;
        this.saveLocation = "./" + Utils.replaceLast(name,".bib","") + "_formatted.bib";
    }

    /**
     *
     * @param types
     * @param entries
     * @param value
     */
    public void createKey(Set<TypeType> types, Set<KeyType> entries, String value){
        for (Entry entry : entrieList) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                for (KeyType keyType : entries) {
                    if (!entry.getKeys().containsKey(keyType.toString())){
                        entry.getKeys().put(keyType.toString(),value);
                    }
                }
            }
        }
    }

    /**
     * Removes and entry specified by the type of article it is inside and the value it contains.
     * Set inverse to true if you want to delete every entry except the specified.
     * @param types
     * @param keyTypeSet
     * @param match
     * @param inverse
     */
    public void removeKey(Set<TypeType> types, Set<KeyType> keyTypeSet, String match, boolean inverse){
        LinkedList<Entry> tempEentrieList = new LinkedList<>(entrieList);
        for (Entry entry : entrieList) {
            if ((!types.contains(entry.getTypeType()) && inverse && keyTypeSet.isEmpty()) || types.isEmpty() || types.contains(entry.getTypeType())){
                if (keyTypeSet.isEmpty()){
                    if (match == null || match.isEmpty()){
                        if (inverse){
                            if (!types.contains(entry.getTypeType())){
                                tempEentrieList.remove(entry);
                            }
                        } else {
                            tempEentrieList.remove(entry);
                        }
                    } else {
                        for (KeyType type : KeyType.values()) {
                            if (entry.getKeys().containsKey(type.toString()) && entry.getKeys().get(type.toString()).matches(match)){
                                tempEentrieList.get(tempEentrieList.indexOf(entry)).getKeys().remove(type.toString());
                            }
                        }
                    }
                } else {
                    if (inverse){
                        for (KeyType type : KeyType.values()) {
                            if (!keyTypeSet.contains(type) && entry.getKeys().containsKey(type.toString()) && (match == null || match.isEmpty() || entry.getKeys().get(type.toString()).matches(match))){
                                tempEentrieList.get(tempEentrieList.indexOf(entry)).getKeys().remove(type.toString());
                            }
                        }
                    } else {
                        for (KeyType keyType : keyTypeSet) {
                            if (entry.getKeys().containsKey(keyType.toString()) && (match == null || match.isEmpty() || entry.getKeys().get(keyType.toString()).matches(match))){
                                tempEentrieList.get(tempEentrieList.indexOf(entry)).getKeys().remove(keyType.toString());
                            }
                        }
                    }
                }
            }
        }
        entrieList = tempEentrieList;
    }

    /**
     *
     * @param types
     * @param keys
     * @param match
     * @param value
     */
    public void replaceValue(Set<TypeType> types, Set<KeyType> keys, String match, String value){
        for (Entry entry : entrieList) {
            if (types.isEmpty() || types.contains(TypeType.valueOf(entry.getType().toUpperCase()))){
                for (String s : entry.getKeys().keySet()) {
                    if (keys.isEmpty() || keys.contains(KeyType.valueOf(s.toUpperCase()))){
                        if (match.isEmpty()|| entry.getKeys().get(s).matches(match)){
                            entrieList.get(entrieList.indexOf(entry)).getKeys().replace(s,value);
                        }
                    }
                }
            }
        }
    }

    /**
     * Overrides every value in every key of every type
     * @param value
     */
    public void setAllValues(String value){
        for (Entry entry : entrieList) {
            for (String s : entry.getKeys().keySet()) {
                entrieList.get(entrieList.indexOf(entry)).getKeys().replace(s,value);
            }
        }
    }

    public void replaceKey(Set<TypeType> types, LinkedList<KeyType> keys, String match, boolean override){
        if (keys.size()%2 == 0){
            for (Entry entry : entrieList) {
                if (types.isEmpty() || types.contains(TypeType.valueOf(entry.getType().toUpperCase()))){
                    for (int i = 0; i < keys.size(); i+=2) {
                         replaceKey(TypeType.valueOf(entry.getType().toUpperCase()),keys.get(i),keys.get(i+1),match,override);
                    }
                }
            }
        }
    }

    /**
     * Adds every entry from a list if the BibTexKey is not already found in the Bibliography
     * @param entries
     */
    public void addEntries(LinkedList<Entry> entries){
        for (Entry entry : entries) {
            if (!getBibTexKeys().contains(entry.getBibtexkey())){
                entrieList.add(entry);
            }
        }
    }

    /**
     *
     * @param entries
     */
    public void removeEntries(LinkedList<Entry> entries){
        Set<String> bibTexKeys = new HashSet<>();
        for (Entry entry : entries) {
            bibTexKeys.add(entry.getBibtexkey());
        }
        removeEntries(bibTexKeys);
    }

    /**
     *
     * @param bibTexKeys
     */
    public void removeEntries(Set<String> bibTexKeys){

        LinkedList<Entry> e = new LinkedList<>();

        for (Entry entry : entrieList) {
            if (bibTexKeys.contains(entry.getBibtexkey())){
                e.add(entry);
            }
        }

        entrieList = e;
    }

    /**
     * Copies the value of the specified key in a specified type to the new replacement key and delete the original key.
     * @param type
     * @param key
     * @param replacement
     */
    public void replaceKey(TypeType type, KeyType key, KeyType replacement){
        replaceKey(type,key,replacement,null, false);
    }

    /**
     * Copies the value of the specified key in a specified type to the new replacement key and delete the original key.
     * @param type
     * @param key
     * @param replacement
     */
    public void replaceKey(TypeType type, KeyType key, KeyType replacement, String match, boolean override){
        for (Entry entry : entrieList) {
            if ((type == null || entry.getType().equalsIgnoreCase(type.toString())) && entry.getKeys().containsKey(key.toString())){
                if (override || !entry.getKeys().containsKey(replacement.toString())){
                    if (match == null || match.isEmpty() || entry.getKeys().get(key).matches(match)){
                        String temp = entry.getValue(key);
                        entrieList.get(entrieList.indexOf(entry)).getKeys().remove(key.toString());
                        entrieList.get(entrieList.indexOf(entry)).getKeys().put(replacement.toString(),temp);
                    }
                }
            }
        }
    }

    /**
     * Copies the value of the specified key to the new replacement key and delete the original key.
     * @param key
     * @param replacement
     */
    public void replaceKey(KeyType key, KeyType replacement){
        replaceKey(null,key,replacement,null, false);
    }

    public LinkedList<String> getValues(KeyType key){
        LinkedList<String> linkedList = new LinkedList<>();

        for (Entry entry : entrieList) {
            linkedList.add(entry.getValue(key));
        }

        return linkedList;
    }

    public LinkedList<Entry> getEntrieList() {
        return entrieList;
    }

    public String getName() {
        return name;
    }

    public LinkedList<String> getComments() {
        return comments;
    }

    public void setEntrieList(LinkedList<Entry> entrieList) {
        this.entrieList = entrieList;
    }

    /**
     * Returns the location where this bib files wants to be saved at.
     * @return
     */
    public String getSaveLocation() {
        return saveLocation;
    }

    public Set<String> getBibTexKeys(){
        Set<String> keys = new HashSet<>();
        for (Entry entry : entrieList) {
            keys.add(entry.getBibtexkey());
        }
        return keys;
    }

    public Entry getEntry(String bibTexKey){
        for (Entry entry : entrieList) {
            if (entry.getBibtexkey().equals(bibTexKey))
                return entry;
        }
        return null;
    }

    public void removeEntry(String bibTexKey){
        for (Entry entry : entrieList) {
            if (entry.getBibtexkey().equals(bibTexKey))
                entrieList.remove(entry);
        }
    }
}
