package com.therazzerapp.bibformatter;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public enum KeyType {
    TITLE,
    SHORTTITLE,
    AUTHOR,
    YEAR,
    MONTH,
    DAY,
    JOURNAL,
    BOOKTITLE,
    LOCATION,
    ON,
    PUBLISHER,
    ADDRESS,
    SERIES,
    VOLUME,
    NUMBER,
    PAGES,
    DOI,
    ISBN,
    ISSN,
    URL,
    URLDATE,
    COPYRIGHT,
    CATEGORY ,
    NOTE,
    METADATA,
    ORGANIZATION,
    MAINTITLE,
    SUBTITLE,
    EDITOR,
    ORIGLANGUAGE,
    TRANSLATOR,
    BOOKSUBTITLE,
    BOOKAUTHOR,
    BOOKTITLEADDON,
    ABSTRACT,
    HOWPUBLISHED,
    KEYWORDS,
    ISSUE,
    BDSK_FILE_1,
    BDSK_URL_1,
    BDSK_URL_2,
    BDSK_URL_3,
    SORTNAME,
    VENUE,
    COPY,
    READ,
    ORIGINAL,
    OWNER,
    TIMESTAMP,
    ANNOTE,
    RATING;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("_","-");
    }


}