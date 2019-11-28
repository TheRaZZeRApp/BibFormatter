package com.therazzerapp.bibformatter;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.15.10
 */
public class Constants {

    //Main Program Version
    public static final String VERSION = "0.17.12";

    public static final String USSAGE = "Usage: ";

    //Errors
    public static final String ERROR = "Error: ";
    public static final String ERROR_INVALID_ARGUMENTS = "Invalid arguments in command ";
    public static final String ERROR_DEFAULT_CHARACCTERMAP_NOTFOUND = "Default character map not found!";

    //Regex
    public static final String REGEX_YES = "^([yY] *$|yes *$|YES *$|Yes *$) *";
    public static final String REGEX_DOI = "10\\.(\\d{4,9})\\/([-._;()\\/:A-Z0-9]+)";
    public static final String REGEX_WEBSITE = "(https{0,1}:\\/\\/)*[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&\\/\\/=]*)";

    //Charsets
    public static final String CHARSET_ANSI = "windows-1252";

    //Extensions
    public static final String EXTENSION_JSON = ".json";
    public static final String EXTENSION_TXT = ".txt";

    //Paths
    public static final String PATH_EXT_CHECKFILES = "./Data/CheckFiles/";
    public static final String PATH_EXT_DATA = "./Data/";
    public static final String PATH_INT_DATA = "data/";
    public static final String PATH_INT_MAIN = "com/therazzerapp/bibformatter/";

    //Files
    public static final String FILE_EXT_DEFAULT_CHECKFILE = "valRequiredFields";
    public static final String FILE_EXT_DEFAULT_CHARACTARMAP = "unicode2latex";
    public static final String FILE_EXT_DEFAULT_DOIPREFIX = "doiprefix";
    public static final String FILE_EXT_CONFIG = "config";

}
