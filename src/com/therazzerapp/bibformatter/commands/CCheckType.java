package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
import com.therazzerapp.bibformatter.TypeType;
import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.RequiredFields;
import com.therazzerapp.bibformatter.manager.RequiredFieldsManager;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.9.8
 */
public class CCheckType {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-checkType|-ct) (?<arg>[^-]{0,})";

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
            StringBuilder currentMatch = new StringBuilder();       //2
            StringBuilder currentValue = new StringBuilder(); //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,currentTypes,null,currentMatch,currentValue);
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){

                    RequiredFields requiredFields;
                    if (currentMatch.toString().isEmpty()){
                        requiredFields = RequiredFieldsManager.getDefaultMap();
                    } else {
                        File file = new File(currentMatch.toString());
                        if (file.exists()){
                            requiredFields = RequiredFieldsManager.load(file);
                        } else {
                            requiredFields = RequiredFieldsManager.getRequiredFieldsMap(currentMatch.toString());
                        }
                    }
                    BibTools.checkType(bibliography,currentTypes,requiredFields,currentValue.toString().trim(),true);
                }
            }
        }
    }
}
