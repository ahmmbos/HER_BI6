package genbank_publications_filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: add head documentation of class "GenbankParser"
 */
public class GenbankParser {
    // Regex for finding author
    static final Pattern authorPattern = Pattern.compile("AUTHORS\\s+((.|\\n)*?)TITLE", Pattern.DOTALL);
    // Regex for finding title
    static final Pattern titlePattern = Pattern.compile("TITLE\\s+((.|\\n)*?)JOURNAL", Pattern.DOTALL);
    // Matcher for finding author
    static Matcher authorMatcher = authorPattern.matcher(GenbankGUI.gbffContent);
    // Matcher for finding title
    static Matcher titleMatcher = titlePattern.matcher(GenbankGUI.gbffContent);

    /**
     * TODO: add authorTitleHash method documentation
     */
    public static void AuthorTitleHash() {
        // make hashmap key: Author value: Title
        if (authorMatcher.find() && titleMatcher.find()) {
            String title = titleMatcher.group(1);
            String authorsString = authorMatcher.group(1).replaceAll(",\\s+", "");
            String[] authors = authorsString.split("\\s*\\.\\s*");
            for (String author : authors) {
                if (GenbankGUI.authorToTitle.containsKey(author)) {
                    List<String> tempTitle = GenbankGUI.authorToTitle.get(author);
                    if (!tempTitle.contains(title)) {
                        tempTitle.add(title);
                    }
                    GenbankGUI.authorToTitle.put(author, tempTitle);
                } else {
                    List<String> tempTitle = new ArrayList<>();
                    tempTitle.add(title);
                    GenbankGUI.authorToTitle.put(author, tempTitle);
                }
            }
        } else {
            System.out.println("no match found: Authors -- Title");
        }
    }

    /**
     * TODO: add titleAuthorHash method documentation
     */
        public static void TitleAuthorHash () {
        // make hashmap key: Title value: Authors
        if (titleMatcher.find() && authorMatcher.find()) {
            String authorsString = authorMatcher.group(1).replaceAll(",\\s+", "");
            String title = titleMatcher.group(1);
            List<String> tempAuthors;
            String[] authors = authorsString.split("\\s*\\.\\s*"); // array to individual
            if (GenbankGUI.titleToAuthor.containsKey(title)) {
                tempAuthors = GenbankGUI.titleToAuthor.get(title);
                for (String author : authors) {
                    if (!tempAuthors.contains(author)) {
                        tempAuthors.add(author);
                    }
                }
                GenbankGUI.titleToAuthor.put(title, tempAuthors);
            } else {
                tempAuthors = new ArrayList<>();
                tempAuthors.add(Arrays.toString(authors));
                GenbankGUI.titleToAuthor.put(title, tempAuthors);
            }
        } else {
            System.out.println("no match found: Title -- Authors");
        }
    }
}