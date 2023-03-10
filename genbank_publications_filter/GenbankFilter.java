package genbank_publications_filter;

import java.util.List;
/**
 * TODO: add head documentation of class GenbankParser
 */
public class GenbankFilter {
    /**
     * Searches for a title and/or an author in the "TitleAuthorsMap" and "AuthorsTitlemap" hashmaps.
     * If a title is provided, it will search for the title in "TitleAuthorsMap" and print the values assosiated with the key.
     * If an author is provided in addition to a title, it will search for the title in "AuthorsTitleMap"and print the values assosiated with the key.
     * If no match is found, it will print "no matches found".
     */
    //TODO: laat er echt mathes gevonden kunnen worden.
    public static void searchTitle() {
        String searchTextTitle = GenbankGUI.nameFieldSearchTitle.getText();
        String searchTextAuthor = GenbankGUI.nameFieldSearchAuthor.getText();
        if (searchTextTitle != "") {
            if (GenbankGUI.TitleAuthorsMap.containsKey(searchTextTitle)) {
                List<String> values = GenbankGUI.TitleAuthorsMap.get(searchTextTitle);
                System.out.println("Values for key '" + searchTextTitle + "': " + values);
            } else {
                System.out.println("no mathes found");
            }
            if (searchTextAuthor != "") {
                if (GenbankGUI.AuthorsTitleMap.containsKey(searchTextTitle)) {
                    List<String> values = GenbankGUI.AuthorsTitleMap.get(searchTextTitle);
                    System.out.println("Values for key '" + searchTextTitle + "': " + values);
                } else {
                    System.out.println("no mathes found");
                }
            }
        }
    }
}