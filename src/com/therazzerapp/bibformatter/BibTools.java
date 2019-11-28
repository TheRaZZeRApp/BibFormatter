package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.content.FlawerdEntry;
import com.therazzerapp.bibformatter.content.RequiredFields;
import com.therazzerapp.bibformatter.manager.SpecialCharacterManager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.1.0
 */
public class BibTools {

    /**
     * Search for DOIs in the specified types and the specified keys.
     * If found, format them in the specified style and copy the value in the Doi key.
     * @param bibliography the {@link Bibliography} to modify
     * @param types the types to apply changes to
     * @param keys the keys in which to search for DOIs
     * @param format the format in which the DOI gets transformed
     * @since 0.17.12
     */
    public static void formatDoi(Bibliography bibliography, Set<TypeType> types, Set<KeyType> keys, String format){
        for (Entry entry : bibliography.getEntrieList()) {
            if (entry.getKeys().containsKey(KeyType.DOI.toString())){
                entry.getKeys().replace(KeyType.DOI.toString(),Utils.formatDOI(entry.getKeys().get(KeyType.DOI.toString()),format));
            } else {
                if (types.isEmpty() || types.contains(entry.getTypeType())) {
                    Map<String, String> temp = new HashMap<>(entry.getKeys());
                    for (Map.Entry<String, String> stringStringEntry : temp.entrySet()) {
                        if ((keys.isEmpty() && stringStringEntry.getKey().equals(KeyType.DOI.toString())) || keys.contains(KeyType.valueOf(stringStringEntry.getKey().toUpperCase()))) {
                            if (Utils.formatDOI(stringStringEntry.getValue(),format)!=null){
                                bibliography.getEntrieList().get(bibliography.getEntrieList().indexOf(entry)).getKeys().put(KeyType.DOI.toString(),Utils.formatDOI(stringStringEntry.getValue(),format));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param bibliography
     * @param types
     * @param keys
     * @param dois
     * @param override
     * @since <version>
     */
    public static void generatePublisher(Bibliography bibliography, Set<TypeType> types, Set<KeyType> keys, Set<Integer> dois, boolean override){

    }

    /**
     * Orders every Entry inside a bib file by a list.
     * @param bibliography
     * @param types
     */
    public static void orderTypes(Bibliography bibliography, ArrayList<TypeType> types){
        LinkedList<Entry> entryList = new LinkedList<>();
        for (TypeType type : types) {
            for (Entry entry : bibliography.getEntrieList()) {
                if (entry.getType().equals(type.toString())){
                    entryList.add(entry);
                }
            }
        }
        bibliography.setEntrieList(entryList);
    }

    /**
     * Adds every entry from the second bib to first one. If the bibTexKey is already found the entry from prim will be completed with the entries from sec
     * @param prim
     * @param sec
     * @param types
     * @param keys
     * @return
     */
    public static Bibliography mergeBibliographies(Bibliography prim, Bibliography sec, Set<TypeType> types, Set<KeyType> keys){
        LinkedList<Entry> mergedEntries = new LinkedList<>(prim.getEntrieList());
        for (Entry entry : sec.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                if (prim.getBibTexKeys().contains(entry.getBibtexkey())){

                    LinkedHashMap<String,String> newKeyMap = new LinkedHashMap<>();
                    newKeyMap.putAll(prim.getEntry(entry.getBibtexkey()).getKeys());
                    for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                        if (!newKeyMap.containsKey(stringStringEntry.getKey()) && (keys.isEmpty() || keys.contains(KeyType.valueOf(stringStringEntry.getKey().toUpperCase())))){
                            newKeyMap.put(stringStringEntry.getKey(),stringStringEntry.getValue());
                        }
                    }

                    mergedEntries.add(new Entry(entry.getType(),entry.getBibtexkey(),newKeyMap));
                } else {
                    mergedEntries.add(entry);
                }
            }
        }
        return new Bibliography(mergedEntries,prim.getName()+sec.getName(),null,prim.getSaveLocation());
    }

    /**
     * Checks if an {@link Entry} is missing keys.
     * @param bibliography
     * @param types
     * @param requiredFields
     * @param args
     * @param print
     * @return
     */
    public static Set<FlawerdEntry> checkType(Bibliography bibliography, Set<TypeType> types, RequiredFields requiredFields, String args, boolean print){
        Set<FlawerdEntry> flawedEntries = new HashSet<>();
        for (Entry entry : bibliography.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                FlawerdEntry flawerdEntry = new FlawerdEntry(entry.getBibtexkey(),entry.getTypeType());
                for (KeyType requiredField : requiredFields.getRequiredFields(entry.getTypeType())) {
                    if (!entry.getKeys().containsKey(requiredField.toString())){
                        flawerdEntry.addKey(requiredField);
                    }
                }
                if (!flawerdEntry.getKeyTypes().isEmpty()){
                    flawedEntries.add(flawerdEntry);
                }
            }
        }
        if (print){
            if (args.isEmpty() || args.equals("html")){
                Utils.exportFlawedEntryAsHTML(bibliography, flawedEntries);
            } else if (args.equals("txt")){
                Utils.exportFlawedEntryAsTXT(bibliography, flawedEntries);
            }else if (args.equals("json")){
                Utils.exportFlawedEntryAsJSON(bibliography, flawedEntries);
            }
        }
        return flawedEntries;
    }

    /**
     * Encloses every char listed in characters with {} in all the specified types and keys
     * @param bibliography
     * @param types
     * @param keys
     * @param characters
     */
    public static void capitalizeValue(Bibliography bibliography, Set<TypeType> types, Set<KeyType> keys, String characters){

        String regEx;
        if (characters.isEmpty()){
            regEx = "([A-Z]+(?![^\\{]*\\}))";
        } else {
            regEx  = "([" + characters + "]+(?![^\\{]*\\}))";
        }

        Bibliography tempBib = new Bibliography(bibliography.getEntrieList(), bibliography.getName(), bibliography.getComments());

        for (Entry entry : bibliography.getEntrieList()) {
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

        bibliography.setEntrieList(tempBib.getEntrieList());
    }

    /**
     *
     * @param bibliography
     */
    public static void formatMonth(Bibliography bibliography, Set<TypeType> types, String style){
        for (Entry entry : bibliography.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                if (entry.getKeys().containsKey("month")){
                    int i = bibliography.getEntrieList().indexOf(entry);
                    int month = Utils.getMonth(bibliography.getEntrieList().get(i).getKeys().get("month"));
                    switch (style){
                        case "name":
                            bibliography.getEntrieList().get(i).getKeys().replace("month",Utils.getMonthByNumber(month));
                            break;
                        case "number":
                            bibliography.getEntrieList().get(i).getKeys().replace("month",Utils.formatMonth(month));
                            break;
                    }
                }
            }
        }
    }

    /**
     *
     * @param bibliography
     * @param types
     * @param style
     */
    public static void formatPages(Bibliography bibliography, Set<TypeType> types, String style){
        for (Entry entry : bibliography.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                if (entry.getKeys().containsKey("pages")){
                    int i = bibliography.getEntrieList().indexOf(entry);
                    String page = bibliography.getEntrieList().get(i).getKeys().get("pages").replaceAll("–","-");;
                    if(style.matches("single") && page.matches("[0-9]{1,}--[0-9]{1,}")){
                        bibliography.getEntrieList().get(i).getKeys().replace("pages",page.replaceAll("--","-"));
                    } else if (style.matches("double") && page.matches("[0-9]{1,}-[0-9]{1,}")){
                        bibliography.getEntrieList().get(i).getKeys().replace("pages",page.replaceAll("-","--"));
                    }
                }
            }
        }
    }

    /**
     * Orders every {@link Entry} in a {@link Bibliography} by an entry order list specified in a String.
     * @param bibliography
     * @param types
     * @param keys
     */
    public static void orderEntries(Bibliography bibliography, Set<TypeType> types, ArrayList<KeyType> keys){
        LinkedList<Entry> tempEntryList = new LinkedList<>();
        for (Entry entry : bibliography.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                tempEntryList.add(
                        new Entry(entry.getType(),entry.getBibtexkey(),Utils.orderMapByList(entry.getKeys(), Utils.getObjectAsString(keys.toArray())))
                );
            } else {
                tempEntryList.add(entry);
            }
        }
        bibliography.setEntrieList(tempEntryList);
    }

    /**
     * Replaces every special character in a specified key by another symbol linked in the character map specified.
     * @param bibliography
     * @param types
     * @param keys
     * @param characterMap
     */
    public static void saveSpecialCharacters(Bibliography bibliography, Set<TypeType> types, Set<KeyType> keys, String characterMap){
        LinkedList<Entry> entryList = new LinkedList<>();
        for (Entry entry : bibliography.getEntrieList()) {
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
                entryList.add(new Entry(entry.getType(),entry.getBibtexkey(),keyMap));
            } else {
                entryList.add(new Entry(entry.getType(),entry.getBibtexkey(),entry.getKeys()));
            }
        }
        bibliography.setEntrieList(entryList);
    }

    /**
     * Encloses every url in a specified type and a specified key with brackets \\url(x)
     * @param bibliography
     * @param types
     * @param keys
     */
    public static void formatURL(Bibliography bibliography, Set<TypeType> types, Set<KeyType> keys){
        LinkedList<Entry> entryList = new LinkedList<>();
        for (Entry entry : bibliography.getEntrieList()) {
            if (types.isEmpty() || types.contains(entry.getTypeType())){
                LinkedHashMap<String,String> keyMap = new LinkedHashMap<>();
                for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                    String temp = stringStringEntry.getValue();
                    if (keys.isEmpty() || keys.contains(KeyType.valueOf(stringStringEntry.getKey().toUpperCase()))){
                        Matcher m = Pattern.compile(Constants.REGEX_WEBSITE).matcher(stringStringEntry.getValue());
                        while (m.find()){
                            temp = Utils.replaceGroup(Constants.REGEX_WEBSITE,temp,0,"\\url{" + m.group(0) + "}");
                        }
                    }
                    keyMap.put(stringStringEntry.getKey(),temp);
                }
                entryList.add(new Entry(entry.getType(),entry.getBibtexkey(),keyMap));
            } else {
                entryList.add(new Entry(entry.getType(),entry.getBibtexkey(),entry.getKeys()));
            }
        }
        bibliography.setEntrieList(entryList);
    }
}
