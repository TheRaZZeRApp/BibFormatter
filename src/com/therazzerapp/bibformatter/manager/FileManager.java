package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <VERSION>
 */
public class FileManager {

    /**
     * Export a {@link JSONConfigSection} as file to a give path
     * @param jsonConfigSection
     * @param path
     */
    public static void exportJSONFile(JSONConfigSection jsonConfigSection, String path){
        File file = new File(path+ ".json");
        JSONConfig config = new JSONConfig();
        config.save(jsonConfigSection,file);
    }

    /**
     * Export the content of a {@link String} to a file.
     * path needs to contain an extension.
     * @param text
     * @param path
     */
    public static void exportFile(String text, String path){
        File file = new File(path);
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),StandardCharsets.UTF_8));
        } catch (FileNotFoundException ignored) {
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

    /**
     * Reads content from files inside the jar file (path starts from "com/therazzerapp/bibformatter/")
     * @param path
     * @return
     */
    public static ArrayList<String> getFileContentJar(String path) {
        ArrayList<String> text = new ArrayList<>();
        ClassLoader.getSystemClassLoader();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("com/therazzerapp/bibformatter/" + path);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        try (BufferedReader is = new BufferedReader(streamReader)) {
            for (String line; (line = is.readLine()) != null; ) {
                text.add(line);
            }
        } catch (IOException ignored) {
        }
        return text;
    }

    /**
     * Get the content of a file as an {@link ArrayList<String>}.
     * Each line in the file represents a new {@link String} in the array.
     * @param file
     * @return
     */
    public static ArrayList<String> getFileContent(File file){
        ArrayList<String> text = new ArrayList<>();
        BufferedReader br;
        InputStreamReader isr;
        try (InputStream is = new FileInputStream(file)) {
            isr = new InputStreamReader(is, Charset.forName("windows-1252"));
            br = new BufferedReader(isr);

            String line = br.readLine();
            while (line != null) {
                text.add(line);
                line = br.readLine();
            }
        } catch (IOException ignored) {
        }
        return text;
    }

    /**
     * Returns a {@link Set <String>} of every match found in a file.
     * @param file
     * @return
     */
    public static Set<String> getMatches(File file, String match){
        Set<String> citations = new HashSet<>();
        Matcher matcher;
        for (String s : FileManager.getFileContent(file)) {
            matcher = Pattern.compile(match).matcher(s);
            if (matcher.find()){
                citations.add(matcher.group(1));
            }
        }
        return citations;
    }

}
