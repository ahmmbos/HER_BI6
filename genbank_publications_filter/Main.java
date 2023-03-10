package genbank_publications_filter;
/**
 * <h1>Resit bioinformatics final exam (course 6)</h1>
 * <p>Application that reads (.gbff)-files from Genbank and lets the user search
 * through authors and/or publications. Authors and publications are connected:
 * the application lets the user "surf" through the authors and publications.</p>
 *
 * The application in a nutshell:<br>
 * 1. Read in the files (.gbff)<br>
 * 2. Make it possible to search for an author in the list<br>
 * 3. Make it possible to go to publication via author (and vice versa)<br>
 * 4. Make it possible to select an item and write all associated data from it, to a file<br>
 *
 * @author Amber Bos
 * @since 10-03-2023
*/
public class Main {
    public static void main(String[] args) {
        GenbankGUI gui = new GenbankGUI();
        gui.CreateGui();
    }
}
