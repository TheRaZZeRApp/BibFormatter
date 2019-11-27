package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.manager.LogManager;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.7.7
 */
public final class CSetValue {
    public static final String ARGUMENTPATTERN = " *((\\+[tT]|\\+type) [^+]* +|\\+[tT] ){0,1}((\\+[kK]|\\+key) [^+]* +|\\+[kK] ){0,1}((\\+[mM]|\\+match) [^+]* +|\\+[mM] ){0,1}((\\+[vV]|\\+value) [^+]* *|\\+[vV] ){1,1}";
    public static final String COMMANDPATTERN = "(-setValue|-sv) (?<arg>[^-]{0,})";
    public static final String USAGE = "-setValue [+type <types>] [+key <keys>] [+match <match>] +value <value>";

    /**
     *
     * @param bibliography
     * @param arguments
     */
    public static void run(Bibliography bibliography, String arguments){
        if (Utils.isArgumentsValid(ARGUMENTPATTERN,arguments)){
            String[] commandLines = Utils.getCommand(arguments).split(" ");
            int currentPosition = -1;

            Set<TypeType> currentTypes = new HashSet<>();           //0
            Set<KeyType> currentKeys = new HashSet<>();             //1
            StringBuilder currentMatch = new StringBuilder();       //2
            StringBuilder currentValue = new StringBuilder(); //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,currentKeys,currentMatch,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    bibliography.replaceValue(currentTypes,currentKeys,currentMatch.toString().trim(),currentValue.toString().trim());
                }
            }
        } else {
            LogManager.writeError(Constants.ERROR_INVALID_ARGUMENTS + "-setValue\n" + Constants.USSAGE + USAGE);
            System.exit(1);
        }
    }
}
