package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.content.CharacterMap;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.loader.BibLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;
import com.therazzerapp.bibformatter.gui.StartUp;
import com.therazzerapp.bibformatter.manager.*;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.0.0
 */
public class BibFormatter {
    public static final String version = "0.6.4";
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
        for (CharacterMap characterMap : SpecialCharacterManager.getCharacterMaps()) {
            System.out.println(characterMap.getName());
        }

    }

    private static Bibliographie runCommands(Bibliographie bib, String commands){
        Matcher matcher;
        matcher = Pattern.compile("(-capitalizeValue|-cv) (?<param1>[^-]{0,})").matcher(commands);
        if (matcher.find()){
            try {
                if (new File(matcher.group("param1")).exists()){
                    for (String param1 : FileManager.getFileContent(new File(matcher.group("param1")))) {
                        BibTools.capitalizeValue(bib, KeyType.valueOf(param1.toUpperCase()));
                    }
                } else {
                    for (String value : matcher.group("param1").split(" ")) {
                        BibTools.capitalizeValue(bib, KeyType.valueOf(value.toUpperCase()));
                    }
                }
            } catch (IllegalArgumentException ex){
                LogManager.writeError("Error: \"" + matcher.group("param1") + "\" is not a valid capitalization parameter!",bib.getName()+"_");
                return null;
            }
        }
        matcher = Pattern.compile("(-orderEntries|-oe) (?<param1>[^-]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("")){
                BibTools.orderEntries(bib, ConfigManager.getConfigProperty(ConfigType.ENTRYORDER).toString());
            } else {
                if (new File(matcher.group("param1")).exists()){
                    StringBuilder sb = new StringBuilder();
                    for (String param1 : FileManager.getFileContent(new File(matcher.group("param1")))) {
                        sb.append(param1).append(" ");
                    }
                    BibTools.orderEntries(bib, sb.toString());
                } else {
                    BibTools.orderEntries(bib, matcher.group("param1"));
                }
            }
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
        matcher = Pattern.compile("(-removeEntry|-re) (?<param1>[^-]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("")){
                LogManager.writeError("Error: No entry specified!",bib.getName()+"_");
                return null;
            } else {
                if (new File(matcher.group("param1")).exists()){
                    for (String param1 : FileManager.getFileContent(new File(matcher.group("param1")))) {
                        bib.removeEntrie(param1);
                    }
                } else {
                    bib.removeEntrie(matcher.group("param1"));
                }
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
