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
        SpecialCharacterManager.init();
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
            //todo Need to rewrite this section!
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
        if (command.matches(Constants.COMMANDPATTER_REPLACEKEY)){
            new CReplaceKey(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_SETVALUE)){
            new CSetValue(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_REMOVEENTRY)){
            new CRemoveEntry(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_SAVECAPITALS)){
            new CSaveCapitals(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_ORDERKEYS)){
            new COrderKeys(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_FORMATMONTH)){
            new CFormatMonth(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_FORMATPAGES)){
            new CFormatPages(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_SAVESYMBOLS)){
            new CSaveSymbols(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_CHECKTYPE)){
            new CCheckType(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_CREATEKEY)){
            new CCreateKey(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_FROMAUX)){
            new CFromAux(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_ADDENTRY)){
            new CAddEntry(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_MERGEBIBLIOGRAPHIES)){
            new CMergeBibliographies(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_ORDERTYPES)){
            new COrderTypes(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_FORMATURL)){
            new CFormatURL(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_GENERATEPUBLISHER)){
            new CGeneratePublisher(arguments).run(bib);
        } else if (command.matches(Constants.COMMANDPATTER_FORMATDOI)){
            new CFormatDoi(arguments).run(bib);
        }
    }
}
