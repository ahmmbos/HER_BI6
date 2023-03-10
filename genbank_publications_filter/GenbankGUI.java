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
import java.util.*;
import java.util.List;

/**
 * TODO: add documentation header of class GUI
 */

public class GenbankGUI extends JFrame implements ActionListener {
     //Left panel that displays the text field, browse files button, and search buttons.
    private static JPanel leftPanel = new JPanel();
     //Panel that displays the information of the authors
    private static JPanel authorPanel = new JPanel();
     //Panel that displays the information of the titles
    static JPanel titlePanel = new JPanel();
    //input file reader
    private BufferedReader inFile;
    //text field to input the file name
    private JTextField nameField;
    //Text field to search for titles
    public static JTextField nameFieldSearchTitle;
    //text field to search for authors
    public static JTextField nameFieldSearchAuthor;
    //File chooser to select a file
    private JFileChooser fileChooser;
    //the content of the selected file
    public static String gbff = "";
    //Map that stores the titles and their corresponding authors
    public static Map<String, List<String>> TitleAuthorsMap = new HashMap<>();
    //Map that stores the authors and their corresponding titles
    public static Map<String, List<String>> AuthorsTitleMap = new HashMap<>();
    //Map that stores the titles and their corresponding accession numbers
    public static Map<String, List<String>> TitleAccessieMap = new HashMap<>();

    /**
     * Set up for the GUI
     * It contains the following panels: left panel, title panel and author panel
     * There are 2 buttons, txtFile and searchTitles/searchAuthors, wich have their own action listeners.
     * txtFile opens a pop-up window to write a txt file
     * searchTitles/searchAuthors searches for a title or author in the hashmaps.
     * the actionPerformed method opens a JFileChooser to select a file or directory
     * the b;aderen method reads the selected file or files in the selected directory
     * and stores the information in hashmaps
     * the information in the hashmaps is then displayed in the map table
     */
    void CreateGui() {

        //left panel
        leftPanel.setBackground(Color.GRAY);
        leftPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        leftPanel.setBounds(0,0,250,850);

        // browse files
        nameField = new JTextField(("file.gbff"));
        nameField.setColumns(15);
        leftPanel.add(nameField);
        JButton browseFiles = new JButton("Browse files");
        browseFiles.setBounds(100,50,150,50);
        browseFiles.addActionListener(this);
        leftPanel.add(browseFiles);

        //search title
        nameFieldSearchTitle = new JTextField("");
        nameFieldSearchTitle.setColumns(15);
        leftPanel.add(nameFieldSearchTitle);
        JButton searchTitles = new JButton("Search title");
        searchTitles.setBounds(100,100,150,50);
        leftPanel.add(searchTitles);

        //search authors
        nameFieldSearchAuthor = new JTextField("");
        nameFieldSearchAuthor.setColumns(15);
        leftPanel.add(nameFieldSearchAuthor);
        JButton searchAuthors = new JButton("Search Authors");
        searchAuthors.setBounds(100,150,150,50);
        leftPanel.add(searchAuthors);

        //make txt file
        JButton txtFile = new JButton("Make txt file");
        leftPanel.add(txtFile);

        // title panel
        titlePanel.setBackground(Color.lightGray);
        titlePanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        titlePanel.setBounds(250,0,425,850);

        // author panel
        authorPanel.setBackground(Color.lightGray);
        authorPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        authorPanel.setBounds(675,0,425,850);

        ActionListener writeTxt = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenbankWriter.pop_up();
            }
        };
        txtFile.addActionListener(writeTxt);
        ActionListener search = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenbankFilter.searchTitle();
            }
        };

        searchTitles.addActionListener(search);
        searchAuthors.addActionListener(search);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(1100,850);
        setTitle("TentamenOpdracht");
        setVisible(true);

        add(leftPanel);
        add(titlePanel);
        add(authorPanel);
    }
    public void actionPerformed(ActionEvent event) {
        File selectedFile;
        int reply;
        fileChooser = new JFileChooser("src/Files");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        reply = fileChooser.showOpenDialog(this);
        if (reply == 0) {
            selectedFile = fileChooser.getSelectedFile();
            nameField.setText(selectedFile.getAbsolutePath());
            if (selectedFile.isDirectory()) {
                File[] files = selectedFile.listFiles();
                for (File file : files) {
                    bladeren(file);
                }
            } else {
                bladeren(selectedFile);
            }
        }
        if (reply == 1) {
            JOptionPane.showMessageDialog(null, "No file selected", "error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void bladeren(File file) {
        try {
            inFile = new BufferedReader(new FileReader(file));
            String str = "";
            String line;
            int count = 0;
            while ((line = inFile.readLine()) != null && count != 5) {
                if (!line.startsWith("//")) {
                    str = "%s%s%n".format(str, line);
                } else if (line.startsWith("//")) {
                    System.out.println(count + ". Started reading file: " + file.getName());
                    gbff = str;
                    GenbankParser.TitleAccessieHash();
                    GenbankParser.TitleAuthorHash();
                    GenbankParser.AuthorTitleHash();
                    str = "";
                    gbff = "";
                    count++; //count om een limit van 5 aan te geven !!!!! verwijderen !!!!!
//                    for (Map.Entry<String, List<String>> entry : TitleAuthorsMap.entrySet()) {
//                        System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//                    }
                }
            }
            inFile.close();
            System.out.println(count + ". Finished reading file: " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "File error: " + e.toString());
        }
        JOptionPane.showMessageDialog(null, "File(s) have been succesfully been loaded.\nThey will appear on the right shortly.", "", JOptionPane.INFORMATION_MESSAGE);
        GuiMap();
    }

    public static void GuiMap() {
        titlePanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(titlePanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        for (Map.Entry<String, List<String>> entry : TitleAuthorsMap.entrySet()) {;
            JButton button = new JButton(entry.getKey());
            System.out.println(entry);
            titlePanel.add(button);
        }
    }
}

//TO-do
// 2. GUI verder uitbreiden: Autheurs -- Titles en aanklikbaar om te switchen
// 3. zoek functie: button voor zoeken naar autheurs en zoeken naar titels
// 4. wegschrijven van een bepaalde titel dmv "TitleAccessieHasmap" --> potentieel door start line van het bestand opteslaan om het terug te kunnen vinden