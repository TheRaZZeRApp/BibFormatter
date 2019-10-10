package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;

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

        File file = new File("C:\\Users\\Computer\\Documents\\Studium\\FrauMeier\\BibZusammenfuehrung\\Test\\CecilesBibKonv.bib");
        Bibliographie bib = FileManager.getBib(file);

        Set<Keys> keys = new HashSet<>();
        keys.add(Keys.ORIGINAL);
        keys.add(Keys.OWNER);
        keys.add(Keys.COPY);
        keys.add(Keys.BDSK_URL_1);
        keys.add(Keys.BDSK_URL_2);
        keys.add(Keys.BDSK_URL_3);
        keys.add(Keys.BDSK_FILE_1);

        bib = BibTools.formatMonth(bib);
        bib = BibTools.removeEntrie(bib,keys);
        bib = BibTools.formatPages(bib);
        //bib = BibTools.replaceValue(bib,Keys.AUTHOR,"Repp, Sophie","Test");
        bib = BibTools.capitaliseTitles(bib);

        FileManager.printBib(bib,"C:\\Users\\Computer\\Documents\\Studium\\FrauMeier\\BibZusammenfuehrung\\Test\\Test.bib");
        Utils.debugEntries(bib,"C:\\Users\\Computer\\Documents\\Studium\\FrauMeier\\BibZusammenfuehrung\\Test\\");

    }
}
