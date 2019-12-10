package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CRemoveEntry extends Command {

    public CRemoveEntry(String arguments) {
        super("RemoveEntry", "", Constants.COMMANDPATTER_REMOVEENTRY, "", arguments);
    }


    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        bibliography.removeKey(types,keys,match,isYes());
    }
}
