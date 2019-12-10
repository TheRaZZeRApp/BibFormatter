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
public class CFormatPages extends Command{

    public CFormatPages(String ARGUMENTS) {
        super("FormatPages", Constants.COMMANDPATTER_FORMATPAGES, ARGUMENTS);
    }


    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        BibTools.formatPages(bibliography,types,value.isEmpty() ? ConfigManager.getAsString(ConfigType.DEFAULTPAGESFORMAT) : value);
    }
}
