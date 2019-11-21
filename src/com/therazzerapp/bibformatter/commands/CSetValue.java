package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.manager.FileManager;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public final class CSetValue {
    public static final String COMMANDPATTERN = "(-setValue|-sv) (?<par>[^-]{0,})";
    public static final String ARGUMENTPATTERN = "";
    public static void run(Bibliographie bibliographie, String parameter){
        if (Utils.isCommandCorrect(ARGUMENTPATTERN,parameter,CSetValue.COMMANDPATTERN)){
            String[] commandLines = Utils.getCommand(parameter).split(" ");

            Set<TypeType> currentTypes = new HashSet<>();           //0
            Set<KeyType> currentKeys = new HashSet<>();             //1
            StringBuilder currentMatch = new StringBuilder();       //2
            StringBuilder currentReplacement = new StringBuilder(); //3

            int currentPosition = 0;
            for (int i = 0; i < commandLines.length; i++) {
                switch (commandLines[i]){
                    case "+type":
                        currentPosition = 0;
                        currentTypes.clear();
                        break;
                    case "+key":
                        currentPosition = 1;
                        currentKeys.clear();
                        break;
                    case "+match":
                        currentPosition = 2;
                        currentMatch = new StringBuilder();
                        break;
                    case "+value":
                        currentPosition = 3;
                        currentReplacement = new StringBuilder();
                        break;
                    default:
                        switch (currentPosition){
                            case 0:
                                Utils.getCommandTypes(commandLines, currentTypes, i);
                                break;
                            case 1:
                                if(new File(commandLines[i]).exists()){
                                    for (String s : FileManager.getFileContent(new File(commandLines[1]))) {
                                        for (String s1 : s.split(" ")) {
                                            currentKeys.add(KeyType.valueOf(s1.toUpperCase()));
                                        }
                                    }
                                } else {
                                    currentKeys.add(KeyType.valueOf(commandLines[i].toUpperCase()));
                                }
                                break;
                            case 2:
                                Utils.getCommandArguments(currentMatch, commandLines, i);
                                break;
                            case 3:
                                Utils.getCommandArguments(currentReplacement, commandLines, i);
                                break;
                        }
                }
                if (i == commandLines.length-1 || (currentPosition == 3 && commandLines[i+1].startsWith("+"))){
                    bibliographie.replaceValue(currentTypes,currentKeys,currentMatch.toString(),currentReplacement.toString());
                }
            }
        }
    }
}
