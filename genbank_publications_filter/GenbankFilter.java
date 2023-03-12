package genbank_publications_filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *      * Searches for a title and/or an author in the "TitleAuthorsMap" and "AuthorsTitlemap" hashmaps.
 *      * If a title is provided, it will search for the title in "TitleAuthorsMap" and print the values associated with the key.
 *      * If an author is provided in addition to a title, it will search for the title in "AuthorsTitleMap"and print the values associated with the key.
 *      * If no match is found, it will print "no matches found".
 * TODO: add head documentation of class "GenbankParser"
 */
public class GenbankFilter {
    public static String outputText;
    /**
     * TODO: add "getTitle" method documentation
     */
    public static String getTitle() { //TODO: change method
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
    // TODO: make method "getAuthor"
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