package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <rezzer101@googlemail.com>
 * @since 0.16.12
 */
public class CGeneratePublisher {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-generatePublisher|-gp) (?<arg>[^-]{0,})";

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
            StringBuilder currentValue = new StringBuilder();       //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,currentKeys,currentMatch,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    Set<Integer> doiPrefix = new HashSet<>();
                    for (String s : currentMatch.toString().split(" ")) {
                        doiPrefix.add(Integer.parseInt(s));
                    }
                    BibTools.generatePublisher(bibliography,currentTypes,currentKeys,doiPrefix,currentValue.toString().matches(Constants.REGEX_YES));
                }
            }
        }
    }
}
