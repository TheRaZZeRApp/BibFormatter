package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.13
 */
public class CGeneratePublisher extends Command{

    public CGeneratePublisher(String ARGUMENTS) {
        super("GeneratePublisher", Constants.COMMANDPATTER_GENERATEPUBLISHER, ARGUMENTS);
    }

    @Override
    protected void action(Bibliography bibliography) {
        compileArgs();
        Set<Integer> doiPrefix = new HashSet<>();
        if (!match.isEmpty()){
            for (String s : match.split(" ")) {
                try{
                    doiPrefix.add(Integer.parseInt(s));
                } catch (NumberFormatException ignored){
                    //todo write error message
                    return;
                }
            }
        }
        BibTools.generatePublisher(bibliography,types,keys,doiPrefix,isYes());

    }
}
