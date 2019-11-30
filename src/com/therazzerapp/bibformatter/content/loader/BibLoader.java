package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads a .bib file and converts the content into a {@link Bibliography} object.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.0.0
 */
public class BibLoader {

    /**
     * Loads a specified file and reads the content line by line. If the format is correct it will return a
     * {@link Bibliography} obhect.
     * @param file the file to load
     * @param saveLocation the location where the bib will evenetually get saved at
     * @return the generated bib file
     */
    public static Bibliography load(File file, String saveLocation){
        BufferedReader bufferedReader;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader;
        Matcher matcher;
        LinkedList<String> entries = new LinkedList<>();
        LinkedList<String> comments = new LinkedList<>();
        StringBuilder fullText = new StringBuilder();

        try{
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String text = bufferedReader.readLine();
            while (text != null){
                fullText.append(text).append("\n");
                if (text.startsWith("%")){
                    comments.add(text);
                }
                text = bufferedReader.readLine();
            }
            matcher = Pattern.compile(Constants.REGEX_BIBKEY).matcher(fullText.toString());
            while (matcher.find()){
                entries.add(matcher.group("entrie"));
            }
        } catch (IOException ignored){
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ignored) {
            }
        }
        return new Bibliography(formatEntries(entries),file.getName().replaceAll(".bib",""),comments,saveLocation);
    }

    /**
     * Extracts the Key type and value from a list of strings.
     * @param entries the list of strings to analyze
     * @return the list of generated entries
     */
    private static LinkedList<Entry> formatEntries(LinkedList<String> entries){
        LinkedList<Entry> entryLinkedList = new LinkedList<>();
        for (String entry : entries) {
            Matcher typ = Pattern.compile(Constants.REGEX_BIBTYPETYPE).matcher(entry);
            String typKey = "";
            String bibKey = "";
            while (typ.find()){
                typKey = typ.group("typ");
                bibKey = typ.group("bibtexkey");
            }
            Matcher keyMatcher = Pattern.compile(Constants.REGEX_BIBKEYTYPE).matcher(entry);
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
