package com.therazzerapp.bibformatter.content;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
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

    /**
     *
     * @param keyType
     */
    public void addKey(KeyType keyType){
        keyTypes.add(keyType);
    }

    /**
     *
     * @param key
     */
    public void addKey(String key){
        keyTypes.add(KeyType.valueOf(key.toUpperCase()));
    }

    /**
     *
     * @return
     */
    public String getBibTexKey() {
        return bibTexKey;
    }

    /**
     *
     * @return
     */
    public TypeType getTypeType() {
        return typeType;
    }

    /**
     *
     * @return
     */
    public Set<KeyType> getKeyTypes() {
        return keyTypes;
    }

    /**
     *
     * @return
     */
    public String[] getKeyTypesAsArray(){
        String[] t = new String[keyTypes.size()];
        int i = 0;
        for (KeyType k : keyTypes) {
            t[i] = k.toString();
            i++;
        }
        return t;
    }
}
