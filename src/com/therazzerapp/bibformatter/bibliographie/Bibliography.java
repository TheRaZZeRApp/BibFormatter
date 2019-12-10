package com.therazzerapp.bibformatter.bibliographie;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.0.0
 */
public class Bibliography {
    private LinkedList<Entry> entryList;
    private String name;
    private LinkedList<String> comments;
    private String saveLocation;

    public Bibliography(LinkedList<Entry> entryList, String name, LinkedList<String> comments, String saveLocation) {
        this.entryList = entryList;
        this.name = name;
        this.comments = comments;
        this.saveLocation = saveLocation;
    }

    public Bibliography(LinkedList<Entry> entryList, String name, LinkedList<String> comments) {
        this.entryList = entryList;
        this.name = name;
        this.comments = comments;
        this.saveLocation = "./" + Utils.replaceLast(name,".bib","") + "_formatted.bib";
    }

    public Bibliography(LinkedList<Entry> entryList, String name, String saveLocation) {
        this.entryList = entryList;
        this.name = name;
        this.comments = null;
        this.saveLocation = saveLocation;
    }

    public Bibliography(LinkedList<Entry> entryList, String name) {
        this.entryList = entryList;
        this.name = name;
        this.comments = null;
        this.saveLocation = "./" + Utils.replaceLast(name,".bib","") + "_formatted.bib";
    }

    /**
     *
     * @param types
     * @param entries
     * @param value
     * @param override
     */
    public void createKey(Set<TypeType> types, Set<KeyType> entries, String value, boolean override){
        for (Entry entry : entryList) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                for (KeyType keyType : entries) {
                    if (!entry.getKeys().containsKey(keyType.toString()) || override){
                        entry.getKeys().put(keyType.toString(),value);
                    }
                }
            }
        }
    }

    /**
     * Removes and entry specified by the type of article it is inside and the value it contains.
     * Set inverse to true if you want to delete every entry except the one where the value matches the match
     * @param types
     * @param keys
     * @param match
     * @param inverse
     * @since 0.19.12
     */
    public void removeKey(Set<TypeType> types, Set<KeyType> keys, String match, boolean inverse){
        LinkedList<Entry> tempEntryList = new LinkedList<>(entryList);
        for (Entry entry : entryList) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                if (keys.isEmpty() && match.isEmpty()){
                    tempEntryList.remove(entry);
                    continue;
                }
                for (KeyType keyType : KeyType.values()) {
                    if (keys.isEmpty() || (keys.contains(keyType) && entry.getKeys().containsKey(keyType.toString()))){
                        if (match.isEmpty()|| (entry.getKeys().get(keyType.toString()).matches(match)&& !inverse) || (!entry.getKeys().get(keyType.toString()).matches(match)&& inverse)){
                            tempEntryList.get(tempEntryList.indexOf(entry)).getKeys().remove(keyType.toString());
                        }
                    }
                }
            }
        }
        entryList = tempEntryList;
    }

    /**
     *
     * @param types
     * @param keys
     * @param match
     * @param value
     */
    public void replaceValue(Set<TypeType> types, Set<KeyType> keys, String match, String value){
        for (Entry entry : entryList) {
            if (types.isEmpty() || types.contains(TypeType.valueOf(entry.getType().toUpperCase()))){
                for (String s : entry.getKeys().keySet()) {
                    if (keys.isEmpty() || keys.contains(KeyType.valueOf(s.toUpperCase()))){
                        if (match.isEmpty()|| entry.getKeys().get(s).matches(match)){
                            entryList.get(entryList.indexOf(entry)).getKeys().replace(s,value);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param types
     * @param keys
     * @param match
     * @param override
     */
    public void replaceKey(Set<TypeType> types, LinkedList<KeyType> keys, String match, boolean override){
        if (keys.size()%2 == 0){
            for (Entry entry : entryList) {
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
                entryList.add(entry);
            }
        }
    }

    /**
     * Adds every an entry if the BibTexKey is not already found in the Bibliography
     * @param entry
     * @since 0.19.12
     */
    public void addEntry(Entry entry){
        if (!getBibTexKeys().contains(entry.getBibtexkey())){
            entryList.add(entry);
        }
    }

    /**
     *
     * @param bibTexKeys
     */
    public void removeEntries(Set<String> bibTexKeys){
        LinkedList<Entry> e = new LinkedList<>();
        for (Entry entry : entryList) {
            if (bibTexKeys.contains(entry.getBibtexkey())){
                e.add(entry);
            }
        }
        entryList = e;
    }

    /**
     * Copies the value of the specified key in a specified type to the new replacement key and delete the original key.
     * @param type
     * @param key
     * @param replacement
     */
    public void replaceKey(TypeType type, KeyType key, KeyType replacement, String match, boolean override){
        for (Entry entry : entryList) {
            if ((type == null || entry.getType().equalsIgnoreCase(type.toString())) && entry.getKeys().containsKey(key.toString())){
                if (override || !entry.getKeys().containsKey(replacement.toString())){
                    if (match == null || match.isEmpty() || entry.getKeys().get(key).matches(match)){
                        String temp = entry.getValue(key);
                        entryList.get(entryList.indexOf(entry)).getKeys().remove(key.toString());
                        entryList.get(entryList.indexOf(entry)).getKeys().put(replacement.toString(),temp);
                    }
                }
            }
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public LinkedList<String> getValues(KeyType key){
        LinkedList<String> linkedList = new LinkedList<>();

        for (Entry entry : entryList) {
            linkedList.add(entry.getValue(key));
        }

        return linkedList;
    }

    /**
     *
     * @return
     */
    public LinkedList<Entry> getEntryList() {
        return entryList;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public LinkedList<String> getComments() {
        return comments;
    }

    /**
     *
     * @param entryList
     */
    public void setEntryList(LinkedList<Entry> entryList) {
        this.entryList = entryList;
    }

    /**
     * Returns the location where this bib files wants to be saved at.
     * @return
     */
    public String getSaveLocation() {
        return saveLocation;
    }

    /**
     *
     * @return
     */
    public Set<String> getBibTexKeys(){
        Set<String> keys = new HashSet<>();
        for (Entry entry : entryList) {
            keys.add(entry.getBibtexkey());
        }
        return keys;
    }

    /**
     *
     * @param bibTexKey
     * @return
     */
    public Entry getEntry(String bibTexKey){
        for (Entry entry : entryList) {
            if (entry.getBibtexkey().equals(bibTexKey))
                return entry;
        }
        return null;
    }

    /**
     *
     * @param bibTexKey
     */
    public void removeEntry(String bibTexKey){
        for (Entry entry : entryList) {
            if (entry.getBibtexkey().equals(bibTexKey))
                entryList.remove(entry);
        }
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param comments
     */
    public void setComments(LinkedList<String> comments) {
        this.comments = comments;
    }

    /**
     *
     * @param saveLocation
     */
    public void setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
    }
}
