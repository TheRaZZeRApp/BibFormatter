package com.therazzerapp.bibformatter.manager;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.content.FileUtils;

/**
 * Needs rewrite
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.1.0
 */
public class LogManager {

    /**
     *
     * @param error
     */
    public static void writeError(String error){
        System.out.println(Constants.ERROR + error);
        writeError(Constants.ERROR + error, "");
    }

    /**
     *
     * @param error
     * @param fileAddition
     */
    public static void writeError(String error, String fileAddition){
        FileUtils.exportFile(error, "./" + fileAddition +"error.log");
    }

    /**
     *
     * @param debug
     * @param path
     */
    public static void writeDebug(String debug, String path){
        FileUtils.exportFile(debug, path+"debug.txt");
    }
}
