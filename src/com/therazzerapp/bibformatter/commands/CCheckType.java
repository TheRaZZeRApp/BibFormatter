package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.BibTools;
import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.RequiredFields;
import com.therazzerapp.bibformatter.manager.RequiredFieldsManager;

import java.io.File;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public class CCheckType extends Command {
    public CCheckType(String ARGUMENTS) {
        super("CheckType", Constants.COMMANDPATTER_CHECKTYPE, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        RequiredFields requiredFields;
        if (match.isEmpty()){
            requiredFields = RequiredFieldsManager.getDefaultMap();
        } else {
            File file = new File(match);
            if (file.exists()){
                requiredFields = RequiredFieldsManager.load(file);
            } else {
                requiredFields = RequiredFieldsManager.getRequiredFieldsMap(match);
            }
        }
        BibTools.checkType(bibliography,types,requiredFields,value,true);
    }
}
