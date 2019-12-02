package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.commands.*;
import com.therazzerapp.bibformatter.content.FileUtils;
import com.therazzerapp.bibformatter.content.loader.BibLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;
import com.therazzerapp.bibformatter.manager.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.0.0
 */
public class BibFormatter {

    public static void main(String[] args) {

        ConfigManager.load();
        SpecialCharacterManager.initiate();
        RequiredFieldsManager.init();
        DoiPrefixManager.init();

        if(!new File("./run.bat").exists()){
            StringBuilder sb = new StringBuilder();
            for (String s : FileUtils.getFileContentJar("data/run.bat")) {
                sb.append(s).append("\n");
            }
            FileUtils.exportFile(sb.toString(),"./run.bat");
        }

        if (args.length == 0){
            //StartUp startUp = new StartUp();
            //SwingUtilities.invokeLater(startUp);
        } else if (args.length == 2){
            LogManager.writeError("Error: No commands found!\nUsage: ");
        } else {
            //Need to rewrite.
            File file;
            Bibliography bib = null;
            StringBuilder temp = new StringBuilder();
            String saveLocation = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-b") || args[i].equals("-bibliography")){
                    if (bib != null){
                        Matcher matcher = Pattern.compile("(-[a-zA-Z0-9]*([^-]{0,}))").matcher(temp.toString());
                        while (matcher.find()){
                            runCommands(bib, matcher.group(1).trim(), matcher.group(2).trim());
                        }
                        BibSaver.save(bib,saveLocation);
                    }
                    file = new File(args[i+1]);
                    if(!file.exists()){
                        LogManager.writeError("Error: Bib file not found!");
                        System.exit(0);
                    }
                    temp = new StringBuilder();
                    if (!args[i+2].startsWith("-")){
                        saveLocation = args[i+2];
                        bib = BibLoader.load(new File(args[i+1]),saveLocation);
                        i++;
                    } else {
                        saveLocation = "./" + Utils.replaceLast(file.getName(),".bib","") + "_formatted.bib";
                        bib = BibLoader.load(new File(args[i+1]),saveLocation);
                    }
                    i++;
                } else {
                    temp.append(args[i]);
                    temp.append(" ");
                }
            }
            Matcher matcher = Pattern.compile("(-[a-zA-Z0-9]*([^-]{0,}))").matcher(temp.toString());
            while (matcher.find()){
                runCommands(bib, matcher.group(1).trim(), matcher.group(2).trim());
            }
            BibSaver.save(bib,saveLocation);
        }
    }

    /**
     *
     * @param bib
     * @param command
     * @param arguments
     */
    private static void runCommands(Bibliography bib, final String command, String arguments){
        if (command.matches(CReplaceKey.COMMANDPATTERN)){
            CReplaceKey.run(bib,arguments);
        } else if (command.matches(CSetValue.COMMANDPATTERN)){
            CSetValue.run(bib,arguments);
        } else if (command.matches(CRemoveEntry.COMMANDPATTERN)){
            CRemoveEntry.run(bib,arguments);
        } else if (command.matches(CSaveCapitals.COMMANDPATTERN)){
            CSaveCapitals.run(bib,arguments);
        } else if (command.matches(COrderKeys.COMMANDPATTERN)){
            COrderKeys.run(bib,arguments);
        } else if (command.matches(CFormatMonth.COMMANDPATTERN)){
            CFormatMonth.run(bib,arguments);
        } else if (command.matches(CFormatPages.COMMANDPATTERN)){
            CFormatPages.run(bib,arguments);
        } else if (command.matches(CSaveSymbols.COMMANDPATTERN)){
            CSaveSymbols.run(bib,arguments);
        } else if (command.matches(CCheckType.COMMANDPATTERN)){
            CCheckType.run(bib,arguments);
        } else if (command.matches(CCreateKey.COMMANDPATTERN)){
            CCreateKey.run(bib,arguments);
        } else if (command.matches(CFromAux.COMMANDPATTERN)){
            CFromAux.run(bib,arguments);
        } else if (command.matches(CAddEntry.COMMANDPATTERN)){
            CAddEntry.run(bib,arguments);
        } else if (command.matches(CMergeBibliographies.COMMANDPATTERN)){
            CMergeBibliographies.run(bib,arguments);
        } else if (command.matches(COrderTypes.COMMANDPATTERN)){
            COrderTypes.run(bib,arguments);
        } else if (command.matches(CFormatURL.COMMANDPATTERN)){
            CFormatURL.run(bib,arguments);
        } else if (command.matches(CGeneratePublisher.COMMANDPATTERN)){
            CGeneratePublisher.run(bib,arguments);
        } else if (command.matches(CFormatDoi.COMMANDPATTERN)){
            CFormatDoi.run(bib,arguments);
        }
    }
}
