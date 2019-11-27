package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
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
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.8.8
 */
public class CFormatPages {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-formatPages|-fp) (?<arg>[^-]{0,})";

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
            StringBuilder currentValue = new StringBuilder();       //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,null,null,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    String style = currentValue.toString().trim();
                    if (style.isEmpty()){
                        style = (String) ConfigManager.getConfigProperty(ConfigType.DEFAULTPAGESFORMAT);
                    }
                    BibTools.formatPages(bibliography,currentTypes,style);
                }
            }
        }
    }
}
