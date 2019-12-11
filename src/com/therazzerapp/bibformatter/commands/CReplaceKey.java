package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

import java.util.LinkedList;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public class CReplaceKey extends Command{


    public CReplaceKey(String ARGUMENTS) {
        super("ReplaceKey", Constants.COMMANDPATTER_REPLACEKEY, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        LinkedList<KeyType> currentKeys = new LinkedList<>();
        getCommandKeys(currentKeys,getArguments(Constants.REGEX_DEF_ARG2),true);
        bibliography.replaceKey(types,currentKeys,match,isYes());

    }
}
