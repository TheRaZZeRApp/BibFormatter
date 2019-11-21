package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliographie;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.7.7
 */
public final class CSetValue {
    public static final String COMMANDPATTERN = "(-setValue|-sv) (?<par>[^-]{0,})";
    public static final String ARGUMENTPATTERN = "";

    public static void run(Bibliographie bibliographie, String parameter){
        if (Utils.isCommandCorrect(ARGUMENTPATTERN,parameter,CSetValue.COMMANDPATTERN)){
            String[] commandLines = Utils.getCommand(parameter).split(" ");
            int currentPosition = 0;

            Set<TypeType> currentTypes = new HashSet<>();           //0
            Set<KeyType> currentKeys = new HashSet<>();             //1
            StringBuilder currentMatch = new StringBuilder();       //2
            StringBuilder currentReplacement = new StringBuilder(); //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,currentKeys,currentMatch,currentReplacement);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    bibliographie.replaceValue(currentTypes,currentKeys,currentMatch.toString(),currentReplacement.toString());
                }
            }
        }
    }
}
