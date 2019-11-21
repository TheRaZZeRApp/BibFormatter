package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class CReplaceKey {
    public static final String COMMANDPATTERN = "(-replaceKey|-rk) (?<par>[^-]{0,})";
    public static final String ARGUMENTPATTERN = "";
    public static void run(Bibliographie bibliographie, String parameter){
        if (Utils.isCommandCorrect(ARGUMENTPATTERN,parameter,COMMANDPATTERN)){
            String[] commandLines = Utils.getCommand(parameter).split(" ");

            Set<TypeType> currentTypes = new HashSet<>();           //0
            Set<Pair<KeyType,KeyType>> currentKeys = new HashSet<>();             //1
            StringBuilder currentMatch = new StringBuilder();       //2

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
                    default:
                        switch (currentPosition){
                            case 0:
                                Utils.getCommandTypes(commandLines, currentTypes, i);
                                break;
                            case 1:
                                //todo Pair richtig erstellen und in currentKeys hinzugügen.
                                break;
                            case 2:
                                Utils.getCommandArguments(currentMatch, commandLines, i);
                                break;
                        }
                }
                if (i == commandLines.length-1 || (currentPosition == 2 && commandLines[i+1].startsWith("+"))){
                    bibliographie.replaceKey(currentTypes,currentKeys,currentMatch.toString());
                }
            }
        }
    }

}
