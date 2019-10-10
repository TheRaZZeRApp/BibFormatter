package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class FileManager {
    public static Bibliographie getBib(File file){
        BufferedReader bufferedReader;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader;

        final String mainBibKeyEx = "(?<entrie>@[^@]{1,})";

        LinkedList<String> entries = new LinkedList<>();

        Matcher matcher;

        StringBuilder fullText = new StringBuilder();

        try{
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream,StandardCharsets.UTF_8);
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
        return new Bibliographie(formatBib(entries),file.getName());
    }

    private static LinkedList<Entry> formatBib(LinkedList<String> entries){
        LinkedList<Entry> entryLinkedList = new LinkedList<>();
        final String typKeyEx = "@(?<typ>[^{]{1,})\\{(?<bibtexkey>[^,]{1,}),";
        final String keysEx = "(?<keyName>[^ @\\t]{1,})[ ]{1,}=[ \\t]{1,}(?<keyValue>[^\\n]{1,})";

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

    public static void printBib(Bibliographie bibliographie, String fileName){
        StringBuilder sb = new StringBuilder();
        if (bibliographie.getComments() != null){
            for (String s : bibliographie.getComments()) {
                sb.append(s + "\n");
            }
        }
        sb.append("%%Modified using BibFormatter v.1.0\n\n");
        for (Entry entry : bibliographie.getEntrieList()) {
            sb.append(entry.getRawEntry());
        }
        exportFile(sb.toString(),fileName);
    }

    public static void exportFile(String text, String fileName){

        File file = new File(fileName);
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
        }
        try {
            file.createNewFile();
            writer.write(text);
        } catch (IOException ignored) {
        } finally {
            try {
                writer.close();
            } catch (IOException ignored) {
            }
        }
    }

    public static void writeError(String error){
        exportFile(error, BibFormatter.jarPath+"error.log");
    }

    public static void writeDebug(String debug, String path){
        exportFile(debug, path+"debug.txt");
    }
}
