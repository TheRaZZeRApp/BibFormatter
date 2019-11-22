package com.therazzerapp.bibformatter;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public enum TypeType {
    DEFAULT,
    INPROCEEDINGS,
    ARTICLE,
    TECHREPORT,
    INCOLLECTION,
    COLLECTION,
    BOOK,
    INBOOK,
    PROCEEDINGS,
    PHDTHESIS,
    MASTERSTHESIS,
    ELECTRONIC,
    THESIS,
    ONLINE,
    UNPUBLISHED,
    CONFERENCE,
    MANUAL,
    BOOKLET,
    MISC;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("_","-");
    }
}
