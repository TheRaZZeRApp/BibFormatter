package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class BibTools {

    public static void capitalizeValue(Bibliographie bibliographie, KeyType key){

        final String capEx = "(?<cap>[A-Z]+(?![^\\{]*\\}))";
        final String bugEx = "(?<cap>\\{\\{[A-Z]{1,}\\}\\})";
        LinkedList<Entry> entrieList = new LinkedList<>();

        Matcher matcher;
        Matcher matcher2;

        for (Entry entry : bibliographie.getEntrieList()) {
            LinkedHashMap<String,String> keys = new LinkedHashMap<>();
            for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                String temp = stringStringEntry.getValue();
                if (stringStringEntry.getKey().equalsIgnoreCase(key.toString())){
                    matcher = Pattern.compile(capEx).matcher(temp);
                    while (matcher.find()){
                        temp = temp.replace(matcher.group("cap"), "{" + matcher.group("cap") + "}");
                    }
                    matcher2 = Pattern.compile(bugEx).matcher(temp);
                    while (matcher2.find()){
                        temp = temp.replace(matcher2.group("cap"),matcher2.group("cap").replaceAll("\\{\\{","{").replaceAll("}}","}"));
                    }
                }
                keys.put(stringStringEntry.getKey(),temp);
            }
            entrieList.add(new Entry(entry.getType(),entry.getBibtexkey(),keys));
        }
        bibliographie.setEntrieList(entrieList);
    }

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
     * Orders every {@link Entry} in a {@link Bibliographie} by an entry order list specified in the config file.
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
}
