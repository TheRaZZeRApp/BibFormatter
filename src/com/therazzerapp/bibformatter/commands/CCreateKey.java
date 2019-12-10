package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CCreateKey extends Command {

    public CCreateKey(String ARGUMENTS) {
        super("CreateKey", Constants.COMMANDPATTER_CREATEKEY, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        bibliography.createKey(types,keys,match,isYes());
    }
}
