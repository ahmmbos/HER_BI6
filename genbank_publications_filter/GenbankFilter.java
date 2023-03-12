package genbank_publications_filter;

import java.util.List;
import java.util.Map;

/**
 * <h1>Class GenbankFilter():</h1>
 * Class that filters trough the hashmaps {@see setHashMap()}. The search of the user is filtered through the HashMap.
 * Output is returned.
 */
public class GenbankFilter {
    public static String outputText;
    /**
     * <h3>Method getTitle()</h3>
     * <p>Method returns corresponding publication. Searched author {@see getSearchAuthor()} is filtered through
     * the HashMap {@see setHashMap()} and found publications are added to the outputText.</p>
     * @return -String- output text for the output textArea
     */
    public static String getTitle() {
        Map<String, String> authorToTitle = GenbankParser.getAuthorToTitle();
        String strAuthor = GenbankGUI.getSearchAuthor(); // Gets search name of an author
        outputText = ("The following publications were found for " + strAuthor + ":");
        if (authorToTitle.containsKey(strAuthor)) {
            outputText += ("\n" + authorToTitle.get(strAuthor));
        } else {
            outputText = "No publications found";
        }
        return outputText;
    }
    /**
     * <h3>Method getAuthor()</h3>
     * <p>Method returns corresponding author. Searched publication {@see getSearchTitle()} is filtered through
     * the HashMap {@see setHashMap()} and found author(s) are added to the outputText.</p>
     * @return -String- output text for the output textArea
     */
    public static String getAuthor() {
        Map<String, List<String>> titleToAuthor = GenbankParser.getTitleToAuthor();
        String strTitle = GenbankGUI.getSearchTitle(); // Gets search name of a publication
        outputText = ("The following author(s) were found for " + strTitle + ":");
        if (titleToAuthor.containsKey(strTitle)) {
            outputText += ("\n" + titleToAuthor.get(strTitle));
        } else {
            outputText = "No authors found";
        }
        return outputText;
    }
}