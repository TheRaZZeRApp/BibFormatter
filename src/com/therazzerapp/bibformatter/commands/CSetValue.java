package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public final class CSetValue extends Command{

    public CSetValue(String arguments) {
        super("SetValue", Constants.ARGUMENTPATTERN_SETVALUE, Constants.COMMANDPATTER_SETVALUE, Constants.USAGE_SETVALUE, arguments);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        bibliography.replaceValue(types,keys,match,value);
    }
}
