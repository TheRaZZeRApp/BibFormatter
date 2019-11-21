package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.manager.SpecialCharacterManager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <VERSION>
 */
public class BibTools {

    /**
     * Encloses every char listed in characters with {} in all the specified types and keys
     * @param bibliographie
     * @param types
     * @param keys
     * @param characters
     */
    public static void capitalizeValue(Bibliographie bibliographie, Set<TypeType> types, Set<KeyType> keys, String characters){

        String regEx;
        if (characters.isEmpty()){
            regEx = "([A-Z]+(?![^\\{]*\\}))";
        } else {
            regEx  = "([" + characters + "]+(?![^\\{]*\\}))";
        }

        Bibliographie tempBib = new Bibliographie(bibliographie.getEntrieList(),bibliographie.getName(),bibliographie.getComments());

        for (Entry entry : bibliographie.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                for (KeyType keyType : KeyType.values()) {
                    if (keys.isEmpty() || keys.contains(keyType)){
                        String temp = entry.getKeys().get(keyType.toString());
                        Matcher matcher = Pattern.compile(regEx).matcher(temp);
                        while (matcher.find()){
                            temp = temp.replaceFirst(regEx,"{"+matcher.group(1)+"}");
                        }
                        System.out.println(temp);
                        tempBib.getEntrieList().get(tempBib.getEntrieList().indexOf(entry)).getKeys().remove(keyType.toString());
                        tempBib.getEntrieList().get(tempBib.getEntrieList().indexOf(entry)).getKeys().put(keyType.toString(),temp);
                    }
                }
            }
        }

        bibliographie.setEntrieList(tempBib.getEntrieList());
    }

    /**
     *
     * @param bibliographie
     * @param name
     */
    public static void formatMonth(Bibliographie bibliographie, boolean name){
        for (int i = 0; i < bibliographie.getEntrieList().size(); i++) {
            if (bibliographie.getEntrieList().get(i).getKeys().containsKey("month")){
                int month = Utils.getMonth(bibliographie.getEntrieList().get(i).getKeys().get("month"));
                if(name){
                    bibliographie.getEntrieList().get(i).getKeys().replace("month",Utils.getMonthByNumber(month));
                } else {
                    bibliographie.getEntrieList().get(i).getKeys().replace("month",Utils.formatMonth(month));
                }
            }
        }
    }

    /**
     *
     * @param bibliographie
     * @param singleLine
     */
    public static void formatPages(Bibliographie bibliographie, boolean singleLine){
        for (int i = 0; i < bibliographie.getEntrieList().size(); i++) {
            if (bibliographie.getEntrieList().get(i).getKeys().containsKey("pages")){
                String page = bibliographie.getEntrieList().get(i).getKeys().get("pages").replaceAll("–","-");;
                if(singleLine && page.matches("[0-9]{1,}--[0-9]{1,}")){
                    bibliographie.getEntrieList().get(i).getKeys().replace("pages",page.replaceAll("--","-"));
                } else if (!singleLine && page.matches("[0-9]{1,}-[0-9]{1,}")){
                    bibliographie.getEntrieList().get(i).getKeys().replace("pages",page.replaceAll("-","--"));
                }
            }
        }
    }

    /**
     * Orders every {@link Entry} in a {@link Bibliographie} by an entry order list specified in a String.
     * @param bibliographie
     * @return
     */
    public static void orderEntries(Bibliographie bibliographie, Set<TypeType> types, ArrayList<KeyType> keys){
        LinkedList<Entry> tempEntrieList = new LinkedList<>();
        for (Entry entry : bibliographie.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                tempEntrieList.add(
                        new Entry(entry.getType(),entry.getBibtexkey(),Utils.orderMapByList(entry.getKeys(), Utils.getObjectAsString(keys.toArray())))
                );
            } else {
                tempEntrieList.add(entry);
            }
        }
        bibliographie.setEntrieList(tempEntrieList);
    }

    /**
     * Orders every {@link Entry} in a {@link Bibliographie} by an entry order list specified in a String.
     * @param bibliographie
     * @param orderList
     * @return
     */
    public static void orderEntries(Bibliographie bibliographie, String orderList){
        LinkedList<Entry> tempEntrieList = new LinkedList<>();
        for (Entry entry : bibliographie.getEntrieList()) {
            tempEntrieList.add(
                    new Entry(entry.getType(),entry.getBibtexkey(),Utils.orderMapByList(entry.getKeys(), orderList))
            );
        }
        bibliographie.setEntrieList(tempEntrieList);
    }

    /**
     * Replaces every special character in a specified key by another symbol linked in the character map specified.
     * @param bibliographie
     * @param key
     */
    public static void saveSpecialCharacters(Bibliographie bibliographie, KeyType key, String characterMap){
        LinkedList<Entry> entrieList = new LinkedList<>();
        for (Entry entry : bibliographie.getEntrieList()) {
            LinkedHashMap<String,String> keys = new LinkedHashMap<>();
            for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                String temp = stringStringEntry.getValue();
                if (stringStringEntry.getKey().equalsIgnoreCase(key.toString())){
                    for (Map.Entry<String, String> characterMapEntry : SpecialCharacterManager.getCharacterMap(characterMap).getCharacterMap().entrySet()) {
                        String pattern = SpecialCharacterManager.getCharacterMap(characterMap).getRegExPattern().replaceAll("%VALUE%",characterMapEntry.getKey());
                        Matcher m = Pattern.compile(pattern).matcher(stringStringEntry.getValue());
                        while (m.find()){
                            temp = Utils.replaceGroup(pattern,temp,2,characterMapEntry.getValue());
                        }
                    }
                }
                keys.put(stringStringEntry.getKey(),temp);
            }
            entrieList.add(new Entry(entry.getType(),entry.getBibtexkey(),keys));
        }
        bibliographie.setEntrieList(entrieList);
    }
}
