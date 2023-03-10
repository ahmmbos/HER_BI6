package genbank_publications_filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: add head documentation of class GenbankParser
 */
public class GenbankParser {
    static Pattern regAuthorsPattern = Pattern.compile("AUTHORS\\s+((.|\\n)*?)TITLE", Pattern.DOTALL);    // regex for finding Authors
    static Pattern regTitlePattern = Pattern.compile("TITLE\\s+((.|\\n)*?)JOURNAL", Pattern.DOTALL);    // regex for finding Tiitle
    static Pattern regAccessiePatern = Pattern.compile("ACCESSION\\s+([A-Z0-9]+)", Pattern.DOTALL);    // regex for finding Accessie

    public static void AuthorTitleHash() {
        Matcher regAuthorsMatcher = regAuthorsPattern.matcher(GenbankGUI.gbff);
        Matcher regTitleMatcher = regTitlePattern.matcher(GenbankGUI.gbff);
        // make hashmap key: Author value: Title
        if (regAuthorsMatcher.find() && regTitleMatcher.find()) {
            String title = regTitleMatcher.group(1);
            String authorsString = regAuthorsMatcher.group(1).replaceAll(",\\s+", "");
            String[] authors = authorsString.split("\\s*\\.\\s*");
            List<String> tempTitle;
            for (String author : authors) {
                if (GenbankGUI.AuthorsTitleMap.containsKey(author)) {
                    tempTitle = GenbankGUI.AuthorsTitleMap.get(author);
                    if (!tempTitle.contains(title)) {
                        tempTitle.add(title);
                    }
                    GenbankGUI.AuthorsTitleMap.put(author, tempTitle);
                } else {
                    tempTitle = new ArrayList<>();
                    tempTitle.add(title);
                    GenbankGUI.AuthorsTitleMap.put(author, tempTitle);
                }
            }
        } else {
            System.out.println("no match found: Authors -- Title");
        }
    }

    public static void TitleAccessieHash () {
        Matcher regTitleMatcher = regTitlePattern.matcher(GenbankGUI.gbff);
        Matcher regAccessieMatcher = regAccessiePatern.matcher(GenbankGUI.gbff);
        // make hashmap key: Titel value: Accessie
        if (regTitleMatcher.find() && regAccessieMatcher.find()) {
            String title = regTitleMatcher.group(1);
            String accession = regAccessieMatcher.group(1);
            List<String> tempAccession;
            if (GenbankGUI.TitleAccessieMap.containsKey(title)) {
                tempAccession = GenbankGUI.TitleAccessieMap.get(title);
                tempAccession.add(accession);
                GenbankGUI.TitleAccessieMap.put(title, tempAccession);
            } else {
                tempAccession = new ArrayList<>();
                tempAccession.add(accession);
                GenbankGUI.TitleAccessieMap.put(title, tempAccession);
            }
        } else {
            System.out.println("no match found: Title -- Accessie");
        }
    }
        public static void TitleAuthorHash () {
        Matcher regAuthorsMatcher = regAuthorsPattern.matcher(GenbankGUI.gbff);
        Matcher regTitleMatcher = regTitlePattern.matcher(GenbankGUI.gbff);
        // make hashmap key: Title value: Authors
        if (regTitleMatcher.find() && regAuthorsMatcher.find()) {
            String authorsString = regAuthorsMatcher.group(1).replaceAll(",\\s+", "");
            String title = regTitleMatcher.group(1);
            List<String> tempAuthors;
            String[] authors = authorsString.split("\\s*\\.\\s*"); // array to individual
            if (GenbankGUI.TitleAuthorsMap.containsKey(title)) {
                tempAuthors = GenbankGUI.TitleAuthorsMap.get(title);
                for (String author : authors) {
                    if (!tempAuthors.contains(author)) {
                        tempAuthors.add(author);
                    }
                }
                GenbankGUI.TitleAuthorsMap.put(title, tempAuthors);
            } else {
                tempAuthors = new ArrayList<>();
                tempAuthors.add(Arrays.toString(authors));
                GenbankGUI.TitleAuthorsMap.put(title, tempAuthors);
            }
        } else {
            System.out.println("no match found: Title -- Authors");
        }
    }
}