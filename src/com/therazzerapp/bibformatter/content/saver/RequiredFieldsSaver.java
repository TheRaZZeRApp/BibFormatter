package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;

import java.io.File;
import java.util.*;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <VERSION>
 */
public class RequiredFieldsSaver {
    public static void save(Map<String, ArrayList<KeyType>> reqFields){
        File file = new File("./Data/valRequiredFields.json");
        JSONConfig config = new JSONConfig();
        JSONConfigSection root = config.newRootSection();

        for (Map.Entry<String, ArrayList<KeyType>> stringArrayListEntry : reqFields.entrySet()) {
            String[] temp = Utils.listToArray(stringArrayListEntry.getValue());
            root.setStringArray(stringArrayListEntry.getKey(),temp);
        }

        config.save(root,file);
    }

    public static void createDefaultRequiredFields(){
        Map<String, ArrayList<KeyType>> reqFieldsMap = new HashMap<>();
        ArrayList<KeyType> temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.BOOKTITLE);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.PUBLISHER);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("inproceedings",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.JOURNAL);
        temp.add(KeyType.NUMBER);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.VOLUME);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("article",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.INSTITUTION);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("techreport",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.BOOKTITLE);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.PUBLISHER);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("incollection",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.PUBLISHER);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("book",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.BOOKTITLE);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.PUBLISHER);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("inbook",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.EDITOR);
        temp.add(KeyType.PUBLISHER);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("proceedings",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.SCHOOL);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("phdthesis",temp);
        reqFieldsMap.put("mastersthesis",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.URL);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("electronic",temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.HOWPUBLISHED);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put("misc",temp);
        save(reqFieldsMap);
    }
}
