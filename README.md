# BibFormatter
> BibFormatter is a program to clean up bib files.

## Features

<li>Save Capitalize Symbols</li>
<li>Order Entries</li>
<li>Format Month</li>
<li>Format Pages</li>
<li>Remove Entries</li>
<li>Save Special Characters</li>
<li>Multi special character map support</li>

## Planned Features

<li>Character Map Config File</li>
<li>Complete Debug</li>
<li>Info/Error Message System</li>
<li>Replace Value</li>
<li>Replace Key</li>
<li>Format URLs</li>
<li>Check for required fields</li>
<li>Generate check file for required fields (i.e. bibtex_check_0.2.0)</li>
<li>Create bib from aux file</li>
<li>Create bib from bcf file</li>
<li>Bib comment section</li>
<li>Generate HTML info file</li>
<li>Format Date</li>
<li>Format Authors</li>
<li>Ordinals to LaTeX superscript</li>
<li>Format DOI</li>
<li>RIS to BibTeX</li>
<li>HTML to BibTeX</li>

## Requirements

You need Java to run this program.

## Installation

OS X & Linux:

```sh
BibFormatter.jar
```

Windows:

```sh
run BibFormatter.jar
edit run.bat
```

## Usage example

```sh
-jar BibFormatter.jar <bibFile> {true|false} [-command [+options] <parameter>] ...
```

Required Parameters:
```sh
<bibFile>
Specifie a .bib file path. i.e. "C:\Users\Computer\Documents\test.bib"
```
```sh
{true|false}
Set to true or false if you want to debug the output
```

Optional Parameters:
```sh
--capitalizeValue [<file>|{key1} <key2> <keyn>] ...
-cv
You can specifiy a file which contains a list of fields or list every field as parameter.
Parameter represent fields i.e. title author book ...
Encloses every uppercase letter in the specified fields (This i A test -> {T}his is {A} test). 
```
```sh
--orderEntries [<file>|{key1} <key2> <keyn>] ...
-oe
If no parameter is specified the standart order listed in the config file is used.
You can specifiy a file which contains a list of fields or list every field as parameter.
Orders every entry in a bib file by a given list of fields.
```
```sh
--formatMonth [number|name]
-fm
If no parameter is specified the standart format is by name.
You can specifiy the format style i.e. 07 or jul
Formats every month value into the described format.
```
```sh
--formatPages [single|double]
-fp
If no parameter is specified the standart format is double.
You can specifiy the format style i.e. - or -- between page numbers
Formats every page value into the described format.
```
```sh
--removeEntry [<file>|{key1} <key2> <keyn>] ...
-re
You can specifiy a file which contains a list of fields or list every field as parameter.
Removes every entry specified
```
```sh
--saveSpecialCharacters {[+characterMap <characterMap>] <file>|{key1} <key2> <keyn>} ...
-ss
You can specifiy a file which contains a list of fields or list every field as parameter.
If no special character map is specified the standart one is picked (see config).
Character map needs to be in the Data folder saved as txt (UTF-8)
Replaces every special character with the correct latex code.
```

## Release History

* 0.6.4
    * Add: Multi special character map support
    * Fix: Error if no bib file found
* 0.4.3
    * Add: Required Fields generator
* 0.4.2
    * Add: Option to save special characters

## Meta

Copyright 2019 © Paul Eduard Koenig
