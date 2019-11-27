package com.therazzerapp.bibformatter.content;

import java.util.HashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.1.0
 */
public class ContentObserver {

    private static Set<ContentUpdater> contentUpdaterSet = new HashSet<>();

    /**
     *
     * @param contentUpdater
     */
    public static void addContentUser(ContentUpdater contentUpdater){
        contentUpdaterSet.add(contentUpdater);
    }

    /**
     *
     * @param contentUpdater
     */
    public static void removeContentUser(ContentUpdater contentUpdater){
        contentUpdaterSet.remove(contentUpdater);
    }

    /**
     *
     * @param contentID
     *          <br>-1 - All
     *          <br> 0 - BuildSettings
     *          <br> 1 - BuildProgram
     *          <br> 2 - Config
     *          <br> 3 - LogPattern
     */
    public static void update(int contentID){
        for (ContentUpdater contentUpdater : contentUpdaterSet) {
            contentUpdater.updateContent(contentID);
        }
    }
}
