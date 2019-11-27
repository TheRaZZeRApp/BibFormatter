package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.7.7
 */
public class CReplaceKey {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-replaceKey|-rk) (?<arg>[^-]{0,})";

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
            LinkedList<KeyType> currentKeys = new LinkedList<>();   //1
            StringBuilder currentMatch = new StringBuilder();       //2
            StringBuilder currentValue = new StringBuilder();       //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines,currentPosition,i,currentTypes,currentKeys,currentMatch,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    bibliography.replaceKey(currentTypes,currentKeys,currentMatch.toString().trim(),currentValue.toString().trim().matches(Constants.REGEX_YES));
                }
            }
        }
    }
}
