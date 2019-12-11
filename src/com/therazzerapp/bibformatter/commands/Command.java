package com.therazzerapp.bibformatter.commands;

import com.therazzerapp.bibformatter.*;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.content.FileUtils;
import com.therazzerapp.bibformatter.manager.LogManager;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since 0.19.12
 */
public abstract class Command {
    public final String NAME;
    public final String ARGUMENTPATTERN;
    public final String COMMANDPATTERN;
    public final String USAGE;
    public final String ARGUMENTS;
    protected String arg1 = Constants.REGEX_DEF_ARG1;
    protected String arg2 = Constants.REGEX_DEF_ARG2;
    protected String arg3 = Constants.REGEX_DEF_ARG3;
    protected String arg4 = Constants.REGEX_DEF_ARG4;
    protected Set<TypeType> types = new HashSet<>();
    protected Set<KeyType> keys = new HashSet<>();
    protected String value = "";
    protected String match = "";
    protected boolean readFileArg1 = true;
    protected boolean readFileArg2 = true;
    protected boolean readFileArg3 = true;
    protected boolean readFileArg4 = true;

    /**
     *
     * @param name
     * @param ARGUMENTPATTERN
     * @param COMMANDPATTERN
     * @param USAGE
     * @param arguments
     */
    public Command(String name, String ARGUMENTPATTERN, String COMMANDPATTERN, String USAGE, String arguments) {
        this.NAME = name;
        this.ARGUMENTPATTERN = ARGUMENTPATTERN;
        this.COMMANDPATTERN = COMMANDPATTERN;
        this.USAGE = USAGE;
        this.ARGUMENTS = getArguments(arguments,true);
    }

    /**
     *
     * @param name
     * @param COMMANDPATTERN
     * @param ARGUMENTS
     */
    public Command(String name, String COMMANDPATTERN, String ARGUMENTS) {
        this.COMMANDPATTERN = COMMANDPATTERN;
        this.ARGUMENTS = ARGUMENTS;
        this.NAME = name;
        this.ARGUMENTPATTERN = "";
        this.USAGE = "";
    }

    /**
     * Main action method. If the command arguments are valid the action() method will be called.
     * If the arguments are not valid an error message will be displayed and the program terminates.
     * @param bibliography
     */
    public void run(Bibliography bibliography){
        if (isValid()){
            action(bibliography);
        } else {
            LogManager.writeError(Constants.ERROR_INVALID_ARGUMENTS + "-" + NAME + "\n" + Constants.USSAGE + USAGE);
            System.exit(1);
        }
    }

    /**
     * The action will that will be performed if the arguments are valid. Enter the main command code here.
     * @param bibliography
     */
    protected abstract void action(Bibliography bibliography);

    /**
     * Check if the arguments a valid.
     * @return true if the argument matches the regular expression, false if not
     */
    protected boolean isValid(){
        return ARGUMENTPATTERN.isEmpty() || ARGUMENTS.matches(ARGUMENTPATTERN);
    }

    /**
     * Compile the standard command parameters. If not modified they are: type/key/match/value
     */
    void compileArgs(){
        Matcher m = Pattern.compile(Constants.REGEX_COMMAND_ARGUMENTS,Pattern.CASE_INSENSITIVE).matcher(ARGUMENTS);
        while (m.find()){
            if (m.group(1).matches(arg1)){
                getCommandTypes(types,m.group(2).trim(),readFileArg1);
            } else if (m.group(1).matches(arg2)){
                getCommandKeys(keys,m.group(2).trim(),readFileArg2);
            } else if (m.group(1).matches(arg3)){
                match = getArguments(m.group(2).trim(),readFileArg3).trim();
            } else if (m.group(1).matches(arg4)){
                value = getArguments(m.group(2).trim(),readFileArg4).trim();
            }
        }
    }

    /**
     * Adds every {@link KeyType} found in a file or String and adds it to a {@link Collection<KeyType>} of keys.
     * If a * was found, add every key.
     * @param k
     * @param arguments
     * @param readFiles
     * @since 0.19.13
     */
    protected void getCommandKeys(Collection<KeyType> k, String arguments, boolean readFiles){
        getCommandType(null,k,arguments,readFiles);
    }

    /**
     * Adds every {@link TypeType} found in a file or String and adds it to a {@link Collection<TypeType>} of types.
     * If a * was found, add every type.
     * @param t
     * @param arguments
     * @param readFiles
     * @since 0.19.13
     */
    protected void getCommandTypes(Collection<TypeType> t, String arguments, boolean readFiles){
        getCommandType(t,null,arguments,readFiles);
    }

    /**
     * Adds every Type found in a file or String and adds it to a {@link Collection<>} of the given typetypes.
     * If a * was found, add every type.
     * @param arguments
     * @param readFiles
     */
    private void getCommandType(Collection<TypeType> t, Collection<KeyType> k , String arguments, boolean readFiles) {
        boolean isType = (t != null);
        String args = getArguments(arguments,readFiles).trim();
        if (args.equals(Constants.UNLIMITEDCHARACTER)){
            if (isType){
                Collections.addAll(t, TypeType.values());
            } else {
                Collections.addAll(k, KeyType.values());
            }
        } else {
            if (args.startsWith(Constants.INVERTCHARACTER) && args.endsWith(Constants.INVERTCHARACTER)){
                args = args.substring(1,args.length()-1);
                if (isType){
                    for (TypeType typeType : TypeType.values()) {
                        if (args.contains(typeType.toString())){
                            continue;
                        }
                        t.add(typeType);
                    }
                } else {
                    for (KeyType keyType : KeyType.values()) {
                        if (args.contains(keyType.toString())){
                            continue;
                        }
                        k.add(keyType);
                    }
                }
            } else {
                if (isType){
                    for (String s : args.split(" ")) {
                        t.add(TypeType.valueOf(s.toUpperCase()));
                    }
                } else {
                    for (String s : args.split(" ")) {
                        k.add(KeyType.valueOf(s.toUpperCase()));
                    }
                }
            }
        }
    }

    /**
     * Returns the arguments if a matching parameter was found in the arguments String.
     * @param parameter the parameter to look for
     * @return the arguments, empty String if no matching parameter was found
     */
    protected String getArguments(String parameter){
        Matcher m = Pattern.compile(Constants.REGEX_COMMAND_ARGUMENTS,Pattern.CASE_INSENSITIVE).matcher(ARGUMENTS);
        while (m.find()){
            if (m.group(1).matches(parameter)){
                return m.group(2).trim();
            }
        }
        return "";
    }

    /**
     * Adds every argument found in a file or String to the arguments
     * @param args
     * @param readFiles
     * @return
     */
    protected String getArguments(String args, boolean readFiles) {
        return new File(args).exists() && readFiles ? Utils.getCollectionAsString(FileUtils.getFileContent(new File(args))) : args;
    }

    /**
     * Returns true if the value matches yes
     * @return true if the value matches yes, false if not
     */
    protected boolean isYes(){
        return value.matches(Constants.REGEX_YES);
    }
}
