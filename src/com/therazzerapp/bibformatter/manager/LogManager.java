package com.therazzerapp.bibformatter.manager;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <VERSION>
 */
public class LogManager {
    public static void writeError(String error){
        writeError(error, "");
    }
    public static void writeError(String error, String fileAddition){
        FileManager.exportFile(error, "./" + fileAddition +"error.log");
    }

    public static void writeDebug(String debug, String path){
        FileManager.exportFile(debug, path+"debug.txt");
    }
}
