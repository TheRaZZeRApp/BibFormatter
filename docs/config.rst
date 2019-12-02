=============
Configuration
=============

The config.json file gets generated (if not already existing) by clicking on the ``BibFormatter.jar``

Configuration Files
===================

Config file location: ``./Data/config.json``


Settings
========

.. csv-table::
    :header: Setting, Default, Description
    :widths: 12, 5, 30

    entryOrder,"list of keys","The default list to order keys inside an entry"
    typeOrder,"list of types","The default list to order entries inside a bibliography"
    overridePublisher,false,"If set to true the generatePublisher command will override any already existing publisher value if a doi was found."
    defaultMonthFormat,"name","The default style to format the value of the month key (e.g. name=jan number=01)"
    defaultPagesFormat,"double","The default style to format the value of the pages key (e.g. double=-- single=-). Changes will effect the symbol between the two page numbers."
    encloseNumerals,false,"If set to true every value inside the bibliography will be enclosed with {} even if its just a numeral."
    indentStyle,"tabs","Define the indent style of the keys and values."
    indentSpaceAmount,3,"The amount of spaces before each key."
    indentValueColumn,12,"The column every value will be placed at."
    writeEmptyEntries,false,"If set to true it will export keys with empty values."
    doiStyle,"raw","Set the default style to format the doi. (raw/doi/proxy/url/prefix)"
    defaultCharacterMap,"unicode2latex","Set the default character map file name."
