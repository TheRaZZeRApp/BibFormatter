package com.therazzerapp.bibformatter.content;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.1.0
 */
public interface ContentUpdater {
    /**
     *
     * @param contentID
     *          <br>-1 - All
     *          <br> 0 - BuildSettings
     *          <br> 1 - BuildProgram
     *          <br> 2 - Config
     *          <br> 3 - LogPattern
     */
    void updateContent(int contentID);
}
