package genbank_publications_filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * <h1>Class GenbankGUI:</h1>
 * <p>Class that opens the GUI and retrieves the information and queries desired by the user via the buttons.
 * This class responds to four different buttons:<br>
 * - fileButton: a filepath is retrieved and the chosen filepath is dropped in the fileInputField.<br>
 * - searchAuthorButton: in the search field a publication is placed by the user. The selected file is
 * searched for associated authors. The found authors will be displayed in the outputArea.<br>
 * - searchTitleButton: an author is dropped in the search field by the user.
 * The prepared file is searched for associated publications. The found publications will be displayed in
 * the outputArea. <br>
 * - saveButton: the text in the outputArea is saved in a file.<br></p>
 */

public class GenbankGUI extends JFrame {
    // TextArea for the output:
    public JTextArea outputArea;
    // Fields for user input: {@see getFileName, getSearchAuthor, getSearchTitle}
    public static JTextField fileInputField, authorInputField, titleInputField;
    // Labels for an user friendly GUI:
    public JLabel fileLabel, authorLabel, titleLabel;
    // Buttons in GUI: {@see buttonLister}
    public JButton fileButton, searchTitleButton, searchAuthorButton, saveButton;

    /**
     * <h3>Method createGUI():</h3>
     * <p>Method that makes and opens the GUI. GUI has a menu on the left side and an outputArea on the right.
     * fileInputField has a start value "example.gbff" {@see getFileName()}.
     * authorInputField has a start value "Kuwahara,T" for faster debugging.</p>
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
        fileInputField = new JTextField("example.gbff"); // {@see getFileName}
        authorInputField = new JTextField("Kuwahara,T"); // Example author
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
        // Link the sizes horizontal for a pretty GUI:
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
        // Link the sizes vertical for a pretty GUI:
        layout.linkSize(SwingConstants.VERTICAL, fileInputField, fileButton, authorInputField, searchTitleButton,
                titleInputField, searchAuthorButton, saveButton);
        // Open GUI:
        frame.setPreferredSize(new Dimension(800, 400)); // Not a special reason for this size
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * <h3>Method getFile()</h3>
     * <p>Method lets user open its documents and browse through files. Chosen file will be saved by saving the
     * document path name as a string.</p>
     * @return -String- document path name
     */
    public String getFile() {
        String pathName = "";
        File selectedFile;
        int reply;
        JFileChooser fileChooser = new JFileChooser();
        reply = fileChooser.showOpenDialog(null);
        if (reply == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            pathName = selectedFile.getAbsolutePath();
        }
        return pathName;
    }

    /**
     * <h3>Method getFileName()</h3>
     * <p>Method that returns the file name (same as the document path name) {@see getFile()}. If the fileInputField
     * has "example.gbff" as input, then a saved document path name will be used. Reason is for fast debugging.</p>
     * @return -String- text of fileInputField
     */
    public static String getFileName() {
        String fileName;
        // Added example.gbff for easy and faster testing
        if (fileInputField.getText().equals("example.gbff")) {
            fileName = "/home/tardigrada/Documents/bio-informatica_leerjaar2/informatica/periode2/BI6a_Kans1_ABos/sample.gbff";
        } else {
            fileName = fileInputField.getText();
        }
        return fileName;
    }
    /**
     * <h3>Method getSearchAuthor</h3>
     * <p>Method that returns the user search input of an author.</p>
     * @return -String- text of authorInputField
     */
    public static String getSearchAuthor() {
        return authorInputField.getText();
    }
    /**
     * <h3>Method getSearchTitle()</h3>
     * <p>Method that returns the user search input of a publication title.</p>
     * @return -string- text of titleInputField
     */
    public static String getSearchTitle() {
        return titleInputField.getText();
    }

    /**
     * <h1>Class buttonListener:</h1>
     * - fileButton: a filepath is retrieved and the chosen filepath is dropped in the fileInputField.<br>
     * - searchAuthorButton: in the search field a publication is placed by the user. The selected file is
     * searched for associated authors. The found authors will be displayed in the outputArea.<br>
     * - searchTitleButton: an author is dropped in the search field by the user.
     * The prepared file is searched for associated publications. The found publications will be displayed in
     * the outputArea. <br>
     * - saveButton: the text in the outputArea is saved in a file.<br></p>
     */
    private class buttonListener implements ActionListener { //TODO: make new .class of buttonListener [NOT PRIORITY]
        @Override
        public void actionPerformed(ActionEvent e) {
            /*
              fileButton: Sets filename into the input field by letting the user chose the file.
              @link getFile() -> get file path name
             */
            if (e.getSource() == fileButton) {
                String fileName = getFile();
                fileInputField.setText(fileName);
            }
            /*
              searchTitleButton: Lets the user search in file for the corresponding publications. The authorInputField
              needs to be not empty, or an ERROR will occur.
              @link getSearchAuthor() -> get user search input of author
              @link getTitle() -> get found corresponding publications
            */
            if (e.getSource() == searchTitleButton) {
                String strAuthor = getSearchAuthor();
                outputArea.setText(""); // Reset output area
                if (!strAuthor.equals("")) {
                    String outputText = GenbankFilter.getTitle();
                    outputArea.setText(outputText);
                } else {
                    JOptionPane.showMessageDialog(null,  // Search field is empty
                            "Search field is empty",
                            "ERROR",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            /*
              searchAuthorButton: Lets the user search in file for the corresponding authors. The titleInputField
              needs to be not empty, or an ERROR will occur.
              @link getSearchTitle() -> get user search input of title
              @link getAuthor() -> get found corresponding author(s)
            */
            if (e.getSource() == searchAuthorButton) {
                String strTitle = getSearchTitle();
                outputArea.setText(""); // Reset output area
                if (!strTitle.equals("")) {
                    String outputText = GenbankFilter.getAuthor();
                    outputArea.setText(outputText);
                } else {
                    JOptionPane.showMessageDialog(null, // Search field is empty
                            "Search field is empty",
                            "ERROR",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            /*
              saveButton: lets user chose the name of a new .txt-file. File will contain the current outputArea text.
            */
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
                        JOptionPane.showMessageDialog(null, // File has been saved
                                "File has been saved",
                                "File saved",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, // File name already exists
                                "File already exists",
                                "ERROR",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, // Unknown ERROR occurred
                            "An error occurred",
                            "ERROR",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}