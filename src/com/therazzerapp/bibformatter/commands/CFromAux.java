package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.loader.AuxLoader;
import com.therazzerapp.bibformatter.manager.LogManager;

import java.io.File;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CFromAux extends Command{

    public CFromAux(String arguments) {
        super("FromAux", "[^+ ]*\\.aux", Constants.COMMANDPATTER_FROMAUX, "-fromAux <auxPath>", arguments);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        bibliography.removeEntries(AuxLoader.getCitations(new File(value)));
    }
}
