package com.therazzerapp.bibformatter.content;

import com.therazzerapp.bibformatter.Constants;
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
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.1.0
 */
public class FileUtils {

    /**
     * Export a {@link JSONConfigSection} as file to a give path.
     * @param jsonConfigSection the json config you want to safe
     * @param path the file path (needs no extension)
     */
    public static void exportJSONFile(JSONConfigSection jsonConfigSection, String path){
        File file = new File(path+ Constants.EXTENSION_JSON);
        JSONConfig config = new JSONConfig();
        config.save(jsonConfigSection,file);
    }

    /**
     * Export the content of a {@link String} to a file.
     * Path needs to contain an extension.
     * @param text the text to be exported
     * @param path the file path (needs extension)
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
     * Reads content from files inside the jar file (path starts from "com/therazzerapp/bibformatter/").
     * @param path the path inside this jar file
     * @return the content of the file
     */
    public static ArrayList<String> getFileContentJar(String path) {
        ArrayList<String> text = new ArrayList<>();
        ClassLoader.getSystemClassLoader();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(Constants.PATH_INT_MAIN + path);
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
     * @param file the file to read in
     * @return the content of the file
     */
    public static ArrayList<String> getFileContent(File file){
        ArrayList<String> text = new ArrayList<>();
        BufferedReader br;
        InputStreamReader isr;
        try (InputStream is = new FileInputStream(file)) {
            isr = new InputStreamReader(is, Charset.forName(Constants.CHARSET_ANSI));
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
     * @param file the file to search in
     * @param match the match to search for
     * @return every match found
     */
    public static Set<String> getMatches(File file, String match){
        Set<String> citations = new HashSet<>();
        Matcher matcher;
        for (String s : FileUtils.getFileContent(file)) {
            matcher = Pattern.compile(match).matcher(s);
            if (matcher.find()){
                citations.add(matcher.group(1));
            }
        }
        return citations;
    }

}
