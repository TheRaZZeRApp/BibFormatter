package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public class CBibliography extends Command{

    public CBibliography(String ARGUMENTS) {
        super("Bibliography", Constants.COMMANDPATTER_BIBLIOGRAPHY, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        //todo rewrite method
        /*
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
                    bibliography.setEntryList(temp.getEntryList());
                    bibliography.setComments(temp.getComments());
                    bibliography.setSaveLocation(temp.getSaveLocation());
                }
            }
        */
    }
}
