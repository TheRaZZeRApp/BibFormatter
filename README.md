# BibFormatter
> BibFormatter is a program to clean up bib files.
>
>Manual & Commands 
>https://bibformatter.readthedocs.io/en/latest/index.html

## Features

<li>Save capitals</li>
<li>Order keys</li>
<li>Order types</li>
<li>Format month</li>
<li>Format pages</li>
<li>Format DOI</li>
<li>Remove keys</li>
<li>Format URLs</li>
<li>Save special characters</li>
<li>Multi special character map support</li>
<li>Replace key</li>
<li>Replace value/Set value</li>
<li>Check for required keys</li>
<li>Create Key+Value</li>
<li>Create bib from aux file</li>
<li>Merge Bib files</li>
<li>Generate publisher key from DOI</li>

## Planned Features

<li>Bib comment section</li>
<li>Load pre sets</li>
<li>Format Authors</li>
<li>Generate check file for required fields (i.e. bibtex_check_0.2.0)</li>
<li>Complete Debug</li>
<li>Info/Error Message System</li>
<li>Create bib from bcf file</li>
<li>Generate HTML info file</li>
<li>Ordinals to LaTeX superscript</li>
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
-jar BibFormatter.jar -b <bibFile> [<outputPath>] [-command [[+parameter] <arguments>] ...
```

A complete list of all commands can be found here: https://bibformatter.readthedocs.io/en/latest/commands.html

## Release History

* 0.19.12
    * Add: New Command System
    * Add: Ability to invert Types and Keys
    * Add: Override option on create Key
    * Fix: Various little things
    * Fix: JavaDoc
* 0.18.12
    * Add: Command to generate a publisher
* 0.17.12
    * Add: Command to Format DOIs
    * Add: Option to add every Type/Key in an argument (#)
    * Fix: JavaDoc
* 0.16.12
    * Add: Generate Doi publisher map
    * Fix: Various little things
    * Fix: JavaDoc
* 0.15.10
    * Add: Command to format urls
    * Fix: Added missing keys
* 0.14.9
    * Add: Command to order entries by a list of types
* 0.13.9
    * Add: Command to merge two bibliography files
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
