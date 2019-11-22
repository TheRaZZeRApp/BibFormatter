package com.therazzerapp.bibformatter;

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

    public static String trim(String text){
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

    public static boolean isArgumentsValid(String regEx, String arguments){
        return true;
    }


    public static void getCommandArguments(StringBuilder currentMatch, String[] commandLines, int i) {
        if(new File(commandLines[i]).exists()){
            currentMatch.append(getCollectionAsString(FileManager.getFileContent(new File(commandLines[i]))));
            currentMatch.append(" ");
        } else {
            currentMatch.append(commandLines[i]);
            currentMatch.append(" ");
        }
    }

    public static void getCommandTypes(String commandLines, Collection<TypeType> currentTypes) {
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

    public static void getCommandKeys(String commandLines, Collection<KeyType> currentKeys) {
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

    public static int getCommandValues(String[] commandLines, int currentPosition, int i, Collection<TypeType> currentTypes, Collection<KeyType> currentKeys, StringBuilder currentMatch, StringBuilder currentValue){
        switch (commandLines[i]){
            case "+t":
            case "+type":
                currentPosition = 0;
                currentTypes.clear();
                break;
            case "+k":
            case "+key":
                currentPosition = 1;
                currentKeys.clear();
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

    public static boolean isCommandEndReached(String[] commandLines, int i, int end, int currentPosition){
        //Removed ((currentPosition) == end && commandLines[i + 1].startsWith("+"))
        // (commandLines[i+1].matches("(\\+type|\\+t)") ||
        //
        return currentPosition == -1 || i == commandLines.length - 1 || (getPosition(commandLines[i+1],currentPosition) < currentPosition) || (getPosition(commandLines[i+1],currentPosition) == currentPosition && commandLines[i+1].startsWith("+"));
    }
}