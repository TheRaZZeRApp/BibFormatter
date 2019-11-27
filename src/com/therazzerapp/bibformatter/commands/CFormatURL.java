package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
import com.therazzerapp.bibformatter.KeyType;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.15.10
 */
public class CFormatURL {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-formatURL|-fu)(?<arg>[^-]{0,})";

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

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,currentKeys,null,null );
                if (Utils.isCommandEndReached(commandLines,i,2,currentPosition)){
                    BibTools.formatURL(bibliography,currentTypes,currentKeys);
                }
            }
        }
    }
}
