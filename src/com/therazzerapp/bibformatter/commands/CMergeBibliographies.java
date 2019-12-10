package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
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
public class CMergeBibliographies extends Command{

    public CMergeBibliographies(String ARGUMENTS) {
        super("MergeBibliographies", Constants.COMMANDPATTER_MERGEBIBLIOGRAPHIES, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        bibliography.setEntryList(BibTools.mergeBibliographies(bibliography, BibLoader.load(new File(value), ""),types,keys).getEntryList());
    }
}
