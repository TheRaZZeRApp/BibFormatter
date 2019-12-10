package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.content.loader.BibLoader;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CAddEntry extends Command{

    public CAddEntry(String arguments) {
        super("AddEntry", "", Constants.COMMANDPATTER_ADDENTRY, "", arguments);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        Set<Bibliography> bibs = new HashSet<>();
        for (String s : value.split(" ")) {
            bibs.add(BibLoader.load(new File(s),""));
        }
        if (types.isEmpty()){
            for (Bibliography bib : bibs) {
                bibliography.addEntries(bib.getEntryList());
            }
        } else {
            for (Bibliography bib : bibs) {
                for (Entry entry : bib.getEntryList()) {
                    if (types.contains(entry.getTypeType())){
                        bibliography.addEntry(entry);
                    }
                }
            }
        }
    }
}
