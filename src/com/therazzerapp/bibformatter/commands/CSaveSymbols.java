package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public class CSaveSymbols extends Command{

    public CSaveSymbols(String ARGUMENTS) {
        super("SaveSymbols", Constants.COMMANDPATTER_SAVESYMBOLS, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        BibTools.saveSpecialCharacters(bibliography,types,keys,value.isEmpty() ? ConfigManager.getAsString(ConfigType.DEFAULTCHARACTERMAP) : value);
    }
}
