package com.therazzerapp.bibformatter.content;

import com.therazzerapp.bibformatter.content.loader.SpecialCharacterLoader;

import java.io.File;
import java.util.Map;

public class CharacterMap {
    private File file;
    private String name;
    private Map<String, String> characterMap;

    public CharacterMap(File file) {
        characterMap = SpecialCharacterLoader.load(file);
        name = file.getName().replaceAll(".txt","").toLowerCase();
    }

    public CharacterMap(String name, Map<String, String> characterMap) {
        this.name = name;
        this.characterMap = characterMap;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getCharacterMap() {
        return characterMap;
    }
}
