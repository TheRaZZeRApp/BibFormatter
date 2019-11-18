package com.therazzerapp.bibformatter.content.saver;

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
    public static void save(Bibliographie bibliographie, String fileName, boolean noBracketsOnInt){
        StringBuilder sb = new StringBuilder();
        if (bibliographie.getComments() != null){
            for (String s : bibliographie.getComments()) {
                sb.append(s + "\n");
            }
        }
        sb.append("%%Modified using BibFormatter v.1.0\n\n");
        for (Entry entry : bibliographie.getEntrieList()) {
            sb.append(entry.getRawEntry(noBracketsOnInt));
        }
        FileManager.exportFile(sb.toString(),fileName);
    }

    public static void save(Bibliographie bibliographie, String fileName){
        save(bibliographie,fileName,true);
    }
}
