package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.content.FlawerdEntry;
import com.therazzerapp.bibformatter.content.RequiredFields;
import com.therazzerapp.bibformatter.manager.SpecialCharacterManager;
import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.1.0
 */
public class BibTools {

    /**
     * Checks if an {@link Entry} is missing keys.
     * @param bibliographie
     * @param types
     * @param requiredFields
     * @param args
     * @param print
     * @return
     */
    public static Set<FlawerdEntry> checkType(Bibliographie bibliographie, Set<TypeType> types, RequiredFields requiredFields, String args, boolean print){
        Set<FlawerdEntry> flawerdEntries = new HashSet<>();
        for (Entry entry : bibliographie.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                FlawerdEntry flawerdEntry = new FlawerdEntry(entry.getBibtexkey(),entry.getTypeType());
                for (KeyType requiredField : requiredFields.getRequiredFields(entry.getTypeType())) {
                    if (!entry.getKeys().containsKey(requiredField.toString())){
                        flawerdEntry.addKey(requiredField);
                    }
                }
                if (!flawerdEntry.getKeyTypes().isEmpty()){
                    flawerdEntries.add(flawerdEntry);
                }
            }
        }
        if (print){
            if (args.isEmpty() || args.equals("html")){
                Utils.exportFlawedEntryAsHTML(bibliographie, flawerdEntries);
            } else if (args.equals("txt")){
                Utils.exportFlawedEntryAsTXT(bibliographie, flawerdEntries);
            }else if (args.equals("json")){
                Utils.exportFlawedEntryAsJSON(bibliographie, flawerdEntries);
            }
        }
        return flawerdEntries;
    }

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
     */
    public static void formatMonth(Bibliographie bibliographie, Set<TypeType> types, String style){
        for (Entry entry : bibliographie.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                if (entry.getKeys().containsKey("month")){
                    int i = bibliographie.getEntrieList().indexOf(entry);
                    int month = Utils.getMonth(bibliographie.getEntrieList().get(i).getKeys().get("month"));
                    switch (style){
                        case "name":
                            bibliographie.getEntrieList().get(i).getKeys().replace("month",Utils.getMonthByNumber(month));
                            break;
                        case "number":
                            bibliographie.getEntrieList().get(i).getKeys().replace("month",Utils.formatMonth(month));
                            break;
                    }
                }
            }
        }
    }

    /**
     *
     * @param bibliographie
     * @param types
     * @param style
     */
    public static void formatPages(Bibliographie bibliographie, Set<TypeType> types, String style){
        for (Entry entry : bibliographie.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                if (entry.getKeys().containsKey("pages")){
                    int i = bibliographie.getEntrieList().indexOf(entry);
                    String page = bibliographie.getEntrieList().get(i).getKeys().get("pages").replaceAll("–","-");;
                    if(style.matches("single") && page.matches("[0-9]{1,}--[0-9]{1,}")){
                        bibliographie.getEntrieList().get(i).getKeys().replace("pages",page.replaceAll("--","-"));
                    } else if (style.matches("double") && page.matches("[0-9]{1,}-[0-9]{1,}")){
                        bibliographie.getEntrieList().get(i).getKeys().replace("pages",page.replaceAll("-","--"));
                    }
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
     * Replaces every special character in a specified key by another symbol linked in the character map specified.
     * @param bibliographie
     * @param types
     * @param keys
     * @param characterMap
     */
    public static void saveSpecialCharacters(Bibliographie bibliographie, Set<TypeType> types, Set<KeyType> keys, String characterMap){
        LinkedList<Entry> entrieList = new LinkedList<>();
        for (Entry entry : bibliographie.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                LinkedHashMap<String,String> keyMap = new LinkedHashMap<>();
                for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                    String temp = stringStringEntry.getValue();
                    if (keys.isEmpty() || keys.contains(KeyType.valueOf(stringStringEntry.getKey().toUpperCase()))){
                        for (Map.Entry<String, String> characterMapEntry : SpecialCharacterManager.getCharacterMap(characterMap).getCharacterMap().entrySet()) {
                            String pattern = SpecialCharacterManager.getCharacterMap(characterMap).getRegExPattern().replaceAll("%VALUE%",characterMapEntry.getKey());
                            Matcher m = Pattern.compile(pattern).matcher(stringStringEntry.getValue());
                            while (m.find()){
                                temp = Utils.replaceGroup(pattern,temp,2,characterMapEntry.getValue());
                            }
                        }
                    }
                    keyMap.put(stringStringEntry.getKey(),temp);
                }
                entrieList.add(new Entry(entry.getType(),entry.getBibtexkey(),keyMap));
            } else {
                entrieList.add(new Entry(entry.getType(),entry.getBibtexkey(),entry.getKeys()));
            }
        }
        bibliographie.setEntrieList(entrieList);
    }
}
