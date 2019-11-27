package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Utils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.loader.BibLoader;

import java.io.File;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.15.10
 */
public class CBibliography {
    public static final String ARGUMENTPATTERN = "";
    public static final String COMMANDPATTERN = "(-bibliography|-b) (?<arg>[^-]{0,})";

    /**
     *
     * @param bibliography
     * @param arguments
     */
    public static void run(Bibliography bibliography, String arguments){
        if (Utils.isArgumentsValid(ARGUMENTPATTERN,arguments)){
            String[] commandLines = Utils.getCommand(arguments).split(" ");
            int currentPosition = -1;

            StringBuilder currentValue = new StringBuilder(); //3

            for (int i = 0; i < commandLines.length; i++) {
                currentPosition = Utils.getCommandValues(commandLines, currentPosition,i,null ,null,null,currentValue,false);
                String bibPath = "";
                if (currentPosition == -1){
                    bibPath = commandLines[i];
                }
                String saveLocation = currentValue.toString();
                if (currentValue.length() == 0){
                    saveLocation = "./" + Utils.replaceLast(bibPath,".bib","") + "_formatted.bib";
                }
                if (Utils.isCommandEndReached(commandLines,i,3,currentPosition)){
                    Bibliography temp = BibLoader.load(new File(bibPath),saveLocation);
                    bibliography.setName(temp.getName());
                    bibliography.setEntrieList(temp.getEntrieList());
                    bibliography.setComments(temp.getComments());
                    bibliography.setSaveLocation(temp.getSaveLocation());
                }
            }
        }
    }
}
