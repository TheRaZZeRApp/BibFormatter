package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.manager.ConfigManager;

import java.util.ArrayList;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.14.9
 */
public class COrderTypes {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-orderTypes|-ot)(?<arg>[^-]{0,})";

    /**
     *
     * @param bibliography
     * @param arguments
     */
    public static void run(Bibliography bibliography, String arguments){
        if (Utils.isArgumentsValid(ARGUMENTPATTERN,arguments)){
            String[] commandLines = Utils.getCommand(arguments).split(" ");
            int currentPosition = -1;
            ArrayList<TypeType> currentTypes = new ArrayList<>();           //0


            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,null,null,null);
                if (Utils.isCommandEndReached(commandLines,i,1,currentPosition)){
                    if (currentTypes.isEmpty()){
                        String temp = (String) ConfigManager.getConfigProperty(ConfigType.TYPEORDER);
                        for (String s : temp.split(" ")) {
                            currentTypes.add(TypeType.valueOf(s.toUpperCase()));
                        }
                    }
                    BibTools.orderTypes(bibliography,currentTypes);
                }
            }
        }
    }
}
