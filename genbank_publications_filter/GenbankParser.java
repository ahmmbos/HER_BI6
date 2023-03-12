package genbank_publications_filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>Class GenbankParser():</h1>
 * <p>Class that reads the .gbff-file {@link getContent()} and extracts the needed information {@link setHashMaps()}.
 * Authors and titles are places in HashMaps {@link getTitleToAuthor()}{@link getAuthorToTitle()}.</p>
 */
public class GenbankParser {
    // Hashmap -> Key: -String- Publication /\ Value: -List- Authors
    public static Map<String, List<String>> titleToAuthor = new HashMap<>();
    // Hashmap -> Key: -String- Author /\ Value: -String- Publication
    public static Map<String, String> authorToTitle = new HashMap<>();

    /**
     * <h3>Method getContent()</h3>
     * <p>Reads .gbff-file and puts whole content in a String.</p>
     * @return -String- content of the .gbff-file
     */
    public static String getContent() {
        BufferedReader br;
        String fileName = GenbankGUI.getFileName();
        StringBuilder gbffContent = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                gbffContent.append(line);
            }
        } catch (IOException e){
            //do something
        }
        return gbffContent.toString();
    }

    /**
     * <h3>Method setHashMap()</h3>
     * <p>Method that filters the content of the .gbff-file {@link getContent()}.
     * Regex pattern is used to find the authors and publications in the content with the matcher.
     * The extracted data is fine-tuned with regex. Then two HashMaps are made {@link getTitleToAuthor()}
     * {@link getAuthorToTitle()}</p>
     */
    public static void setHashMap() {
        String gbffContent = getContent();
        Pattern authorPattern = Pattern.compile("AUTHORS\\s+((.|\\n)*?)TITLE", Pattern.DOTALL);
        Pattern titlePattern = Pattern.compile("TITLE\\s+((.|\\n)*?)JOURNAL", Pattern.DOTALL);
        Matcher authorsMatcher = authorPattern.matcher(gbffContent);
        Matcher titleMatcher = titlePattern.matcher(gbffContent);
        if (titleMatcher.find() && authorsMatcher.find()) { //TODO: only finds first hit
            String authorsContent = authorsMatcher.group(1).replaceAll(",\\s+| and ", "");
            String strTitle = titleMatcher.group(1).trim().replace("           ", "");
            String[] strAuthors = authorsContent.split("\\s*\\.\\s*");
            // HashMap -> Key: Publication /\ Value: Author
            titleToAuthor.put(strTitle, Arrays.asList(strAuthors));
            // HashMap -> Key: Author /\ Value: Publication
            for (int i = 0; i + 1 < strAuthors.length; i++) {
                authorToTitle.put(strAuthors[i], strTitle);
            }
        } else {
            System.out.println("no match found: Title -- Authors"); //TODO: make ERROR pop-up [NOT PRIORITY]
        }
    }

    /**
     * <h3>Method getTitleToAuthor()</h3>
     * Returns Hashmap -> keys: publication /\ value: author {@link setHashMap()}.
     * @return -HashMap- titleToAuthor
     */
    public static Map<String, List<String>> getTitleToAuthor() {
        setHashMap();
        return titleToAuthor;
    }
    /**
     * <h3>Method getAuthorToTitle()</h3>
     * Returns Hashmap -> keys: author /\ value: publication {@link setHashMap()}.
     * @return -HashMap- authorToTitle
     */
    public static Map<String, String> getAuthorToTitle() {
        setHashMap();
        return authorToTitle;
    }
}