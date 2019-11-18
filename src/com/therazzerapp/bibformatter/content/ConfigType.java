package com.therazzerapp.bibformatter.content;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public enum ConfigType {
    ENTRYORDER("entryOrder", "string");

    private final String name;
    private final String typ;

    ConfigType(String name, String typ) {
        this.name = name;
        this.typ = typ;
    }

    public String getName() {
        return name;
    }

    public String getTyp() {
        return typ;
    }
}
