package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.manager.FileManager;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.0.0
 */
public class BibSaver {

    /**
     *
     * @param bibliography
     * @param fileName
     */
    public static void save(Bibliography bibliography, String fileName){
        StringBuilder sb = new StringBuilder();
        if (bibliography.getComments() != null){
            for (String s : bibliography.getComments()) {
                sb.append(s + "\n");
            }
        }
        sb.append("%%Modified using BibFormatter " + Constants.VERSION + "\n");
        sb.append("%%Entries: " + bibliography.getEntrieList().size()+ "\n\n");
        for (Entry entry : bibliography.getEntrieList()) {
            sb.append(entry.getRawEntry());
        }
        if (!fileName.endsWith(".bib")){
            fileName+=".bib";
        }
        FileManager.exportFile(sb.toString(),fileName);
    }
}
