package com.therazzerapp.bibformatter.manager;

/**
 * Needs rewrite
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since 0.1.0
 */
public class LogManager {

    /**
     *
     * @param error
     */
    public static void writeError(String error){
        System.out.println(error);
        writeError(error, "");
    }

    /**
     *
     * @param error
     * @param fileAddition
     */
    public static void writeError(String error, String fileAddition){
        FileManager.exportFile(error, "./" + fileAddition +"error.log");
    }

    /**
     *
     * @param debug
     * @param path
     */
    public static void writeDebug(String debug, String path){
        FileManager.exportFile(debug, path+"debug.txt");
    }
}
