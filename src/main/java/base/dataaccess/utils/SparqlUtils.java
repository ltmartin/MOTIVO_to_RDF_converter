package base.dataaccess.utils;

import org.apache.commons.validator.routines.UrlValidator;

public class SparqlUtils {

    public static String getSparqlCompatibleString(String inputString) {
        if ((inputString.startsWith("http://")) || (inputString.startsWith("https://")))
            return "<" + inputString + ">" ;
        else
            return  "'" + inputString + "'";
    }

    public static String getCanonicalString(String inputString){
        inputString = inputString.replaceAll("<","");
        inputString = inputString.replaceAll(">","");
        inputString = inputString.replaceAll("'", "");
        inputString = inputString.replaceAll("\"", "");

        return inputString;
    }
}
