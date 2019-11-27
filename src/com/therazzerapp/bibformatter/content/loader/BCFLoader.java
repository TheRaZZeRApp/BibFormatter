package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.content.FileUtils;

import java.io.File;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.13.9
 */
public class BCFLoader {

    /**
     * Returns a {@link Set <String>} of every BibTexKey (i.e. chomsky:1990a) found in a bcf file.
     * @param file
     * @return
     */
    public static Set<String> getCitations(File file){
        return FileUtils.getMatches(file,"<bcf:citekey order=\"[0-9]*\">([^\\<]*)");
    }

    /**
     * Returns a {@link Set <String>} of every Bibliography path found in a bcf file.
     * @param file
     * @return
     */
    public static Set<String> getBibliographies(File file){
        return FileUtils.getMatches(file,"<bcf:datasource type=\"file\" datatype=\"bibtex\">([^\\<]*)");
    }
}
