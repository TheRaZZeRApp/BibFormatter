package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.8.8
 */
public class CSaveSymbols {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-saveSymbols|-ss) (?<arg>[^-]{0,})";

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
            StringBuilder currentValue = new StringBuilder();       //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,currentKeys,null,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    String characterMap = currentValue.toString().trim();
                    if (characterMap.isEmpty()){
                        characterMap = (String) ConfigManager.getConfigProperty(ConfigType.DEFAULTCHARACTERMAP);
                    }
                    BibTools.saveSpecialCharacters(bibliography,currentTypes,currentKeys,characterMap);
                }
            }
        }
    }
}
