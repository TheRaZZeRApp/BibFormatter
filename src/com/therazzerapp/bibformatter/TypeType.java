package com.therazzerapp.bibformatter;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.1.0
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
