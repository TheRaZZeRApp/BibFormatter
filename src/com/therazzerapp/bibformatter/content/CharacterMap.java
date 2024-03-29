package com.therazzerapp.bibformatter.content;

import com.therazzerapp.bibformatter.content.loader.SpecialCharacterLoader;

import java.io.File;
import java.util.Map;

/**
 * Contains the character map name and a {@link Map} where the key represents the special character and the value represents the replacement.
 * Can be constructed using a txt file where the first line represents the key and the second line the value etc.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.6.4
 */
public class CharacterMap {
    private File file;
    private String name;
    private String regExPattern;
    private Map<String, String> characterMap;

    public CharacterMap(File file) {
        characterMap = SpecialCharacterLoader.load(file);
        name = file.getName().replaceAll(".txt","").toLowerCase();
        regExPattern = SpecialCharacterLoader.loadRegExPatten(file);
    }

    public CharacterMap(String name, Map<String, String> characterMap, String regExPattern) {
        this.name = name;
        this.characterMap = characterMap;
        this.file = null;
        this.regExPattern = regExPattern;
    }

    /**
     * Returns the special character map file.
     * If {@link CharacterMap} was constructed with a {@link Map} and name this returns null.
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns the name of the character map <br>
     * If {@link CharacterMap} was constructed with a {@link File} this represents the file name without ".txt"
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the special character {@link Map}, where the key represents the special character and the value represents the replacement.
     * @return the special character map
     */
    public Map<String, String> getCharacterMap() {
        return characterMap;
    }

    /**
     * Returns the regEx pattern.
     * %VALUE% inside the pattern will be replaced with the special character.
     * @return
     */
    public String getRegExPattern() {
        return regExPattern;
    }
}
