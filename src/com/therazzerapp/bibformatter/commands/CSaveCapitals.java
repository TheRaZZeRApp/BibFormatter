package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public class CSaveCapitals extends Command{

    public CSaveCapitals(String ARGUMENTS) {
        super("SaveCapitals", Constants.COMMANDPATTER_SAVECAPITALS, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        BibTools.capitalizeValue(bibliography,types,keys,match);
    }
}
