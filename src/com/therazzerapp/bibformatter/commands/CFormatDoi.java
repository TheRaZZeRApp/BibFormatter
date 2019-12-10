package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

/**
 * Formats DOIs inside a {@link Bibliography} by a given string of arguments.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CFormatDoi extends Command{

    public CFormatDoi(String ARGUMENTS) {
        super("FormatDoi", Constants.COMMANDPATTER_FORMATDOI, ARGUMENTS);
    }

    /**
     * Formats DOIs by a given string of arguments.
     * @param bibliography the bibliography to format
     */
    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        BibTools.formatDoi(bibliography,types,keys,value.isEmpty() ? ConfigManager.getAsString(ConfigType.DOISTYLE) : value);
    }
}
