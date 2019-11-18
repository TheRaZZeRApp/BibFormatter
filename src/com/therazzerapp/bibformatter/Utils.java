package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class Utils {

    public static final String entryOrder = "title shorttitle author year month day journal booktitle location on publisher address series volume number pages doi isbn issn url urldate copyright category note metadata";

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
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

    public static void debugEntries(Bibliographie bibliographie, String path){
        StringBuilder sb = new StringBuilder();
        for (Entry entry : bibliographie.getEntrieList()) {
            if (!entry.getKeys().keySet().contains("title")){
                sb.append("Missing Title \t\t" + entry.getBibtexkey() + "\n");
            }
            if (!entry.getKeys().keySet().contains("author")){
                sb.append("Missing Author \t\t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("year")){
                sb.append("Missing Year \t\t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("journal") && entry.getType().equals("article")){
                sb.append("Missing Journal \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("publisher") && entry.getType().equals("book")){
                sb.append("Missing Publisher \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("chapter") && entry.getType().equals("inbook")){
                sb.append("Missing Chapter \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("booktitle") && (entry.getType().equals("incollection") || entry.getType().equals("inproceedings"))){
                sb.append("Missing Book Title \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("school") && entry.getType().equals("mastersthesis")){
                sb.append("Missing School \t\t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("institution") && entry.getType().equals("techreport")){
                sb.append("Missing Institution " + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("note") && entry.getType().equals("unpublished")){
                sb.append("Missing Note \t\t" + entry.getBibtexkey()+ "\n");
            }
        }
        FileManager.writeDebug(sb.toString(),path);
    }

    public static void debugEntries(Bibliographie bibliographie, String type, String path){
        StringBuilder sb = new StringBuilder();

        for (Entry entry : bibliographie.getEntrieList()) {
            if (entry.getType().equals(type)){

                sb.append(String.format("%-20s %-5s %-5s %s",entry.getBibtexkey(),entry.getValue(Keys.YEAR),entry.getValue(Keys.AUTHOR),entry.getValue(Keys.TITLE).replaceAll("}","").replaceAll("\\{","") + "\n"));
                if (!entry.getKeys().keySet().contains("author")){
                    sb.append("Missing Author\n");
                }
                if (!entry.getKeys().keySet().contains("location")){
                    sb.append("Missing Location\n");
                }
                if (!entry.getKeys().keySet().contains("number")){
                    sb.append("Missing Number\n");
                }
                if (!entry.getKeys().keySet().contains("publisher")){
                    sb.append("Missing Publisher\n");
                }
                if (!entry.getKeys().keySet().contains("series")){
                    sb.append("Missing Series\n");
                }
                if (!entry.getKeys().keySet().contains("subtitle")){
                    sb.append("Missing Subtitle\n");
                }
                if (!entry.getKeys().keySet().contains("title")){
                    sb.append("Missing Title\n");
                }
                if (!entry.getKeys().keySet().contains("year")){
                    sb.append("Missing Year\n");
                }
                sb.append("====================================="+"\n");
            }
        }
        FileManager.writeDebug(sb.toString(),path);
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
            if (month.equalsIgnoreCase(getMonthByNumber(i)) || month.matches(match)){
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
            }
        }
        return tempMap;
    }
}

