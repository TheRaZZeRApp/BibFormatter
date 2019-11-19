# BibFormatter
> BibFormatter is a program to clean up bib files.

## Features

<li>Save Capitalize Symbols</li>
<li>Order Entries</li>
<li>Format Month</li>
<li>Format Pages</li>
<li>Remove Entries</li>
<li>Save Special Characters</li>

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
<li>bibFile: Specifie a .bib file path. i.e. "C:\Users\Computer\Documents\test.bib"</li>
<li>debug: Set to true or false if you want to debug the output</li>
<br>

Optional Parameters:
```sh
-capitalizeValue [<file>|{key1} <key2> <keyn>] ...
You can specifiy a file which contains a list of fields or list every field as parameter.
Parameter represent fields i.e. title author book ...
Encloses every uppercase letter in the specified fields (This i A test -> {T}his is {A} test). 
```
```sh
-orderEntries [<file>|{key1} <key2> <keyn>] ...
If no parameter is specified the standart order listed in the config file is use.
You can specifiy a file which contains a list of fields or list every field as parameter.
Orders every entry in a bib file by a given list of fields.
```
```sh
-formatMonth [number|name]
If no parameter is specified the standart format is by name.
You can specifiy the format style i.e. 07 or jul
Formats every month value into the described format.
```
```sh
-formatPages [single|double]
If no parameter is specified the standart format is double.
You can specifiy the format style i.e. - or -- between page numbers
Formats every page value into the described format.
```
```sh
-removeEntry [<file>|{key1} <key2> <keyn>] ...
You can specifiy a file which contains a list of fields or list every field as parameter.
Removes every entry specified
```
```sh
-saveSpecialCharacters {[+characterMap] <file>|{key1} <key2> <keyn>} ...
You can specifiy a file which contains a list of fields or list every field as parameter.
If no special character map is specified the standart one is picked (see config).
Replaces every special character with the correct latex code.
```

## Release History

* 0.4.3
    * Add: Required Fields generator
* 0.4.2
    * Add: Option to save special characters

## Meta

Copyright 2019 © Paul Eduard Koenig
