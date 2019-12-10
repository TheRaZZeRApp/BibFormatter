package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CFormatMonth extends Command {

    public CFormatMonth(String ARGUMENTS) {
        super("FormatMonth", Constants.COMMANDPATTER_FORMATMONTH, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        BibTools.formatMonth(bibliography,types,value.isEmpty() ? ConfigManager.getAsString(ConfigType.DEFAULTMONTHFORMAT) : value);
    }
}
