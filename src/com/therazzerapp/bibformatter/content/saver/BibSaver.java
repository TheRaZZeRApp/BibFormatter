package com.therazzerapp.bibformatter.content.saver;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.content.FileUtils;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;

/**
 * Manages the {@link Bibliography} exportation.
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.0.0
 */
public class BibSaver {

    /**
     * Exports a {@link Bibliography} object as a .bib file.
     * @param bibliography the bib to export
     * @param fileName the path where to save the bib
     */
    public static void save(Bibliography bibliography, String fileName){
        StringBuilder sb = new StringBuilder();
        if (bibliography.getComments() != null){
            for (String s : bibliography.getComments()) {
                sb.append(s).append("\n");
            }
        }

        int i = 0;

        StringBuilder entries = new StringBuilder();
        for (Entry entry : bibliography.getEntrieList()) {
            if (entry.getKeys().isEmpty()){
                continue;
            }
            entries.append(entry.getRawEntry());
            i++;
        }

        sb.append("%%Modified using BibFormatter " + Constants.VERSION + "\n");
        sb.append("%%Entries: ").append(i).append("\n\n");
        sb.append(entries.toString());

        if (!fileName.endsWith(".bib")){
            fileName+=".bib";
        }
        FileUtils.exportFile(sb.toString(),fileName);
    }
}
