package base.dataaccess.utils;

import org.apache.commons.validator.routines.UrlValidator;

public class SparqlUtils {

    public static String getSparqlCompatibleString(String inputString) {
        return UrlValidator.getInstance().isValid(inputString) ? "<" + inputString + ">" : "'" + inputString + "'";
    }
}
