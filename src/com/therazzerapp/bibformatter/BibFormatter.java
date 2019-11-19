package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.loader.BibLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;
import com.therazzerapp.bibformatter.gui.StartUp;
import com.therazzerapp.bibformatter.manager.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class BibFormatter {
    public static final String version = "0.4.3";
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

    private static Bibliographie runCommands(Bibliographie bib, String commands){
        Matcher matcher;
        matcher = Pattern.compile("-capitalizeValue (?<param1>[^-]{0,})").matcher(commands);
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
        matcher = Pattern.compile("-orderEntries (?<param1>[^-]{0,})").matcher(commands);
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
        matcher = Pattern.compile("-formatMonth (?<param1>[^ ]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("number")){
                BibTools.formatMonth(bib, false);
            } else {
                BibTools.formatMonth(bib, true);
            }
        }
        matcher = Pattern.compile("-formatPages (?<param1>[^ ]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("single")){
                BibTools.formatPages(bib, true);
            } else {
                BibTools.formatPages(bib, false);
            }
        }
        matcher = Pattern.compile("-removeEntry (?<param1>[^-]{0,})").matcher(commands);
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
        matcher = Pattern.compile("-saveSpecialCharacters (?<param1>[^-]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("")){
                LogManager.writeError("Error: No entry specified!",bib.getName()+"_");
                return null;
            } else {
                if (new File(matcher.group("param1")).exists()){
                    for (String value : FileManager.getFileContent(new File(matcher.group("param1")))) {
                        BibTools.saveSpecialCharacters(bib,KeyType.valueOf(value.toUpperCase()));
                    }
                } else {
                    for (String value : matcher.group("param1").split(" ")) {
                        BibTools.saveSpecialCharacters(bib,KeyType.valueOf(value.toUpperCase()));
                    }
                }
            }
        }
        return bib;
    }
}
