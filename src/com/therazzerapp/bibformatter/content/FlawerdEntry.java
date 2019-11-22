package com.therazzerapp.bibformatter.content;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.9.8
 */
public class FlawerdEntry {
    private String bibTexKey;
    private TypeType typeType;
    private Set<KeyType> keyTypes;

    public FlawerdEntry(String bibTexKey, TypeType typeType, Set<KeyType> keyTypes) {
        this.bibTexKey = bibTexKey;
        this.typeType = typeType;
        this.keyTypes = keyTypes;
    }

    public FlawerdEntry(String bibTexKey, TypeType typeType, KeyType keyType) {
        this.bibTexKey = bibTexKey;
        this.typeType = typeType;
        keyTypes = new HashSet<>();
        keyTypes.add(keyType);
    }

    public FlawerdEntry(String bibTexKey, TypeType typeType) {
        this.bibTexKey = bibTexKey;
        this.typeType = typeType;
        this.keyTypes = new HashSet<>();
    }

    public void addKey(KeyType keyType){
        keyTypes.add(keyType);
    }

    public void addKey(String key){
        keyTypes.add(KeyType.valueOf(key.toUpperCase()));
    }

    public String getBibTexKey() {
        return bibTexKey;
    }

    public TypeType getTypeType() {
        return typeType;
    }

    public Set<KeyType> getKeyTypes() {
        return keyTypes;
    }

    public String[] getKeyTypesAsArray(){
        String[] temp = new String[keyTypes.size()];
        int i = 0;
        for (KeyType keyType : keyTypes) {
            temp[i] = keyType.toString();
            i++;
        }
        return temp;
    }
}
