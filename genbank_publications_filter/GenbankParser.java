package genbank_publications_filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: add head documentation of class "GenbankParser"
 */
public class GenbankParser {
    public static Map<String, List<String>> titleToAuthor = new HashMap<>();
    public static Map<String, String> authorToTitle = new HashMap<>();

    /**
     * TODO: add "getContent" method documentation
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
     * TODO: add "setHashMap" method documentation
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
            titleToAuthor.put(strTitle, Arrays.asList(strAuthors));
            for (int i = 0; i + 1 < strAuthors.length; i++) {
                authorToTitle.put(strAuthors[i], strTitle);
            }
        } else {
            System.out.println("no match found: Title -- Authors");
        }
    }
    public static Map<String, List<String>> getTitleToAuthor() {
        setHashMap();
        return titleToAuthor;
    }

    public static Map<String, String> getAuthorToTitle() {
        setHashMap();
        return authorToTitle;
    }
}