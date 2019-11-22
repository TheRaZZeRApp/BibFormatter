package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;
import com.therazzerapp.bibformatter.content.FlawerdEntry;
import com.therazzerapp.bibformatter.manager.FileManager;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.0.0
 */
public class Utils {

    /**
     * Exports the content of every {@link FlawerdEntry} in a {@link Set} as a formatted HTML file.
     * @param bib
     * @param flawedEntries
     */
    public static void exportFlawedEntryAsHTML(Bibliographie bib, Set<FlawerdEntry> flawedEntries){

    }

    /**
     * Exports the content of every {@link FlawerdEntry} in a {@link Set} as a plain text file.
     * @param bib
     * @param flawedEntries
     */
    public static void exportFlawedEntryAsTXT(Bibliographie bib, Set<FlawerdEntry> flawedEntries){
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
        FileManager.exportFile(sb.toString(),"./" + bib.getName() + "_flaws.txt");
    }

    /**
     * Exports the content of every {@link FlawerdEntry} in a {@link Set} as a JSON file.
     * @param bib
     * @param flawedEntries
     */
    public static void exportFlawedEntryAsJSON(Bibliographie bib, Set<FlawerdEntry> flawedEntries){
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
        FileManager.exportJSONFile(root,"./" + bib.getName() + "_flaws");
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

    public static String replaceGroup(String regex, String source, int groupToReplace, String replacement) {
        return replaceGroup(regex, source, groupToReplace, 1, replacement);
    }

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
        if (text.endsWith(",")){
            text = replaceLast(text,",","");
        }
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
     * Returns a Collection as a String with a space between every appended String.
     * @return
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
     * Checks if argument represents a file of arguments.
     * @param rawCommand
     * @return
     */
    public static String getCommand(String rawCommand){
        if (new File(rawCommand).exists()){
            StringBuilder sb = new StringBuilder();
            for (String c : FileManager.getFileContent(new File(rawCommand))) {
                sb.append(c).append(" ");
            }
            return sb.toString();
        } else {
            return rawCommand;
        }
    }

    /**
     * Checks if the given arguments are correct formatted.
     * @param regEx
     * @param arguments
     * @return
     */
    public static boolean isArgumentsValid(String regEx, String arguments){
        if (regEx.isEmpty()){
            return true;
        }
        return arguments.matches(regEx);
    }


    /**
     * Adds every argument found in a file or String to a given {@link StringBuilder}.
     * @param currentMatch
     * @param commandLines
     * @param i
     */
    public static void getCommandArguments(StringBuilder currentMatch, String[] commandLines, int i) {
        if(new File(commandLines[i].trim()).exists()){
            currentMatch.append(getCollectionAsString(FileManager.getFileContent(new File(commandLines[i]))));
            currentMatch.append(" ");
        } else {
            currentMatch.append(commandLines[i].trim());
            currentMatch.append(" ");
        }
    }

    /**
     * Adds every {@link TypeType} found in a file or String and adds it to a {@link Collection<TypeType>} of types.
     * @param commandLines
     * @param currentTypes
     */
    public static void getCommandTypes(String commandLines, Collection<TypeType> currentTypes) {
        if (currentTypes != null)
        if(new File(commandLines).exists()){
            for (String s : FileManager.getFileContent(new File(commandLines))) {
                for (String s1 : s.split(" ")) {
                    currentTypes.add(TypeType.valueOf(s1.toUpperCase()));
                }
            }
        } else {
            currentTypes.add(TypeType.valueOf(commandLines.toUpperCase()));
        }
    }

    /**
     * Adds every {@link KeyType} found in a file or String and adds it to a {@link Collection<KeyType>} of keys.
     * @param commandLines
     * @param currentKeys
     */
    public static void getCommandKeys(String commandLines, Collection<KeyType> currentKeys) {
        if (currentKeys != null)
        if(new File(commandLines).exists()){
            for (String s : FileManager.getFileContent(new File(commandLines))) {
                for (String s1 : s.split(" ")) {
                    currentKeys.add(KeyType.valueOf(s1.toUpperCase()));
                }
            }
        } else {
            currentKeys.add(KeyType.valueOf(commandLines.toUpperCase()));
        }
    }


    /**
     * Analyses a argument of command and interpret its values.
     * @param commandLines
     * @param currentPosition
     * @param i
     * @param currentTypes
     * @param currentKeys
     * @param currentMatch
     * @param currentValue
     * @return
     */
    public static int getCommandValues(String[] commandLines, int currentPosition, int i, Collection<TypeType> currentTypes, Collection<KeyType> currentKeys, StringBuilder currentMatch, StringBuilder currentValue){
        switch (commandLines[i]){
            case "+t":
            case "+type":
                currentPosition = 0;
                if (currentTypes != null) currentTypes.clear();
                break;
            case "+k":
            case "+key":
                currentPosition = 1;
                if (currentKeys != null) currentKeys.clear();
                break;
            case "+m":
            case "+match":
                currentPosition = 2;
                currentMatch.setLength(0);
                break;
            case "+v":
            case "+value":
                currentPosition = 3;
                currentValue.setLength(0);
                break;
            default:
                switch (currentPosition){
                    case 0:
                        Utils.getCommandTypes(commandLines[i], currentTypes);
                        break;
                    case 1:
                        Utils.getCommandKeys(commandLines[i], currentKeys);
                        break;
                    case 2:
                        Utils.getCommandArguments(currentMatch, commandLines, i);
                        break;
                    case 3:
                        Utils.getCommandArguments(currentValue, commandLines, i);
                        break;
                    default:
                        return -1;
                }
        }
        return currentPosition;
    }

    /**
     * Returns the position of the argument.
     * @param arg
     * @param position
     * @return
     */
    public static int getPosition(String arg, int position){
        switch (arg){
            case "+t":
            case "+type":
                return 0;
            case "+k":
            case "+key":
                return 1;
            case "+m":
            case "+match":
                return 2;
            case "+v":
            case "+value":
                return 3;
            default:
                return position;
        }
    }

    /**
     * Checks if the command end is reached or if a new argument section of a command starts.
     * @param commandLines
     * @param i
     * @param end
     * @param currentPosition
     * @return
     */
    public static boolean isCommandEndReached(String[] commandLines, int i, int end, int currentPosition){
        return currentPosition == -1 || i == commandLines.length - 1 || (getPosition(commandLines[i+1],currentPosition) < currentPosition) || (getPosition(commandLines[i+1],currentPosition) == currentPosition && commandLines[i+1].startsWith("+"));
    }
}