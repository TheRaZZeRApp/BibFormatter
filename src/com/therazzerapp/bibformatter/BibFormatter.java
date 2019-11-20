package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.content.CharacterMap;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.loader.BibLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;
import com.therazzerapp.bibformatter.gui.StartUp;
import com.therazzerapp.bibformatter.manager.*;
import javafx.util.Pair;

import javax.swing.*;
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
    public static final String VERSION = "0.7.6";
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

    private static Bibliographie runCommands(Bibliographie bib, String commands){
        Matcher matcher;
        matcher = Pattern.compile("(-replaceKey|-rk) (?<param1>[^-]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("")){
                LogManager.writeError("Error: No entry specified!",bib.getName()+"_");
                return null;
            } else {
                String replaceCommand;
                if (new File(matcher.group("param1")).exists()){
                    StringBuilder sb = new StringBuilder();
                    for (String param1 : FileManager.getFileContent(new File(matcher.group("param1")))) {
                        sb.append(param1).append(" ");
                    }
                    replaceCommand = sb.toString();
                } else {
                    replaceCommand = matcher.group("param1");
                }
                if (replaceCommand.split(" ").length%2 != 0){
                    LogManager.writeError("Error: Wrong amount of arguments",bib.getName()+"_");
                    return null;
                }
                TypeType currentType = null;
                String[] commandLines = replaceCommand.split(" ");
                for (int i = 0; i < commandLines.length; i+=2) {
                    if (commandLines[i].equals("+type")){
                        currentType = TypeType.valueOf(commandLines[i+1].toUpperCase());
                        continue;
                    }
                    if (currentType == null){
                        bib.replaceKey(KeyType.valueOf(commandLines[i].toUpperCase()),KeyType.valueOf(commandLines[i+1].toUpperCase()));
                    } else {
                        bib.replaceKey(KeyType.valueOf(commandLines[i].toUpperCase()),KeyType.valueOf(commandLines[i+1].toUpperCase()),currentType);
                    }
                }
            }
        }
        //Extreme messy! Need to clean this up and make standart for every command in this style.
        matcher = Pattern.compile("(-setValue|-sv) (?<param1>[^-]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("") || !matcher.group("param1").contains("+value")){
                LogManager.writeError("Error: No parameters specified!",bib.getName()+"_");
                return null;
            } else {
                String parameters;
                if (new File(matcher.group("param1")).exists()){
                    StringBuilder sb = new StringBuilder();
                    for (String param1 : FileManager.getFileContent(new File(matcher.group("param1")))) {
                        sb.append(param1).append(" ");
                    }
                    parameters = sb.toString();
                } else {
                    parameters = matcher.group("param1");
                }

                Set<TypeType> currentTypes = new HashSet<>();           //0
                Set<KeyType> currentKeys = new HashSet<>();             //1
                StringBuilder currentMatch = new StringBuilder();       //2
                StringBuilder currentReplacement = new StringBuilder(); //3

                String[] commandLines = parameters.split(" ");

                int currentPosition = 0;

                for (int i = 0; i < commandLines.length; i++) {

                    switch (commandLines[i]){
                        case "+type":
                            currentPosition = 0;
                            currentTypes.clear();
                            break;
                        case "+key":
                            currentPosition = 1;
                            currentKeys.clear();
                            break;
                        case "+match":
                            currentPosition = 2;
                            currentMatch = new StringBuilder();
                            break;
                        case "+value":
                            currentPosition = 3;
                            currentReplacement = new StringBuilder();
                            break;
                        default:
                            switch (currentPosition){
                                case 0:
                                    if(new File(commandLines[i]).exists()){
                                        for (String s : FileManager.getFileContent(new File(commandLines[1]))) {
                                            for (String s1 : s.split(" ")) {
                                                currentTypes.add(TypeType.valueOf(s1.toUpperCase()));
                                            }
                                        }
                                    } else {
                                        currentTypes.add(TypeType.valueOf(commandLines[i].toUpperCase()));
                                    }
                                    break;
                                case 1:
                                    if(new File(commandLines[i]).exists()){
                                        for (String s : FileManager.getFileContent(new File(commandLines[1]))) {
                                            for (String s1 : s.split(" ")) {
                                                currentKeys.add(KeyType.valueOf(s1.toUpperCase()));
                                            }
                                        }
                                    } else {
                                        currentKeys.add(KeyType.valueOf(commandLines[i].toUpperCase()));
                                    }
                                    break;
                                case 2:
                                    if(new File(commandLines[i]).exists()){
                                        currentMatch.append(Utils.getCollectionAsString(FileManager.getFileContent(new File(commandLines[i]))));
                                        currentMatch.append(" ");
                                        break;
                                    } else {
                                        currentMatch.append(commandLines[i]);
                                        currentMatch.append(" ");
                                        break;
                                    }
                                case 3:
                                    if(new File(commandLines[i]).exists()){
                                        currentReplacement.append(Utils.getCollectionAsString(FileManager.getFileContent(new File(commandLines[i]))));
                                        currentReplacement.append(" ");
                                        break;
                                    } else {
                                        currentReplacement.append(commandLines[i]);
                                        currentReplacement.append(" ");
                                        break;
                                    }

                            }
                    }
                    if (i == commandLines.length-1 || (currentPosition == 3 && commandLines[i+1].startsWith("+"))){
                        bib.replaceValue(currentTypes,currentKeys,currentMatch.toString(),currentReplacement.toString());

                    }
                }
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
