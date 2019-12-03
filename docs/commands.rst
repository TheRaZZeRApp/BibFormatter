========
Commands
========

.. contents::
    :local:
    :backlinks: none

.. note::

    Arguments enclosed in ``[ ]`` are optional, those enclosed in ``< >`` are required.

    Type represents things like article/book/inbook etc. and Key represents things like author/title/pages/doi etc.

    Every command parameter argument can be written down in a .txt file and loaded by entering the exact path (useful if a path contains a space or for regex searches in certain commands).
    (e.g. ``-createKey +t article C:/Types.txt +k title +v C:/Text.txt``)

.. tip::

    You can access a command listing via the ``-help`` command.

    +type / +key / +match / +value can be shortened to +t / +k / +m /+v

General Commands
~~~~~~~~~~~~~~~~
.. raw:: html

    <span id="command--bibliography"></span>

.. topic:: ``-bibliography``
    Everytime this command gets issued the currently loaded bibliography file will be saved (with all applied changes) and the new one will be loaded. Every command after this one will only effect the bibliography file loaded by this command (until -b is found again etc.).
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Load a new .bib file. If no output path is specified the modified bib gets saved in the same folder where the original .bib file is located."
        **Shortcut**,"``-b``"
        **Usage**,"``-bibliography <file> [output]``"
          ``<file>``,"Exact path to the bibliography file you want to load"
          ``[output]``,"The exact save path. If not specified this will be the current location of the loaded ´´.bib´´ file appended with ´´_formatted.bib´´"

.. raw:: html

    <span id="command--debug"></span>

.. topic:: ``-debug``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Debugs the modification process. If not specified debugging is set to no."
        **Shortcut**,"``-d``"
        **Usage**,"``-debug {yes|no}``"


Bibliography Commands
~~~~~~~~~~~~~~~~
.. raw:: html

    <span id="command--addEntry"></span>

.. topic:: ``-addEntry``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Adds every entry from other .bib files to the main bibliography file."
        **Shortcut**,"``-ae``"
        **Usage**,"``-addEntry [+type <types>] <+value <files>>``"
          ``[+type <types>]``,"Only entries of this type get added. If not specified every type will be added"
          ``<+value <files>>``,"A list of all bibliography files. No spaces allowed"

.. raw:: html

    <span id="command--checkType"></span>

.. topic:: ``-checkType``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Checks if an entry is missing keys and exports the missing keys for every entry as a list."
        **Shortcut**,"``-ct``"
        **Usage**,"``-checkType [+type <types>] [+match <required>] [+value <style>]``"
          ``[+type <types>]``,"Only entries of this type will be checked. If not specified every type will be checked"
          ``[+match <required>]``,"Specify a .json file which represents a map of required keys for every type. If not specified the default map will be loaded (``.\Data\CheckFiles\valRequiredFields.json``)"
          ``[+value <style>]``,"Specify the output style of the missing keys. (txt/json/html)"
        **Styles**,"``txt`` Exports the list of missing keys as plain txt"
        ,"``json`` Exports the list of missing keys as json array"
        ,"``html`` Exports the list of missing keys as a html document (e.g. BibChecker)"

.. raw:: html

    <span id="command--createKey"></span>

.. topic:: ``-createKey``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Creates a new key with a specified value."
        **Shortcut**,"``-ck``"
        **Usage**,"``-createKey [+type <types>] [+key <keys>] [+value <value>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Specify the keys you want to create. If multiple keys are listed they will all get the same value"
          ``[+value <value>]``,"Specify the value to put in the new key"

.. raw:: html

    <span id="command--formatDOI"></span>

.. topic:: ``-formatDOI``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Searches for DOIs and adds the correct formatted value in the doi key."
        **Shortcut**,"``-fd``"
        **Usage**,"``-createKey [+type <types>] [+key <keys>] [+value <style>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Specify the keys in which you want to search for DOIs. If no keys are specified only the doi key will be analysed.``"
          ``[+value <style>]``,"Specify the style to format the DOI after (raw/doi/proxy/url/prefix)"
        **Styles**,"``raw`` 10.1000/foobar"
        ,"``doi`` doi:10.1000/foobar"
        ,"``proxy`` https://doi.org/10.1000/foobar"
        ,"``url`` \\url{https://doi.org/10.1000/foobar}"
        ,"``prefix`` 1000"

.. raw:: html

    <span id="command--formatMonth"></span>

.. topic:: ``-formatMonth``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Formats the month value into the described format."
        **Shortcut**,"``-fm``"
        **Usage**,"``-formatMonth [+type <types>] [+value <style>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+value <style>]``,"Specify the style to format the month after (name/number). If no parameter is specified the standard format is ``name``."
        **Styles**,"``name`` jul"
        ,"``number`` 07"

.. raw:: html

    <span id="command--formatPages"></span>

.. topic:: ``-formatPages``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Formats the pages value into the described format."
        **Shortcut**,"``-fp``"
        **Usage**,"``-formatPages [+type <types>] [+value <style>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+value <style>]``,"Specify the style to format the pages after (single/double). If no parameter is specified the standard format is ``double``."
        **Styles**,"``single`` 157-160"
        ,"``double`` 157--160"

.. raw:: html

    <span id="command--formatURL"></span>

.. topic:: ``-formatURL``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Formats a url found in a value into the correct format."
        **Shortcut**,"``-fu``"
        **Usage**,"``-createKey [+type <types>] [+key <keys>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Only the keys specified here will be affected. If not specified every key will be affected"

.. raw:: html

    <span id="command--fromAux"></span>

.. topic:: ``-fromAux``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Removes every entry in the currently loaded bibliography that is not cited in the ``.aux`` file."
        **Shortcut**,"``-fa``"
        **Usage**,"``-fromAux <+value <path>>``"
          ``<+value <path>>``,"The exact path to the ``.aux`` file you want to load (no spaces allowed)"

.. raw:: html

    <span id="command--generatePublisher"></span>

.. topic:: ``-generatePublisher``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"If an entry contains a DOI it will search in the DOI prefix list after a matching publisher name and if found add this as value to a new publisher key."
        **Shortcut**,"``-gp``"
        **Usage**,"``-generatePublisher [+type <types>] [+key <keys>] [+match <dois>] [+value {y|n}]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Specify the keys in which you want to search for DOIs. If no keys are specified only the doi key will be analysed."
          ``[+match <dois>]``,"Specify the DOIs you want to accept. If no DOIs are specified every DOI will be accepted."
          ``[+value {yes|no}]``,"Set to yes/y if you want to override any already existing publisher value. If not specified overriding is set to no/n."

.. raw:: html

    <span id="command--mergeBibliographies"></span>

.. topic:: ``-mergeBibliographies``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Add every entry or key from the specified ``.bib`` file that is not already in the loaded bibliography."
        **Shortcut**,"``-mb``"
        **Usage**,"``-mergeBibliographies [+type <types>] [+key <keys>] [+value <path>]``"
          ``[+type <types>]``,"Only entries of this type will be added. If not specified every type will be added"
          ``[+key <keys>]``,"Only the keys specified here will be added. If not specified every key will be added"
          ``[+value <path>]``,"The exact path to the ``.bib`` file you want to load (no spaces allowed)"

.. raw:: html

    <span id="command--orderKeys"></span>

.. topic:: ``-orderKeys``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Orders every key in a bib file by a given list."
        **Shortcut**,"``-ok``"
        **Usage**,"``-orderKeys [+type <types>] [+key <keys>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"The order in which the keys will be ordered. If no keys are specified the standard order listed from the config file is used (``entryOrder``)."

.. raw:: html

    <span id="command--orderTypes"></span>

.. topic:: ``-orderTypes``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Orders every key in a bib file by a given list."
        **Shortcut**,"``-ot``"
        **Usage**,"``-orderTypes [+type <types>]``"
          ``[+type <types>]``,"The order in which the entries will be ordered. If no types are specified the standard order listed from the config file is used (``typeOrder``)."

.. raw:: html

    <span id="command--removeEntry"></span>

.. topic:: ``-removeEntry``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Removes specified entries/keys from a bibliography."
        **Shortcut**,"``-re``"
        **Usage**,"``-removeEntry [+type <types>] [+key <keys>] [+match <match>] [+value {y|n}]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Only the keys specified here will be affected. If not specified every key will be affected"
          ``[+match <match>]``,"Only the values that match this pattern will be affected (for regex please use a file here). If no match is specified every value will be affected."
          ``[+value {yes|no}]``,"Set to yes/y if you want to invert the whole statement. If not specified invert is set to no/n."

.. raw:: html

    <span id="command--replaceKey"></span>

.. topic:: ``-replaceKey``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Replace a key with a new key."
        **Shortcut**,"``-rk``"
        **Usage**,"``-replaceKey [+type <types>] [+key <keys>] [+match <match>] [+value {y|n}]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Only the keys specified here will be affected. If not specified every key will be affected"
          ``[+match <match>]``,"The value that has to match with the value of the key you want to replace. If no match is specified every value will be affected."
          ``[+value {yes|no}]``,"Set to yes/y if you want to override any already existing key. If not specified overriding is set to no/n."

.. raw:: html

    <span id="command--saveCapitals"></span>

.. topic:: ``-saveCapitals``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Enclose every uppercase letter. (´´This i A test´´ -> ´´{T}his is {A} test´´)."
        **Shortcut**,"``-sc``"
        **Usage**,"``-saveCapitals [+type <types>] [+key <keys>] [+match <characters>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Only the keys specified here will be affected. If not specified every key will be affected"
          ``[+match <characters>]``,"Only the characters specified here will be enclosed. If not specified every character will be enclosed"

.. raw:: html

    <span id="command--saveSymbols"></span>

.. topic:: ``-saveSymbols``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Replaces every special character with the correct latex code."
        **Shortcut**,"``-ss``"
        **Usage**,"``-saveSymbols [+type <types>] [+key <keys>] [+value <characterMap>]``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Only the keys specified here will be affected. If not specified every key will be affected"
          ``[+value <characterMap>]``,"The character map file name (``.Data/<characterMap>.txt``). If no map is specified the standard one is loaded (``unicode2latex``)"

.. raw:: html

    <span id="command--setValue"></span>

.. topic:: ``-setValue``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Set the specified value of a specified key in a specified typ to new value."
        **Shortcut**,"``-sv``"
        **Usage**,"``-setValue [+type <types>] [+key <keys>] [+match <match>] <+value <value>>``"
          ``[+type <types>]``,"Only entries of this type will be affected. If not specified every type will be affected"
          ``[+key <keys>]``,"Only the keys specified here will be affected. If not specified every key will be affected"
          ``[+match <match>]``,"Only values matching this value will be affected (to escape special characters when using regex matching, use a file (e.g. ``+match C:/regexcode.txt``)). If no matching string is specified the changes will effect every value."
          ``<+value <value>>``,"The character map file name (``.Data/<characterMap>.txt``). If no map is specified the standard one is loaded (unicode2latex)"

Utility Commands
~~~~~~~~~~~~~~~~
.. raw:: html

    <span id="command--help"></span>

.. topic:: ``-help``
    Not yet implemented!
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Displays help for BibFormatter commands"
        **Shortcut**,"``-h``"
        **Usage**,"``-help [+match <command>] [+value <value>]``"
          ``[+match <command>]``,"The command to retrieve help for. If no command is specified a list of all commands will be displayed"
          ``[+value <value>]``,"The page to retrieve"
