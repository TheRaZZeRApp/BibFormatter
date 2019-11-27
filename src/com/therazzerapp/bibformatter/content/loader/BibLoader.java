package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.0.0
 */
public class BibLoader {

    /**
     *
     * @param file
     * @param saveLocation
     * @return
     */
    public static Bibliography load(File file, String saveLocation){
        BufferedReader bufferedReader;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader;

        final String mainBibKeyEx = "(?<entrie>@[^@]{1,})";

        LinkedList<String> entries = new LinkedList<>();

        Matcher matcher;

        StringBuilder fullText = new StringBuilder();

        try{
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String text = bufferedReader.readLine();
            while (text != null){
                fullText.append(text+"\n");
                text = bufferedReader.readLine();
            }
            matcher = Pattern.compile(mainBibKeyEx).matcher(fullText.toString());
            while (matcher.find()){
                entries.add(matcher.group("entrie"));
            }
        } catch (IOException ignored){
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
            }
        }
        return new Bibliography(formatEntries(entries),file.getName().replaceAll(".bib",""),saveLocation);
    }

    /**
     *
     * @param entries
     * @return
     */
    private static LinkedList<Entry> formatEntries(LinkedList<String> entries){
        LinkedList<Entry> entryLinkedList = new LinkedList<>();
        final String typKeyEx = "@(?<typ>[^{]{1,})\\{(?<bibtexkey>[^,]{1,}),";
        final String keysEx = "(?<keyName>[^ @\\t]{1,})[ ]{1,}=[ \\t]{1,}(?<keyValue>[^\\n]{1,})";

        Set<String> key = new HashSet<>();
        for (String entry : entries) {
            Matcher typ = Pattern.compile(typKeyEx).matcher(entry);
            String typKey = "";
            String bibKey = "";
            while (typ.find()){
                typKey = typ.group("typ");
                bibKey = typ.group("bibtexkey");

            }
            Matcher keyMatcher = Pattern.compile(keysEx).matcher(entry);
            LinkedHashMap<String,String> entrieMap = new LinkedHashMap<>();
            while (keyMatcher.find()){
                String temp = Utils.trim(keyMatcher.group("keyValue"));
                entrieMap.put(keyMatcher.group("keyName").toLowerCase(),temp);
            }
            if(!entrieMap.isEmpty()){
                entryLinkedList.add(new Entry(typKey.toLowerCase(),bibKey,entrieMap));
            }
        }
        return entryLinkedList;
    }
}
