package com.therazzerapp.bibformatter;

import com.therazzerapp.bibformatter.bibliographie.Bibliographie;
import com.therazzerapp.bibformatter.bibliographie.Entry;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class Utils {

    public static final String entryOrder = "title shorttitle author year month day journal booktitle location on publisher address series volume number pages doi isbn issn url urldate copyright category note metadata";

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    public static String trim(String text){

        int openCounter = 0;
        int closeCounter = 0;
        for (char c : text.toCharArray()) {
            if (c == '{'){
                openCounter++;
            } else if (c == '}'){
                closeCounter++;
            }
        }

        while (openCounter>closeCounter){
            text = text.replaceFirst("\\{","");
            openCounter--;
        }
        while (closeCounter>openCounter){
            text = replaceLast(text,"}","");
            closeCounter--;
        }
        text = text.replaceFirst("\\{","");
        text = replaceLast(text,"}","");

        if (text.endsWith(",")){
            text = replaceLast(text,",","");
        }
        return text;
    }

    public static void debugEntries(Bibliographie bibliographie, String path){
        StringBuilder sb = new StringBuilder();
        for (Entry entry : bibliographie.getEntrieList()) {
            if (!entry.getKeys().keySet().contains("title")){
                sb.append("Missing Title \t\t" + entry.getBibtexkey() + "\n");
            }
            if (!entry.getKeys().keySet().contains("author")){
                sb.append("Missing Author \t\t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("year")){
                sb.append("Missing Year \t\t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("journal") && entry.getType().equals("article")){
                sb.append("Missing Journal \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("publisher") && entry.getType().equals("book")){
                sb.append("Missing Publisher \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("chapter") && entry.getType().equals("inbook")){
                sb.append("Missing Chapter \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("booktitle") && (entry.getType().equals("incollection") || entry.getType().equals("inproceedings"))){
                sb.append("Missing Book Title \t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("school") && entry.getType().equals("mastersthesis")){
                sb.append("Missing School \t\t" + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("institution") && entry.getType().equals("techreport")){
                sb.append("Missing Institution " + entry.getBibtexkey()+ "\n");
            }
            if (!entry.getKeys().keySet().contains("note") && entry.getType().equals("unpublished")){
                sb.append("Missing Note \t\t" + entry.getBibtexkey()+ "\n");
            }
        }
        FileManager.writeDebug(sb.toString(),path);
    }

    public static String getMonth(String month){
        if (month.equals("jan")|| month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]01[.][0-9]{1,}")){
            return "1";
        } else if (month.equals("feb") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]02[.][0-9]{1,}")){
            return "2";
        } else if (month.equals("mar") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]03[.][0-9]{1,}")){
            return "3";
        } else if (month.equals("apr") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]04[.][0-9]{1,}")){
            return "4";
        } else if (month.equals("may") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]05[.][0-9]{1,}")){
            return "5";
        } else if (month.equals("jun") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]06[.][0-9]{1,}")){
            return "6";
        } else if (month.equals("jul") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]07[.][0-9]{1,}")){
            return "7";
        } else if (month.equals("aug") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]08[.][0-9]{1,}")){
            return "8";
        } else if (month.equals("sep") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]09[.][0-9]{1,}")){
            return "9";
        } else if (month.equals("oct") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]10[.][0-9]{1,}")){
            return "10";
        } else if (month.equals("nov") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]11[.][0-9]{1,}")){
            return "11";
        } else if (month.equals("dec") || month.matches("[0-9]{0,}[.-]{0,}[0-9]{0,}[.]12[.][0-9]{1,}")){
            return "12";
        }
        return month;
    }
}
