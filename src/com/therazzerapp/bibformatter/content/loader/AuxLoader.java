package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.content.FileUtils;

import java.io.File;
import java.util.Set;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.12.9
 */
public class AuxLoader {

    /**
     * Returns a {@link Set<String>} of every BibTexKey (i.e. chomsky:1990a) found in an aux file.
     * @param file
     * @return
     */
    public static Set<String> getCitations(File file){
        return FileUtils.getMatches(file,"\\\\abx@aux@defaultrefcontext\\{0\\}\\{([^\\}]*)");//257
    }
}
