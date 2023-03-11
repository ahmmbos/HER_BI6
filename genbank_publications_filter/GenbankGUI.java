package genbank_publications_filter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
 * TODO: add documentation header of class GUI
 */

public class GenbankGUI extends JFrame implements ActionListener {
    public JPanel menuPanel, authorPanel, titlePanel;
    public JTextField inputFileField, inputTitleField, inputAuthorField;
    // The content of the selected file
    public static String gbffContent = "";
    public JButton searchTitleButton, searchAuthorButton, saveButton, fileButton;
    // Hashmap that stores the titles and their corresponding authors
    public static Map<String, List<String>> titleToAuthor = new HashMap<>();
    // Hashmap that stores the authors and their corresponding titles
    public static Map<String, List<String>> authorToTitle = new HashMap<>();

    /**
     * TODO: add createGUI method documentation
     */
    void createGui() {
        // Left panel
        menuPanel.setBackground(Color.GRAY);
        menuPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        menuPanel.setBounds(0,0,250,850);

        // Browse files
        inputFileField = new JTextField(("file.gbff"));
        inputFileField.setColumns(15);
        menuPanel.add(inputFileField);
        JButton browseFiles = new JButton("Browse files");
        browseFiles.setBounds(100,50,150,50);
        browseFiles.addActionListener(this);
        menuPanel.add(browseFiles);

        // Search title
        inputTitleField = new JTextField("");
        inputTitleField.setColumns(15);
        menuPanel.add(inputTitleField);
        JButton searchTitles = new JButton("Search Title");
        searchTitles.setBounds(100,100,150,50);
        menuPanel.add(searchTitles);

        // Search authors
        inputAuthorField = new JTextField("");
        inputAuthorField.setColumns(15);
        menuPanel.add(inputAuthorField);
        JButton searchAuthors = new JButton("Search Author");
        searchAuthors.setBounds(100,150,150,50);
        menuPanel.add(searchAuthors);

        // Make text file
        JButton txtFile = new JButton("Make txt file");
        menuPanel.add(txtFile);

        // Title panel
        titlePanel.setBackground(Color.lightGray);
        titlePanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        titlePanel.setBounds(250,0,425,850);

        // Author panel
        authorPanel.setBackground(Color.lightGray);
        authorPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        authorPanel.setBounds(675,0,425,850);

        ActionListener writeTxt = e -> GenbankWriter.pop_up();
        txtFile.addActionListener(writeTxt);
        ActionListener search = e -> GenbankFilter.getTitle();

        searchTitles.addActionListener(search);
        searchAuthors.addActionListener(search);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(1100,850);
        setTitle("Genbank Publications Filter");
        setVisible(true);

        add(menuPanel);
        add(titlePanel);
        add(authorPanel);
    }
    /**
     * TODO: add "actionPerformed" method documentation
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
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
            // do something
            // GenbankWriter().getText()
            // GenbankWriter().writeText()
        }
        if (e.getSource() == fileButton) {
            // do something
            // getFile()
        }
    }

    /** searchTitleButton, searchAuthorButton, saveButton, fileButton
    public void actionPerformed(ActionEvent event) { // TODO: change "actionPerformed"
        File selectedFile;
        int reply;
        //File chooser to select a file
        JFileChooser fileChooser = new JFileChooser("src/Files");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        reply = fileChooser.showOpenDialog(this);
        if (reply == 0) {
            selectedFile = fileChooser.getSelectedFile();
            inputFileField.setText(selectedFile.getAbsolutePath());
            if (selectedFile.isDirectory()) {
                File[] files = selectedFile.listFiles();
                assert files != null;
                for (File file : files) {
                    getFile(file);
                }
            } else {
                getFile(selectedFile);
            }
        }
        if (reply == 1) {
            JOptionPane.showMessageDialog(null, "No file selected", "error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
     */
    /**
     * TODO: add getFile method documentation
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
        GUIMap();
    }

    /**
     * TODO: delete this
     */
    public void GUIMap() {
        titlePanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(titlePanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        for (Map.Entry<String, List<String>> entry : titleToAuthor.entrySet()) {
            JButton button = new JButton(entry.getKey());
            System.out.println(entry);
            titlePanel.add(button);
        }
    }
}