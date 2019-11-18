package com.therazzerapp.bibformatter.filefilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
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
