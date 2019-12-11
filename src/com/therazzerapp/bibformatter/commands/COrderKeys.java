package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public class COrderKeys extends Command{

    public COrderKeys(String ARGUMENTS) {
        super("OrderKeys", Constants.COMMANDPATTER_ORDERKEYS, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        ArrayList<KeyType> keyOrder = new ArrayList<>();
        getCommandKeys(keyOrder,getArguments(Constants.REGEX_DEF_ARG2),true);
        getCommandTypes(types,getArguments(Constants.REGEX_DEF_ARG1),true);
        BibTools.orderEntries(bibliography,types,keyOrder);
    }
}
