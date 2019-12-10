package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CFormatURL extends  Command{

    public CFormatURL(String ARGUMENTS) {
        super("FormatURL", Constants.COMMANDPATTER_FORMATURL, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        BibTools.formatURL(bibliography,types,keys);
    }
}
