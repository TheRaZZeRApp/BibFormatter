package com.therazzerapp.bibformatter.content.loader;

import com.therazzerapp.bibformatter.manager.FileManager;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.12.9
 */
public class AuxLoader {

    /**
     * Returns a {@link Set<String>} of every BibTexKey (i.e. chomsky:1990a) found in an aux file.
     * @param file
     * @return
     */
    public static Set<String> getCitations(File file){
        return FileManager.getMatches(file,"\\\\abx@aux@defaultrefcontext\\{0\\}\\{([^\\}]*)");//257
    }
}
