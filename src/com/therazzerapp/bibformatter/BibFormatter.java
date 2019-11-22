package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.commands.*;
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
    public static final String VERSION = "0.8.8";

    public static void main(String[] args) {

        ConfigManager.load();
        SpecialCharacterManager.initiate();
        RequiredFieldsManager.init();

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
            Matcher matcher = Pattern.compile("(-[a-zA-Z0-9]*([^-]{0,}))").matcher(temp);
            while (matcher.find()){
                runCommands(bib, matcher.group(1).trim(), matcher.group(2).trim());
            }
            BibSaver.save(bib,"./" + bib.getName() + "_formatted.bib");
        }
    }

    private static void runCommands(Bibliographie bib, final String command, String arguments){
        if (command.matches(CReplaceKey.COMMANDPATTERN)){
            CReplaceKey.run(bib,arguments);
        } else if (command.matches(CSetValue.COMMANDPATTERN)){
            CSetValue.run(bib,arguments);
        } else if (command.matches(CRemoveEntry.COMMANDPATTERN)){
            CRemoveEntry.run(bib,arguments);
        } else if (command.matches(CSaveCapitals.COMMANDPATTERN)){
            CSaveCapitals.run(bib,arguments);
        } else if (command.matches(COrderEntries.COMMANDPATTERN)){
            COrderEntries.run(bib,arguments);
        } else if (command.matches(CFormatMonth.COMMANDPATTERN)){
            CFormatMonth.run(bib,arguments);
        } else if (command.matches(CFormatPages.COMMANDPATTERN)){
            CFormatPages.run(bib,arguments);
        } else if (command.matches(CSaveSymbols.COMMANDPATTERN)){
            CSaveSymbols.run(bib,arguments);
        } else if (command.matches(CCheckType.COMMANDPATTERN)){
            CCheckType.run(bib,arguments);
        }
    }
}
