package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.BibFormatter;
import com.therazzerapp.bibformatter.manager.FileManager;
import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class BibSaver {
    public static void save(Bibliographie bibliographie, String fileName){
        StringBuilder sb = new StringBuilder();
        if (bibliographie.getComments() != null){
            for (String s : bibliographie.getComments()) {
                sb.append(s + "\n");
            }
        }
        sb.append("%%Modified using BibFormatter " + BibFormatter.version + "\n");
        sb.append("%%Entries: " + bibliographie.getEntrieList().size()+ "\n\n");
        for (Entry entry : bibliographie.getEntrieList()) {
            sb.append(entry.getRawEntry());
        }
        FileManager.exportFile(sb.toString(),fileName);
    }
}
