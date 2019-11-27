package com.therazzerapp.bibformatter.content;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.3.0
 */
public enum ConfigType {
    ENTRYORDER("entryOrder", "string"),
    TYPEORDER("typeOrder", "string"),
    OVERRIDEPUBLISHER("overridePublisher","boolean"),
    DEFAULTMONTHFORMAT("defaultMonthFormat", "string"),
    DEFAULTPAGESFORMAT("defaultPagesFormat", "string"),
    ENCLOSENUMERICS("encloseNumerals", "boolean"),
    INDENTSTYLE("indentStyle","string"),
    INDENTSPACESAMOUNT("indentSpacesAmount","int"),
    INDENTVALUECOLUMM("indentValueColumm","int"),
    WRITEEMPTYENTRIES("writeEmptyEntries","boolean"),
    DEFAULTCHARACTERMAP("defaultCharacterMap","string");

    private final String name;
    private final String typ;

    ConfigType(String name, String typ) {
        this.name = name;
        this.typ = typ;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getTyp() {
        return typ;
    }
}
