package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

import java.util.ArrayList;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public class COrderTypes extends Command{

    public COrderTypes(String ARGUMENTS) {
        super("OrderTypes", Constants.COMMANDPATTER_ORDERTYPES, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        ArrayList<TypeType> typeOrder = new ArrayList<>();
        getCommandTypes(typeOrder,getArguments(Constants.REGEX_DEF_ARG1),true);
        BibTools.orderTypes(bibliography,typeOrder);
    }
}
