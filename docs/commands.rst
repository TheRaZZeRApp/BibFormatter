========
Commands
========

.. contents::
    :local:

.. note::

    Arguments enclosed in ``[ ]`` are optional, those enclosed in ``< >`` are required.

.. tip::

    You can access a command listing via the ``-help`` command.

General Commands
~~~~~~~~~~~~~~~~
.. raw:: html

    <span id="command--bibliography"></span>
    Everytime this command gets issued the currently loaded bibliography file will be saved (with all applied changes) and the new one will be loaded. Every command after this one will only effect the bibliography file loaded by this command (until -b is found again etc.).

.. topic:: ``-bibliography``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Load a new .bib file. If no output path is specified the modified bib gets saved in the same folder where the original .bib file is located."
        **Shortcut**,"``-b``"
        **Usage**,"``-bibliography <file> [output]``"
          ``<file>``,"Exact path to the bibliography file you want to load"
          ``[output]``,"The exact save path"

.. raw:: html

    <span id="command--debug"></span>

.. topic:: ``-debug``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Debugs the modification process. If not other specified debuging is set to no."
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
          ``[+type <types>]``,"Only entries of this type get added (e.g. article book) If not other specified every type will be added"
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
          ``[+type <types>]``,"Only entries of this type will be checked (e.g. article book). If not other specified every type will be checked"
          ``[+match <required>]``,"Specify a .json file which represents a map of required keys for every type. If not specified the default map will be loaded (.\Data\CheckFiles\valRequiredFields.json)"
          ``[+value <style>]``,"Specify the output style of the missing keys. (txt/json/html)"


Utility Commands
~~~~~~~~~~~~~~~~
.. raw:: html

    <span id="command--help"></span>

.. topic:: ``-help``
    :class: command-topic

    .. csv-table::
        :widths: 8, 15

        **Description**,"Displays help for BibFormatter commands"
        **Shortcut**,"``-h``"
        **Usage**,"``-help [-s] [-p <page>] [command...]``"
          ``[-s]``,"List sub-commands of the given command, if applicable"
          ``[-p <page>]``,"The page to retrieve"
          ``[command...]``,"The command to retrieve help for"
