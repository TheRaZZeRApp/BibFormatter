package com.therazzerapp.bibformatter.bibliographie;

import com.therazzerapp.bibformatter.Keys;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
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

    public void removeEntrie(Set<Keys> entries){
        for (Keys entry : entries) {
            for (Entry entry1 : entrieList) {
                if (entry1.getKeys().keySet().contains(entry.toString())){
                    entry1.getKeys().remove(entry.toString());
                }
            }
        }
    }

    public void removeEntrie(Keys key){
        Set<Keys> keys = new HashSet<>();
        keys.add(key);
        removeEntrie(keys);
    }

    public void replaceValue(Keys key, String match, String replacement){
        for (Entry entry : entrieList) {
            for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                if (stringStringEntry.getKey().equals(key.toString())&&stringStringEntry.getValue().equals(match)){
                    entrieList.get(entrieList.indexOf(entry)).getKeys().replace(key.toString(),replacement);
                }
            }
        }
    }

    public void replaceKey(Keys key, Keys replacement, String type){
        for (Entry entry : entrieList) {
            if ((type == null || entry.getType().equalsIgnoreCase(type)) && entry.getKeys().containsKey(key.toString()) && !entry.getKeys().containsKey(replacement.toString())){
                String temp = entry.getValue(key);
                entry.getKeys().remove(key);
                entry.getKeys().put(replacement.toString(),temp);
            }
        }
    }

    public void replaceKey(Keys key, Keys replacement){
        replaceKey(key,replacement,null);
    }

    public LinkedList<String> getValues(Keys key){
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
