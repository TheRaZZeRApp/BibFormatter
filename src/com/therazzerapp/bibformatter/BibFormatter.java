package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.content.ConfigType;
import com.therazzerapp.bibformatter.content.loader.BibLoader;
import com.therazzerapp.bibformatter.content.saver.BibSaver;
import com.therazzerapp.bibformatter.gui.StartUp;
import com.therazzerapp.bibformatter.manager.ConfigManager;
import com.therazzerapp.bibformatter.manager.FileManager;
import com.therazzerapp.bibformatter.manager.LogManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class BibFormatter {
    public static final String version = "0.2.0";
    public static void main(String[] args) {

        ConfigManager.load();

        if(!new File("./run.bat").exists()){
            createRunBat();
        }

        StartUp startUp = new StartUp();

        if (args.length == 0){
            SwingUtilities.invokeLater(startUp);
        } else if (args.length == 1){
            LogManager.writeError("Error: Set debug to true/false!\nUsage: <file:bibFile> <boolean:debug> -c1 -c2 -c3 ...");
        } else if (args.length == 2){
            LogManager.writeError("Error: No commands found!\nUsage: <file:bibFile> <boolean:debug> -c1 -c2 -c3 ...");
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
        matcher = Pattern.compile("-capitalizeValue (?<capParam1>[^ ]{0,})").matcher(commands);
        if (matcher.find()){
            try {
                bib = BibTools.capitalizeValue(bib,KeyType.valueOf(matcher.group("capParam1").toUpperCase()));
            } catch (IllegalArgumentException ex){
                LogManager.writeError("Error: \"" + matcher.group("capParam1") + "\" is not a valid capitalization parameter!",bib.getName()+"_");
                return null;
            }
        }
        matcher = Pattern.compile("-orderEntries (?<param1>[^- ]{0,})").matcher(commands);
        if (matcher.find()){
            if (matcher.group("param1").equals("")){
                bib = BibTools.orderEntries(bib,ConfigManager.getConfigProperty(ConfigType.ENTRYORDER).toString());
            } else {
                StringBuilder sb = new StringBuilder();
                for (String param1 : FileManager.getFileContent(new File(matcher.group("param1")))) {
                    sb.append(param1).append(" ");
                }
                LogManager.writeError(sb.toString());
                bib = BibTools.orderEntries(bib,sb.toString());
            }
        }

        return bib;
    }

    private static void createRunBat(){
        String content = "@echo off\n" +
                "rem ================ Settings ================\n" +
                "set debug=false\n" +
                "set commands=\n" +
                "rem ================= Readme =================\n" +
                "rem Usage: <file:bibFile> <boolean:debug> -c1 -c2 -c3 ...\n" +
                "rem Commands:\n" +
                "rem capitaliseTitles: surrounds every uppercase character in the title entry with {}\n" +
                "rem ==========================================\n" +
                "set a=\"Usage: <file:bibFile> <boolean:debug> -c1 -c2 -c3 ...\"\n" +
                "if [\"%~1\"]==[\"\"] (\n" +
                "\techo Error: No bib file specified!\n" +
                "\techo %a%\n" +
                "\tgoto stop\n" +
                ") \n" +
                "if \"%debug%\" NEQ \"true\" (\n" +
                "\tif \"%debug%\" NEQ \"false\" (\n" +
                "\t\techo Error: Set debug to true/false!\n" +
                "\t\techo %a%\n" +
                "\t\tgoto stop\n" +
                "\t) else (\n" +
                "\t\tgoto s2\n" +
                "\t)\n" +
                "\techo Error: Set debug to true/false!\n" +
                "\techo %a%\n" +
                "\tgoto stop\n" +
                ")\n" +
                ":s2\n" +
                "if \"%commands%\"==\"\" (\n" +
                "\techo Error: No commands found!\n" +
                "\techo %a%\n" +
                "\tgoto stop\n" +
                ")\n" +
                "echo Commands: %commands%\n" +
                "java -jar BibFormatter.jar %1 %debug% %commands%\n" +
                ":stop\n" +
                "pause";
        FileManager.exportFile(content,"./run.bat");
    }
}
