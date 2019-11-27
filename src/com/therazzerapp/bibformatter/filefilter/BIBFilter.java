package com.therazzerapp.bibformatter.filefilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.1.0
 */
public class BIBFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bib");
    }

    @Override
    public String getDescription() {
        return "BIB File";
    }
}
