package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;
import com.therazzerapp.bibformatter.content.FlawerdEntry;
import com.therazzerapp.bibformatter.content.FileUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Random collection of useful functions.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.0.0
 */
public class Utils {

    /**
     * Exports the content of every {@link FlawerdEntry} in a {@link Set} as a formatted HTML file.
     * @param bib
     * @param flawedEntries
     * @since <version>
     */
    public static void exportFlawedEntryAsHTML(Bibliography bib, Set<FlawerdEntry> flawedEntries){
        //todo
    }

    /**
     * Exports the content of every {@link FlawerdEntry} in a {@link Set} as a plain text file.
     * @param bib
     * @param flawedEntries
     * @since 0.9.8
     */
    public static void exportFlawedEntryAsTXT(Bibliography bib, Set<FlawerdEntry> flawedEntries){
        StringBuilder sb = new StringBuilder();
        for (FlawerdEntry flawerdEntry : flawedEntries) {
            sb.append(flawerdEntry.getTypeType().toString());
            sb.append("@");
            sb.append(flawerdEntry.getBibTexKey());
            for (KeyType keyType : flawerdEntry.getKeyTypes()) {
                sb.append("\n");
                sb.append(keyType);
            }
        }
        FileUtils.exportFile(sb.toString(),Utils.replaceLast(bib.getSaveLocation(),"(\\.bib|\\_formatted\\.bib)","")+"_check");
    }

    /**
     * Exports the content of every {@link FlawerdEntry} in a {@link Set} as a JSON file.
     * @param bib
     * @param flawedEntries
     * @since 0.10.9
     */
    public static void exportFlawedEntryAsJSON(Bibliography bib, Set<FlawerdEntry> flawedEntries){
        JSONConfigSection root = new JSONConfig().newRootSection();
        Set<TypeType> types = new HashSet<>();
        for (FlawerdEntry flawedEntry : flawedEntries) {
            types.add(flawedEntry.getTypeType());
        }
        for (TypeType type : types) {
            JSONConfigSection rType = root.addConfigSectionEntry(type.toString());
            for (FlawerdEntry flawedEntry : flawedEntries) {
                if (flawedEntry.getTypeType() == type){
                    JSONConfigSection eType = rType.addConfigSectionEntry(flawedEntry.getBibTexKey());
                    eType.setStringArray("missing",flawedEntry.getKeyTypesAsArray());
                }
            }
        }
        FileUtils.exportJSONFile(root,Utils.replaceLast(bib.getSaveLocation(),"(\\.bib|\\_formatted\\.bib)","")+"_check");
    }

    /**
     * Replaces the last match of a String by a given String
     * @param text
     * @param regex
     * @param replacement
     * @return
     */
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    /**
     *
     * @param regex
     * @param source
     * @param groupToReplace
     * @param replacement
     * @return
     */
    public static String replaceGroup(String regex, String source, int groupToReplace, String replacement) {
        return replaceGroup(regex, source, groupToReplace, 1, replacement);
    }

    /**
     *
     * @param regex
     * @param source
     * @param groupToReplace
     * @param groupOccurrence
     * @param replacement
     * @return
     */
    public static String replaceGroup(String regex, String source, int groupToReplace, int groupOccurrence, String replacement) {
        Matcher m = Pattern.compile(regex).matcher(source);
        for (int i = 0; i < groupOccurrence; i++)
            if (!m.find()) return source;
        return new StringBuilder(source).replace(m.start(groupToReplace), m.end(groupToReplace), replacement).toString();
    }

    /**
     * Trims a String, removes last "," and {}
     * @param text
     * @return
     */
    public static String trim(String text){
        //todo rewrite, code is half false and complete trash

        if (text.endsWith(",")){
            text = replaceLast(text,",","");
        }

        int openCounter = 0;
        int closeCounter = 0;
        for (char c : text.toCharArray()) {
            if (c == '{'){
                openCounter++;
            } else if (c == '}'){
                closeCounter++;
            }
        }
        while (openCounter>closeCounter){
            text = text.replaceFirst("\\{","");
            openCounter--;
        }
        while (closeCounter>openCounter){
            text = replaceLast(text,"}","");
            closeCounter--;
        }
        text = text.replaceFirst("\\{","");
        text = replaceLast(text,"}","");

        return text;
    }


    /**
     * Returns month number by reading Keys.MONTH entry
     * @param month
     * @return
     */
    public static int getMonth(String month){
        String match;
        for (int i = 1; i < 13; i++) {
            match = "[0-9]{0,}[.-]{0,}[0-9]{0,}[.]" +formatMonth(i) + "[.][0-9]{1,}";
            if (month.equalsIgnoreCase(getMonthByNumber(i)) || month.matches(match) || month.equals(formatMonth(i))){
                return i;
            }
        }
        return -1;
    }

    /**
     * Formats one character month in two typ character design. i.e. 3 -> 03
     * @param number
     * @return
     */
    public static String formatMonth(int number){
        if (number < 1 || number > 12){
            return "null";
        } else if (number < 10) {
            return "0"+number;
        } else {
            return ""+number;
        }
    }

    /**
     * Returns month name by value i.e. 1 -> jan
     * @param number
     * @return
     */
    public static String getMonthByNumber(int number){
        switch (number){
            case 1:
                return "jan";
            case 2:
                return "feb";
            case 3:
                return "mar";
            case 4:
                return "apr";
            case 5:
                return "may";
            case 6:
                return "jun";
            case 7:
                return "jul";
            case 8:
                return "aug";
            case 9:
                return "sep";
            case 10:
                return "okt";
            case 11:
                return "nov";
            case 12:
                return "dec";
            default:
                return "null";
        }
    }

    /**
     * Orders a map by a entry list
     * @param map
     *          The map to be ordered
     * @param orderList
     *          The List of keys to order after
     * @return
     */
    public static LinkedHashMap<String,String> orderMapByList(Map<String,String> map, String orderList){
        LinkedHashMap<String,String> tempMap = new LinkedHashMap<>();
        String[] values = orderList.split(" ");
        for (String value : values) {
            if (map.containsKey(value)){
                tempMap.put(value,map.get(value));
                map.remove(value);
            }
        }
        for (String s : map.keySet()) {
            tempMap.put(s,map.get(s));
        }
        return tempMap;
    }

    /**
     * Returns a Collection as a String with a space between every appended String.
     * @param set
     * @return
     */
    public static String getCollectionAsString(Collection<String> set){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : set) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * Returns an Array as a String with a space between every appended String.
     * @return string of every object in the Array
     */
    public static String getObjectAsString(Object[] a){
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : a) {
            stringBuilder.append(o.toString());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * Convert any list object to a String array.
     * @param list
     * @param <T>
     * @return
     */
    public static <T> String[] listToArray(List<T> list) {
        String [] array = new String[list.size()];
        for (int i = 0; i < array.length; i++)
            array[i] = list.get(i).toString();
        return array;
    }

    /**
     * If a string contains a doi at any point it will return only the matched doi.
     * @param text the text to check/format
     * @return the formatted doi, null if no doi was found.
     * @since 0.16.12
     */
    public static String formatDOI(String text, String format){
        int i = 0;
        String s = "";
        switch (format){
            case "raw":
                s = "$";
                break;
            case "doi":
                s = "doi:$";
                break;
            case "proxy":
                s = "https://doi.org/$";
                break;
            case "url":
                s = "\\url{https://doi.org/$}";
                break;
            case "prefix":
                s = "$";
                i = 1;
                break;
        }
        Matcher m = Pattern.compile(Constants.REGEX_DOI,Pattern.CASE_INSENSITIVE).matcher(text);
        if (m.find()){
            return s.replaceAll("\\$",m.group(i));
        }
        return null;
    }
}