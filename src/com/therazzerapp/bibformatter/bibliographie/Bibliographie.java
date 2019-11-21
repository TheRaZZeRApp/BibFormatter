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
 * @since <VERSION>
 */
public class Bibliographie {
    private LinkedList<Entry> entrieList;
    private String name;
    private LinkedList<String> comments;

    public Bibliographie(LinkedList<Entry> entrieList, String name, LinkedList<String> comments) {
        this.entrieList = entrieList;
        this.name = name;
        this.comments = comments;
    }

    public Bibliographie(LinkedList<Entry> entrieList, String name) {
        this.entrieList = entrieList;
        this.name = name;
        this.comments = null;
    }

    public void removeEntrie(Set<KeyType> entries){
        for (KeyType entry : entries) {
            removeEntrie(entry);
        }
    }

    public void removeEntrie(KeyType key){
        for (Entry entry1 : entrieList) {
            if (entry1.getKeys().containsKey(key.toString())){
                entry1.getKeys().remove(key.toString());
            }
        }
    }

    public void removeEntrie(String keys){
        String[] values = keys.split(" ");
        for (String value : values) {
            removeEntrie(KeyType.valueOf(value.toUpperCase()));
        }
    }

    /**
     * //todo test
     * @param key
     * @param match
     * @param value
     */
    public void replaceValue(TypeType type, KeyType key, String match, String value){

    }

    /**
     *
     * @param types
     * @param keys
     * @param match
     * @param value
     */
    public void replaceValue(Set<TypeType> types, Set<KeyType> keys, String match, String value){
        match = Utils.replaceLast(match," ","");
        value = Utils.replaceLast(value," ", "");
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
        value = Utils.replaceLast(value," ", "");
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
                    if (match == null || match.isEmpty() || entry.getKeys().get(key).matches(Utils.replaceLast(match," ",""))){
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
}
