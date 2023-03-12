package genbank_publications_filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *      * Set up for the GUI
 *      * It contains the following panels: left panel, title panel and author panel
 *      * There are 2 buttons, txtFile and searchTitles/searchAuthors, which have their own action listeners.
 *      * txtFile opens a pop-up window to write a txt file
 *      * searchTitles/searchAuthors searches for a title or author in the hashmaps.
 *      * the actionPerformed method opens a JFileChooser to select a file or directory
 *      * the getFile() method reads the selected file or files in the selected directory
 *      * and stores the information in hashmaps
 *      * the information in the hashmaps is then displayed in the map table
 * TODO: add documentation header of class "GenbankGUI"
 */

public class GenbankGUI extends JFrame {
    public JTextArea outputArea;
    public JTextField fileInputField, authorInputField, titleInputField;
    public JLabel fileLabel, authorLabel, titleLabel;
    public JButton fileButton, searchTitleButton, searchAuthorButton, saveButton;
    // The content of the selected file
    public static String gbffContent = "";
    // Hashmap that stores the titles and their corresponding authors
    public static Map<String, List<String>> titleToAuthor = new HashMap<>();
    // Hashmap that stores the authors and their corresponding titles
    public static Map<String, List<String>> authorToTitle = new HashMap<>();

    /**
     * TODO: add "createGUI" method documentation
     */
    public void createGui() {
        // Set start layout:
        JFrame frame = new JFrame("Genbank File Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPanel = frame.getContentPane();
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        // Contents:
        outputArea = new JTextArea("");
        outputArea.setEditable(false);
        fileInputField = new JTextField("example.gbff");
        authorInputField = new JTextField("");
        titleInputField = new JTextField("");
        fileLabel = new JLabel("Select a gbff-file:");
        authorLabel = new JLabel("Search publications of a author:");
        titleLabel = new JLabel("Search authors of a publication:");
        fileButton = new JButton("Select file");
        fileButton.addActionListener(new buttonListener());
        searchTitleButton = new JButton("Search publication(s)");
        searchTitleButton.addActionListener(new buttonListener());
        searchAuthorButton = new JButton("Search author(s)");
        searchAuthorButton.addActionListener(new buttonListener());
        saveButton = new JButton("Save results");
        saveButton.addActionListener(new buttonListener());
        // Set horizontal layout:
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(fileLabel)
                                                .addComponent(fileInputField)
                                                .addComponent(fileButton)
                                                .addComponent(authorLabel)
                                                .addComponent(authorInputField)
                                                .addComponent(searchTitleButton)
                                                .addComponent(titleLabel)
                                                .addComponent(titleInputField)
                                                .addComponent(searchAuthorButton)
                                                .addComponent(saveButton))
                                        .addComponent(outputArea))));
        layout.linkSize(SwingConstants.HORIZONTAL, fileLabel, fileInputField, fileButton,
                authorLabel, authorInputField, searchTitleButton,
                titleLabel, titleInputField, searchAuthorButton,
                saveButton);
        // Set vertical layout:
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(fileLabel)
                                        .addComponent(fileInputField)
                                        .addComponent(fileButton)
                                        .addComponent(authorLabel)
                                        .addComponent(authorInputField)
                                        .addComponent(searchTitleButton)
                                        .addComponent(titleLabel)
                                        .addComponent(titleInputField)
                                        .addComponent(searchAuthorButton)
                                        .addComponent(saveButton))
                                .addComponent(outputArea)));
        layout.linkSize(SwingConstants.VERTICAL, fileInputField, fileButton,
                authorInputField, searchTitleButton,
                titleInputField, searchAuthorButton,
                saveButton);
        // Open GUI:
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * TODO: add getFile method documentation
     *
     * @param file
     */
    private void getFile(File file) {
        try {
            //input file reader
            BufferedReader inFile = new BufferedReader(new FileReader(file));
            String str = "";
            String line;
            int count = 0;
            while ((line = inFile.readLine()) != null && count != 5) {
                if (!line.startsWith("//")) { // TODO: rewrite if-statement
                    str = String.format(str, line);
                } else if (line.startsWith("//")) {
                    System.out.println(count + ". Started reading file: " + file.getName());
                    gbffContent = str;
                    GenbankParser.TitleAuthorHash();
                    GenbankParser.AuthorTitleHash();
                    str = "";
                    gbffContent = "";
                    count++;
                }
            }
            inFile.close();
            System.out.println(count + ". Finished reading file: " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "File error: " + e);
        }
        JOptionPane.showMessageDialog(null,
                "File(s) have been successfully been loaded.\nThey will appear on the right shortly.",
                "",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private class buttonListener implements ActionListener { //TODO: make new .class of buttonListener [NOT PRIORITY]
        /**
         * TODO: add "actionPerformed" method documentation
         *
         * @param event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == fileButton) {
                // do something
                // getFile()
            }
            if (e.getSource() == searchTitleButton) {
                // do something
                // GenbankParser.getContent()
                // GenbankFilter.getTitle()
            }
            if (e.getSource() == searchAuthorButton) {
                // do something
                // GenbankParser.getContent()
                // GenbankFilter.getAuthor()
            }
            if (e.getSource() == saveButton) {
                String saveText = outputArea.getText();
                String saveName = JOptionPane.showInputDialog("Enter file name",
                        ".txt");
                try {
                    File newFile = new File(saveName);
                    if (newFile.createNewFile()) {
                        FileWriter myWriter = new FileWriter(saveName);
                        myWriter.write(saveText);
                        myWriter.close();
                        JOptionPane.showMessageDialog(null,
                                "File has been saved",
                                "File saved",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "File already exists",
                                "ERROR",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,
                            "An error occurred",
                            "ERROR",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}