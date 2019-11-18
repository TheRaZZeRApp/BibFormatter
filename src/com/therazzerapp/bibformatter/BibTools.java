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

    public static Bibliographie capitalizeValue(Bibliographie bibliographie, KeyType key){

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
        return bibliographie;
    }

    public static Bibliographie formatMonth(Bibliographie bibliographie){

        for (Entry entry : bibliographie.getEntrieList()) {
            for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                if (stringStringEntry.getKey().equals("month")){
                    if (!stringStringEntry.getValue().matches("^[0-9]*$")){
                        stringStringEntry.setValue(""+Utils.getMonth(stringStringEntry.getValue()));
                    }
                }
            }
        }

        return bibliographie;
    }

    public static Bibliographie formatPages(Bibliographie bibliographie){
        for (Entry entry : bibliographie.getEntrieList()) {
            for (Map.Entry<String, String> stringStringEntry : entry.getKeys().entrySet()) {
                if (stringStringEntry.getKey().equals("pages")){
                    if (stringStringEntry.getValue().matches("[0-9]{1,}-[0-9]{1,}")){
                        stringStringEntry.setValue(stringStringEntry.getValue().replaceAll("-","--"));
                    }
                }
            }
        }
        return bibliographie;
    }

    /**
     * Orders every {@link Entry} in a {@link Bibliographie} by an entry order list specified in the config file.
     * @param bibliographie
     * @param orderList
     * @return
     */
    public static Bibliographie orderEntries(Bibliographie bibliographie, String orderList){
        LinkedList<Entry> tempEntrieList = new LinkedList<>();
        for (Entry entry : bibliographie.getEntrieList()) {
            tempEntrieList.add(
                    new Entry(entry.getType(),entry.getBibtexkey(),Utils.orderMapByList(entry.getKeys(), orderList))
            );
        }
        bibliographie.setEntrieList(tempEntrieList);
        return bibliographie;
    }
}
