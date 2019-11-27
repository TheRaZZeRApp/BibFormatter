package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.config.JSONConfig;
import com.therazzerapp.bibformatter.config.JSONConfigSection;
import com.therazzerapp.bibformatter.content.RequiredFields;
import com.therazzerapp.bibformatter.manager.FileManager;

import java.util.*;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.4.3
 */
public class RequiredFieldsSaver {

    /**
     *
     * @param requiredFields
     */
    public static void save(RequiredFields requiredFields){
        String path = "./Data/CheckFiles/" + requiredFields.getName();
        JSONConfigSection root = new JSONConfig().newRootSection();

        for (Map.Entry<TypeType, ArrayList<KeyType>> stringArrayListEntry : requiredFields.getRequiredFieldsMap().entrySet()) {
            String[] temp = Utils.listToArray(stringArrayListEntry.getValue());
            root.setStringArray(stringArrayListEntry.getKey().toString(),temp);
        }

        FileManager.exportJSONFile(root,path);
    }

    /**
     *
     */
    public static void createDefaultRequiredFields(){
        Map<TypeType, ArrayList<KeyType>> reqFieldsMap = new HashMap<>();
        ArrayList<KeyType> temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.BOOKTITLE);
        temp.add(KeyType.PUBLISHER);
        reqFieldsMap.put(TypeType.INPROCEEDINGS,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.JOURNAL);
        temp.add(KeyType.NUMBER);
        temp.add(KeyType.VOLUME);
        reqFieldsMap.put(TypeType.ARTICLE,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.INSTITUTION);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put(TypeType.TECHREPORT,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.BOOKTITLE);
        temp.add(KeyType.PUBLISHER);
        reqFieldsMap.put(TypeType.INCOLLECTION,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.PUBLISHER);
        reqFieldsMap.put(TypeType.BOOK,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.PAGES);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.BOOKTITLE);
        temp.add(KeyType.PUBLISHER);
        reqFieldsMap.put(TypeType.INBOOK,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.EDITOR);
        temp.add(KeyType.PUBLISHER);
        reqFieldsMap.put(TypeType.PROCEEDINGS,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.SCHOOL);
        reqFieldsMap.put(TypeType.PHDTHESIS,temp);
        reqFieldsMap.put(TypeType.MASTERSTHESIS,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.URL);
        reqFieldsMap.put(TypeType.ELECTRONIC,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        temp.add(KeyType.HOWPUBLISHED);
        reqFieldsMap.put(TypeType.MISC,temp);
        temp = new ArrayList<>();
        temp.add(KeyType.AUTHOR);
        temp.add(KeyType.TITLE);
        temp.add(KeyType.YEAR);
        reqFieldsMap.put(TypeType.DEFAULT,temp);
        save(new RequiredFields(reqFieldsMap,"valRequiredFields"));
    }
}
