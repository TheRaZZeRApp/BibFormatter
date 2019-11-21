package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliographie;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.7.7
 */
public class CReplaceKey {
    public static final String COMMANDPATTERN = "(-replaceKey|-rk) (?<par>[^-]{0,})";
    public static final String ARGUMENTPATTERN = "";

    public static void run(Bibliographie bibliographie, String parameter){
        if (Utils.isCommandCorrect(ARGUMENTPATTERN,parameter,COMMANDPATTERN)){
            String[] commandLines = Utils.getCommand(parameter).split(" ");
            int currentPosition = 0;

            Set<TypeType> currentTypes = new HashSet<>();           //0
            LinkedList<KeyType> currentKeys = new LinkedList<>();   //1
            StringBuilder currentMatch = new StringBuilder();       //2
            StringBuilder currentValue = new StringBuilder();       //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines,currentPosition,i,currentTypes,currentKeys,currentMatch,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    bibliographie.replaceKey(currentTypes,currentKeys,currentMatch.toString(),currentValue.toString().matches("(yes|y) *"));
                }
            }
        }
    }
}
