package com.therazzerapp.bibformatter;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public enum TypeType {
    INPROCEEDINGS,
    ARTICLE,
    TECHREPORT,
    INCOLLECTION,
    BOOK,
    INBOOK,
    PROCEEDINGS,
    PHDTHESIS,
    MASTERSTHESIS,
    ELECTRONIC,
    MISC;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("_","-");
    }
}
