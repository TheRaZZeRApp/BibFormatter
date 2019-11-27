package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.content.loader.DoiPrefixLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <rezzer101@googlemail.com>
 * @since 0.16.12
 */
public class DoiPrefixManager {
    private static Map<Integer,String> dois = new HashMap<>();

    /**
     * Checks if the ./Data/doiprefix.txt is found.<br>
     * If not generate it.
     */
    public static void init(){
        if(!new File("./Data/doiprefix.txt").exists()){
            StringBuilder sb = new StringBuilder();
            for (String s : FileManager.getFileContentJar("data/doiprefix.txt")) {
                sb.append(s).append("\n");
            }
            FileManager.exportFile(sb.toString(),"./Data/doiprefix.txt");
        }
        dois = DoiPrefixLoader.load(new File("./Data/doiprefix.txt"));
    }

    /**
     * @return
     */
    public static Map<Integer, String> getDoiPrefixMap() {
        return dois;
    }

    /**
     * @param publisherName
     * @return
     */
    public static String getDoi(String publisherName){
        for (Map.Entry<Integer, String> integerStringEntry : dois.entrySet()) {
            if (integerStringEntry.getValue().equals(publisherName)){
                return "10."+integerStringEntry.getKey();
            }
        }
        return "10.";
    }

    /**
     * Get the doi prefix listed in the doiprefix.txt file if a matching prefix was found for the specified publisher name.<br>
     * The doi prefix is defined as the 4-9 numbers after 10.<br>
     * i.e. the prefix of doi:10.1162/foobar would be 1162<br>
     *
     * @param publisherName the publisher name (i.e. MIT Press)
     * @return the doi prefix of the specified publisher,<br>
     *          or 1000 if no publisher was found.
     */
    public static int getDoiPrefix(String publisherName){
        for (Map.Entry<Integer, String> integerStringEntry : dois.entrySet()) {
            if (integerStringEntry.getValue().equals(publisherName)){
                return integerStringEntry.getKey();
            }
        }
        return 1000;
    }

    /**
     *
     * @param doi
     * @return
     */
    public static String getPublisherName(String doi){
        Matcher m = Pattern.compile(Constants.REGEX_DOI,Pattern.CASE_INSENSITIVE).matcher(doi);
        return dois.get(Integer.parseInt(m.group(1)));
    }

    /**
     *
     * @param doiPrefix
     * @return
     */
    public static boolean containsPrefix(int doiPrefix){
        return dois.containsKey(doiPrefix);
    }

    /**
     *
     * @param doiPrefix
     * @return
     */
    public static String getPublisherName(int doiPrefix){
        return dois.get(doiPrefix);
    }

    /**
     *
     * @param publisher
     * @return
     */
    public static boolean containsPublisher(String publisher){
        return dois.containsValue(publisher);
    }
}
