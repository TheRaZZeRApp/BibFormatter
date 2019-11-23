# BibFormatter
> BibFormatter is a program to clean up bib files.

## Features

<li>Save capitals</li>
<li>Order keys</li>
<li>Format month</li>
<li>Format pages</li>
<li>Remove keys</li>
<li>Save special characters</li>
<li>Multi special character map support</li>
<li>Replace key</li>
<li>Replace value/Set value</li>
<li>Check for required keys</li>
<li>Create Key+Value</li>
<li>Create bib from aux file</li>

## Planned Features

<li>Complete Debug</li>
<li>Info/Error Message System</li>
<li>Format URLs</li>
<li>Generate check file for required fields (i.e. bibtex_check_0.2.0)</li>
<li>Create bib from bcf file</li>
<li>Bib comment section</li>
<li>Generate HTML info file</li>
<li>Format Date</li>
<li>Format Authors</li>
<li>Ordinals to LaTeX superscript</li>
<li>Format DOI</li>
<li>RIS to BibTeX</li>
<li>HTML to BibTeX</li>
<li>Format into a canonical form</li>


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
--saveCapitals
-sv
You can specifiy a file which contains a list of fields or list every field as parameter.
Parameter represent fields i.e. title author book ...
Encloses every uppercase letter in the specified fields (This i A test -> {T}his is {A} test). 
```
```sh
--orderEntries
-oe
If no parameter is specified the standart order listed in the config file is used.
You can specifiy a file which contains a list of fields or list every field as parameter.
Orders every entry in a bib file by a given list of fields.
```
```sh
--formatMonth
-fm
If no parameter is specified the standart format is by name.
You can specifiy the format style i.e. 07 or jul
Formats every month value into the described format.
```
```sh
--formatPages
-fp
If no parameter is specified the standart format is double.
You can specifiy the format style i.e. - or -- between page numbers
Formats every page value into the described format.
```
```sh
--removeEntry
-re
You can specifiy a file which contains a list of fields or list every field as parameter.
Removes every entry specified
```
```sh
--saveSymbols
-ss
You can specifiy a file which contains a list of fields or list every field as parameter.
If no special character map is specified the standart one is picked (see config).
Character map needs to be in the Data folder saved as txt (UTF-8)
Replaces every special character with the correct latex code.
```
```sh
--replaceKey 
-rk
You can specifiy a file which contains a list of commands or add them directly.
If no type (i.e. book) is specified the changes will affect every type (until a type has specified). 
You have to specifie a key (i.e. location) and a replacement key (i.e. adress).
You can list as many replacement pairs as you want.
Replaces a key with a new key.
Example:
-rk location adress +type book subtitle title +type article issn isbn 
First it will replace every location key in any type with an adress key and than
replaces every subtitle key inside a book type with a title key.
It also replaces every issn key in an article with an isbn key. 
```
```sh
--setValue 
-sv
You can specifiy a file which contains a list of commands or add them directly.
If no type (i.e. book) is specified the changes will affect every type (until a type has specified).
If no key (i.e. author) is specified the changes will affect every key (until a type has specified).  
You can list unlimited types/keys. 
If no matching string is specified the changes will effect every value.
Info: if you want to escape special characters when using regex matching, use a file (i.e. +match C:/regexcode.txt)
match and value arguements can contain spaces.
Set the specified value of a specified key in a specified typ to new value. 
Example:
-sv +type book article +key author +match Noam Chomsky +value Chomsky, N.

```

## Release History

* 0.12.9
    * Add: Option to generate bib file from aux file
    * Add: Option to add every entry from a bib file to another bib file if the BibTexKey is not already found.
* 0.11.9
    * Add: Option to create new keys and add a value
* 0.10.9
    * Add: Option to specify a save location
    * Add: Option to run multiple commands on multiple bib files
    * Fix: Various little things
* 0.9.8
    * Add: Command to export flawed entries as txt
* 0.8.8
    * Add: More powerful command system
* 0.7.7
    * Edit: Rewriting Command System
* 0.7.6
    * Add: Replace value option
* 0.7.5
    * Add: Replace key option
* 0.6.5
    * Add: Moved special character regEx to file
    * Fix: Special character map mismatch
* 0.6.4
    * Add: Multi special character map support
    * Fix: Error if no bib file found
* 0.4.3
    * Add: Required Fields generator
* 0.4.2
    * Add: Option to save special characters

## Meta

Copyright 2019 © Paul Eduard Koenig
