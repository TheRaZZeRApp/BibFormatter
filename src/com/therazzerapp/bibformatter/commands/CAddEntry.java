package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.loader.BibLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.12.9
 */
public class CAddEntry {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-addEntry|-ae) (?<arg>[^-]{0,})";

    public static void run(Bibliography bibliography, String arguments){
        if (Utils.isArgumentsValid(ARGUMENTPATTERN,arguments)){
            String[] commandLines = Utils.getCommand(arguments).split(" ");
            int currentPosition = -1;

            StringBuilder currentValue = new StringBuilder(); //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,null ,null,null,currentValue,false);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    Set<Bibliography> bibs = new HashSet<>();
                    for (String s : currentValue.toString().split(" ")) {
                        bibs.add(BibLoader.load(new File(s),""));
                    }
                    for (Bibliography bib : bibs) {
                        bibliography.addEntries(bib.getEntrieList());
                    }
                    BibSaver.save(bibliography,bibliography.getSaveLocation());
                }
            }
        }
    }
}
