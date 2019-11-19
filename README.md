# BibFormatter
> BibFormatter is a program to clean up bib files.

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
-jar BibFormatter.jar <bibFile> {true|false} [-command <parameter>] ...
```

Required Parameters:
<li>bibFile: Specifie a .bib file path. i.e. "C:\Users\Computer\Documents\test.bib"</li>
<li>debug: Set to true or false if you want to debug the output</li>
<br>

Optional Parameters:
```sh
-capitalizeValue {key1} <key2> <keyn> ...
Parameter represent fields i.e. title author book ...
Encloses every uppercase letter in the specified fields (This i A test -> {T}his is {A} test). 
```
```sh
-orderEntries [<file>|{key1} <key2> <keyn>] ...
If no parameter is specified the standart order listed in the config file is use.
You can specifiy a file which contains a list of fields or list every field as parameter.
Orders every entry in a bib file by a given list of fields.
```

## Release History

* 0.4.2
    * Add: Option to save special characters

## Meta

Copyright 2019 © Paul Eduard Koenig
