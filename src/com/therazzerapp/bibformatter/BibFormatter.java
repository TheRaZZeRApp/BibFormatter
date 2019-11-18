package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.gui.StartUp;

import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class BibFormatter {
    public static final String version = "0.2.0";
    public static String jarPath = "";
    public static boolean debug = false;
    public static void main(String[] args) {

        String jarPath = "C:\\";
        try {
            jarPath = BibFormatter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            jarPath = jarPath.replace("BibFormatter.jar","");
        } catch (URISyntaxException e) {
            FileManager.writeError("Error: Jar path not found!");
            e.printStackTrace();
        }

        StartUp startUp = new StartUp();

        if (args.length == 0){
            SwingUtilities.invokeLater(startUp);
        } else {

        }

        File file = new File("C:\\Users\\Computer\\Documents\\Studium\\FrauMeier\\BibFix2\\VarDefSemComFinalBib.bib");
        Bibliographie bib = FileManager.getBib(file);

        Set<Keys> keys = new HashSet<>();
        keys.add(Keys.BDSK_FILE_1);
        keys.add(Keys.BDSK_URL_1);

        bib = BibTools.formatMonth(bib);
        bib.removeEntrie(keys);
        bib = BibTools.formatPages(bib);
        //bib = BibTools.replaceValue(bib,Keys.AUTHOR,"Repp, Sophie","Test");
        bib = BibTools.capitaliseTitles(bib);

        bib.replaceKey(Keys.ADDRESS,Keys.LOCATION);
        bib.removeEntrie(Keys.ADDRESS);

        FileManager.printBib(bib,"C:\\Users\\Computer\\Documents\\Studium\\FrauMeier\\BibFix2\\VarDefSemComFinalBibTest.bib",false);
        Utils.debugEntries(bib,"book", "C:\\Users\\Computer\\Documents\\Studium\\FrauMeier\\BibFix2\\");

    }
}
