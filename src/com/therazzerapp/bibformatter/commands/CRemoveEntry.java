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
 * @since <version>
 */
public class CRemoveEntry {
    public static final String PATTERNGROUP1 = "par1";
    public static final String COMMANDPATTERN = "(-removeEntry|-re) (?<" + PATTERNGROUP1 + ">[^-]{0,})";
    public static final String ARGUMENTPATTERN = "";

    public static void run(Bibliographie bibliographie, String parameter){
        if (Utils.isCommandCorrect(ARGUMENTPATTERN,parameter,CSetValue.COMMANDPATTERN)){
            String[] commandLines = Utils.getCommand(parameter).split(" ");
            int currentPosition = 0;

            Set<TypeType> currentTypes = new HashSet<>();           //0
            Set<KeyType> currentKeys = new HashSet<>();             //1
            StringBuilder currentMatch = new StringBuilder();       //2
            StringBuilder currentValue = new StringBuilder();       //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,currentKeys,currentMatch,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    bibliographie.removeEntrie(currentTypes,currentKeys,currentMatch.toString(),currentValue.toString().matches("(yes|y) *"));
                }
            }
        }
    }
}
