package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.content.loader.BibLoader;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.12.9
 */
public class CAddEntry {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-addEntry|-ae) (?<arg>[^-]{0,})";

    /**
     *
     * @param bibliography
     * @param arguments
     */
    public static void run(Bibliography bibliography, String arguments){
        if (Utils.isArgumentsValid(ARGUMENTPATTERN,arguments)){
            String[] commandLines = Utils.getCommand(arguments).split(" ");
            int currentPosition = -1;

            Set<TypeType> currentTypes = new HashSet<>();           //0
            StringBuilder currentValue = new StringBuilder(); //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes ,null,null,currentValue,false);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    Set<Bibliography> bibs = new HashSet<>();
                    for (String s : currentValue.toString().split(" ")) {
                        bibs.add(BibLoader.load(new File(s),""));
                    }

                    if (currentTypes.isEmpty()){
                        for (Bibliography bib : bibs) {
                            bibliography.addEntries(bib.getEntrieList());
                        }
                    } else {
                        LinkedList<Entry> entries = new LinkedList<>();
                        for (Bibliography bib : bibs) {
                            for (Entry entry : bib.getEntrieList()) {
                                if (currentTypes.contains(TypeType.valueOf(entry.getType().toUpperCase()))){
                                    entries.add(entry);
                                }
                            }
                        }
                        bibliography.addEntries(entries);
                    }
                }
            }
        }
    }
}
