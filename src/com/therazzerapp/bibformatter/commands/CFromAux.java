package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.loader.AuxLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;

import java.io.File;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.12.9
 */
public class CFromAux {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-fromAux|-fa) (?<arg>[^-]{0,})";

    public static void run(Bibliography bibliography, String arguments){
        if (Utils.isArgumentsValid(ARGUMENTPATTERN,arguments)){
            String[] commandLines = Utils.getCommand(arguments).split(" ");
            int currentPosition = -1;

            StringBuilder currentValue = new StringBuilder(); //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,null ,null,null,currentValue,false);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    bibliography.removeEntries(AuxLoader.getCitations(new File(currentValue.toString())));
                    BibSaver.save(bibliography,bibliography.getSaveLocation());
                }
            }
        }
    }
}
