package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;
import com.therazzerapp.bibformatter.manager.LogManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Formats DOIs inside a {@link Bibliography} by a given string of arguments.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.17.12
 */
public class CFormatDoi {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-formatDOI|-fd) (?<arg>[^-]{0,})";
    public static final String USAGE = "-formatDOI [+type <types>] [+key <keys>] +value <style>";

    /**
     * Formats DOIs by a given string of arguments.
     * @param bibliography the bibliography to format
     * @param arguments the command arguments
     */
    public static void run(Bibliography bibliography, String arguments){
        if (Utils.isArgumentsValid(ARGUMENTPATTERN,arguments)){
            String[] commandLines = Utils.getCommand(arguments).split(" ");
            int currentPosition = -1;

            Set<TypeType> currentTypes = new HashSet<>();           //0
            Set<KeyType> currentKeys = new HashSet<>();             //1
            StringBuilder currentValue = new StringBuilder();       //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,currentKeys,null,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    String t = currentValue.toString();
                    if (t.trim().equals("")){
                        t = (String) ConfigManager.getConfigProperty(ConfigType.DOISTYLE);
                    }
                    BibTools.formatDoi(bibliography,currentTypes,currentKeys,t);
                }
            }
        } else {
            LogManager.writeError(Constants.ERROR_INVALID_ARGUMENTS + "-formatDOI\n" + Constants.USSAGE + USAGE);
            System.exit(1);
        }
    }
}
