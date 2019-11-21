package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.commands.*;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.loader.BibLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;
import com.therazzerapp.bibformatter.manager.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.0.0
 */
public class BibFormatter {
    public static final String VERSION = "0.7.8";

    public static void main(String[] args) {

        ConfigManager.load();
        SpecialCharacterManager.initiate();
        RequiredFieldsManager.load();

        if(!new File("./run.bat").exists()){
            StringBuilder sb = new StringBuilder();
            for (String s : FileManager.getFileContentJar("data/run.bat")) {
                sb.append(s).append("\n");
            }
            FileManager.exportFile(sb.toString(),"./run.bat");
        }

        if (args.length == 0){
            //StartUp startUp = new StartUp();
            //SwingUtilities.invokeLater(startUp);
        } else if (args.length == 1){
            LogManager.writeError("Error: Set debug to true/false!\nUsage: <file:bibFile> <boolean:debug> -c1 pn -c2 pn -cn pn ...");
        } else if (args.length == 2){
            LogManager.writeError("Error: No commands found!\nUsage: <file:bibFile> <boolean:debug> -c1 pn -c2 pn -cn pn ...");
        } else {
            File file = new File(args[0]);
            if(!file.exists()){
                LogManager.writeError("Error: Bib file not found!");
                System.exit(0);
            }
            Bibliographie bib = BibLoader.load(new File(args[0]));
            StringBuilder temp = new StringBuilder();
            for (String s : Arrays.copyOfRange(args, 2, args.length)) {
                temp.append(s).append(" ");
            }
            bib = runCommands(bib, temp.toString());
            if (bib != null){
                BibSaver.save(bib,"./" + bib.getName() + "_formatted.bib");
            }
        }
    }

    private static Bibliographie runCommands(Bibliographie bib, final String commands){
        Matcher matcher;

        matcher = Pattern.compile(CReplaceKey.COMMANDPATTERN).matcher(commands);
        if (matcher.find()){
            CReplaceKey.run(bib,matcher.group(CReplaceKey.PATTERNGROUP1));
        }

        matcher = Pattern.compile(CSetValue.COMMANDPATTERN).matcher(commands);
        if (matcher.find()){
            CSetValue.run(bib,matcher.group(CSetValue.PATTERNGROUP1));
        }

        matcher = Pattern.compile(CRemoveEntry.COMMANDPATTERN).matcher(commands);
        if (matcher.find()){
            CRemoveEntry.run(bib,matcher.group(CRemoveEntry.PATTERNGROUP1));
        }

        matcher = Pattern.compile(CSaveCapitals.COMMANDPATTERN).matcher(commands);
        if (matcher.find()){
            CSaveCapitals.run(bib,matcher.group(CSaveCapitals.PATTERNGROUP1));
        }

        matcher = Pattern.compile(COrderEntries.COMMANDPATTERN).matcher(commands);
        if (matcher.find()){
            COrderEntries.run(bib,matcher.group(COrderEntries.PATTERNGROUP1));
        }

        matcher = Pattern.compile("(-formatMonth|-fm) (?<param1>[^ ]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("number")){
                BibTools.formatMonth(bib, false);
            } else {
                BibTools.formatMonth(bib, true);
            }
        }

        matcher = Pattern.compile("(-formatPages|-fp) (?<param1>[^ ]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("single")){
                BibTools.formatPages(bib, true);
            } else {
                BibTools.formatPages(bib, false);
            }
        }

        matcher = Pattern.compile("(-saveSpecialCharacters|-ss) (?<param1>[^-]{0,})").matcher(commands);
        if (matcher.find()){
            String characterMap = (String) ConfigManager.getConfigProperty(ConfigType.DEFAULTCHARACTERMAP);
            String parameter = matcher.group("param1");
            Matcher cmatch = Pattern.compile("\\+characterMap (?<map>[^ ]*)").matcher(parameter);
            if (cmatch.find()){
                characterMap = cmatch.group("map");
                SpecialCharacterManager.load(new File("./Data/" + characterMap +".txt"));
                parameter = parameter.replaceAll("\\+characterMap [^ ]* ","");
            }
            if (parameter.equals("")){
                LogManager.writeError("Error: No entry specified!",bib.getName()+"_");
                return null;
            } else {
                if (new File(parameter).exists()){
                    for (String value : FileManager.getFileContent(new File(parameter))) {
                        BibTools.saveSpecialCharacters(bib,KeyType.valueOf(value.toUpperCase()),characterMap);
                    }
                } else {
                    for (String value : parameter.split(" ")) {
                        BibTools.saveSpecialCharacters(bib,KeyType.valueOf(value.toUpperCase()),characterMap);
                    }
                }
            }
        }
        return bib;
    }

}
