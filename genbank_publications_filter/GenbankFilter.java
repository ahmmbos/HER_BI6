package genbank_publications_filter;

import java.util.List;
/**
 *      * Searches for a title and/or an author in the "TitleAuthorsMap" and "AuthorsTitlemap" hashmaps.
 *      * If a title is provided, it will search for the title in "TitleAuthorsMap" and print the values associated with the key.
 *      * If an author is provided in addition to a title, it will search for the title in "AuthorsTitleMap"and print the values associated with the key.
 *      * If no match is found, it will print "no matches found".
 * TODO: add head documentation of class "GenbankParser"
 */
public class GenbankFilter {
    static String strTitle = GenbankGUI.inputTitleField.getText();
    static String strAuthor = GenbankGUI.inputAuthorField.getText();
    /**
     * TODO: add "getTitle" method documentation
     */
    public static void getTitle() {
        if (!strTitle.equals("")) {
            if (GenbankGUI.titleToAuthor.containsKey(strTitle)) {
                List<String> values = GenbankGUI.titleToAuthor.get(strTitle);
                System.out.println("Values for key '" + strTitle + "': " + values);
            } else {
                System.out.println("no matches found");
            }
            if (!strAuthor.equals("")) {
                if (GenbankGUI.authorToTitle.containsKey(strTitle)) {
                    List<String> values = GenbankGUI.authorToTitle.get(strAuthor);
                    System.out.println("Values for key '" + strAuthor + "': " + values);
                } else {
                    System.out.println("no matches found");
                }
            }
        }
    }
    /** TODO: make method "getAuthor"
    public static void searchTitle() {
        if (!strTitle.equals("")) {
            if (GenbankGUI.titleToAuthor.containsKey(strTitle)) {
                List<String> values = GenbankGUI.titleToAuthor.get(strTitle);
                System.out.println("Values for key '" + strTitle + "': " + values);
            } else {
                System.out.println("no matches found");
            }
            if (!strAuthor.equals("")) {
                if (GenbankGUI.authorToTitle.containsKey(strTitle)) {
                    List<String> values = GenbankGUI.authorToTitle.get(strAuthor);
                    System.out.println("Values for key '" + strAuthor + "': " + values);
                } else {
                    System.out.println("no matches found");
                }
            }
        }
    }
     */
}