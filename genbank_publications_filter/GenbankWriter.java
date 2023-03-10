package genbank_publications_filter;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 * TODO: add head documentation of class GenbankParser
 */
public class GenbankWriter {
    /**
     * Class Write provides methods for exporting data to a text file.
     */
    static JFrame frame = new JFrame("Search to write");
    private static String textAuthor = "";
    private static String textTitle = "";

    /**
     * Shows a dialog to choose either the title and author or the whole genome to export.
     */
    //TODO: alternatief voor deze functie vinden dit kost te veel tijd voor de user
    public static void pop_up() {
        String[] options = {"Title + Autheur", "whole genome"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option: ", "Option Dialog", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            String[] options2 = {"Search on title", "Search on author"};
            int choice2 = JOptionPane.showOptionDialog(null, "Choose an option: ", "Option Dialog", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options[0]);
            if (choice2 == 0) {
                Container content = frame.getContentPane();
                content.setLayout(new FlowLayout());
                JTextField textField = new JTextField(20);
                content.add(textField);
                JOptionPane.showOptionDialog(frame, content, "Enter search", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                textTitle = textField.getText();
            } else if (choice2 == 1) {
                Container content = frame.getContentPane();
                content.setLayout(new FlowLayout());
                JTextField textField = new JTextField(20);
                content.add(textField);
                JOptionPane.showOptionDialog(frame, content, "Enter search", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                textAuthor = textField.getText();
            }
            WriteTxt();
        } else if (choice == 1) {
            WriteGenome();
        }
        System.out.println("you chose: " + options[choice]);
    }

    /**
     * Writes the title or author data to a text file.
     */
    //TODO: ipv zoektermen dmv op de knop van de class MapTable wegschrijfbaar te maken
    //TODO: als dit niet lukt dit laten werken.
    public static void WriteTxt() {
        if (textTitle != "") {
            List<String> values = GenbankGUI.TitleAuthorsMap.get(textTitle);
            if (values == null) {
                System.out.println("Search term not found");
                return;
            }
            try (FileWriter writer = new FileWriter("output.txt")) {
                for (String value : values) {
                    writer.write(value + "\n");
                }
                System.out.println("Data exported succesfully");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else if (textAuthor != "") {
            List<String> values = GenbankGUI.AuthorsTitleMap.get(textAuthor);
            if (values == null) {
                System.out.println("Search term not found");
                return;
            }
            try (FileWriter writer = new FileWriter("output.txt")) {
                for (String value : values) {
                    writer.write(value + "\n");
                }
                System.out.println("Data exported succesfully");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Writes the genome data to a text file.
     */
    //TODO: deze hele zooi dmv van de TitleAccessieMap (moet jammergenoeg dan wel nog eens door de files geloopt moeten worden
    public static void WriteGenome() {

    }
}