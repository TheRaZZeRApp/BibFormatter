package com.therazzerapp.bibformatter.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <rezzer101@googlemail.com>
 * @author ar56te876mis
 * @since 0.3.3
 */
public class JSONConfig {

    /**
     *
     * @param file
     * @return
     */
    public JSONConfigSection load(File file) {
        try {
            JsonElement parse;
            try (JsonReader jsonReader = new JsonReader(new FileReader(file))) {
                parse = new JsonParser().parse(jsonReader);
            }
            if (parse == null || parse.isJsonNull()) {
                parse = new JsonObject();
            }
            return new JSONConfigSection(parse.getAsJsonObject());
        } catch (IOException ex) {
            return newRootSection();
        }
    }

    /**
     *
     * @param section
     * @param file
     */
    public void save(JSONConfigSection section, File file) {
        try {
            file.getParentFile().mkdirs();
            try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(file))) {
                jsonWriter.setIndent("    ");
                new Gson().toJson(section.getObject(), jsonWriter);
                jsonWriter.flush();
            }
        } catch (IOException e) {
        }
    }

    /**
     *
     * @return
     */
    public JSONConfigSection newRootSection() {
        return new JSONConfigSection(new JsonObject());
    }
}
