package com.therazzerapp.bibformatter.gui;

import com.therazzerapp.bibformatter.Constants;
import com.therazzerapp.bibformatter.bibliographie.Bibliography;
import com.therazzerapp.bibformatter.bibliographie.Entry;
import com.therazzerapp.bibformatter.content.saver.BibSaver;
import com.therazzerapp.bibformatter.filefilter.BIBFilter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <description>
 *
 * @author Paul Eduard Koenig <s6604582@stud.uni-frankfurt.de>
 * @since <VERSION>
 */
public class StartUp implements Runnable{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel toolsPanel;
    private JPanel tablePanel;
    private JPanel infoPanel;
    private JTextArea sourceTextArea;
    private JTabbedPane tabbedPane2;
    private JTextArea rawCurrentTextArea;
    private JTabbedPane tabbedPane3;
    private JList toolsList;
    private JButton applyButton;
    private JPanel tableCurrentPain;
    private JTabbedPane tablePain;
    private JPanel tableOriginalPane;
    private JList tableCurrentList;
    private JList tableOriginalList;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JButton addButton;
    private JTextField textField1;
    private JButton removeButton;
    private JFileChooser bibChooser;
    private JFileChooser fileSaver;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;

    private Bibliography bibliographyOld;
    private Bibliography bibliographyNew;

    public Bibliography getBibliographyNew() {
        return bibliographyNew;
    }

    public void setBibliographyNew(Bibliography bibliographyNew) {
        this.bibliographyNew = bibliographyNew;
        updateBib();
    }

    private void updateBib(){
        DefaultListModel tableListModel = new DefaultListModel();
        for (Entry entry : bibliographyNew.getEntryList()) {
            rawCurrentTextArea.append(entry.getRawEntry());
            rawCurrentTextArea.setCaretPosition(0);
            tableListModel.addElement(entry.getBibtexkey());
        }
        tableCurrentList.setModel(tableListModel);
    }

    public Bibliography getBibliographyOld() {
        return bibliographyOld;
    }

    public void run(){
        createAndShowUI();
    }

    private void createAndShowUI(){
        JFrame frame = new JFrame("StartUp");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setContentPane(mainPanel);
        frame.setSize(900,600);
        frame.setTitle("BibFormatter " + Constants.VERSION);
        frame.setLocationRelativeTo(null);

        tabbedPane2.setEnabled(false);
        tabbedPane1.setEnabled(false);
        tablePain.setEnabled(false);

        bibChooser = new JFileChooser();
        bibChooser.setDialogTitle("Choose BIB File");
        bibChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        bibChooser.setFileFilter(new BIBFilter());
        bibChooser.setAcceptAllFileFilterUsed(false);

        fileSaver = new JFileChooser();
        fileSaver.setDialogTitle("Choose Directory");
        fileSaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //File Menu
        JMenu jMenuFile = new JMenu("File");
        menuBar.add(jMenuFile);

            menuOpen = new JMenuItem("Open File");
            jMenuFile.add(menuOpen);

            menuSave = new JMenuItem("Save File");
            jMenuFile.add(menuSave);
            menuSave.setEnabled(false);

        //Edit Menu
        JMenu jMenuEdit = new JMenu("Edit");
        menuBar.add(jMenuEdit);

        //Help Menu
        JMenu jMenuHelp = new JMenu("Help");
        menuBar.add(jMenuHelp);

        initComponents(frame);

        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Page Number");
        listModel.addElement("Capitalization");
        toolsList.setModel(listModel);




    }

    private void initComponents(final JFrame frame){
        menuOpen.addActionListener(e -> {
            if (bibChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                tabbedPane1.setEnabled(true);
                tablePain.setEnabled(true);
                //bibliographieOld = BibLoader.load(bibChooser.getSelectedFile());
                setBibliographyNew(bibliographyOld);
                menuSave.setEnabled(true);

                DefaultListModel tableOriginalListModel = new DefaultListModel();
                for (Entry entry : bibliographyOld.getEntryList()) {
                    sourceTextArea.append(entry.getRawEntry());
                    sourceTextArea.setCaretPosition(0);
                    tableOriginalListModel.addElement(entry.getBibtexkey());
                }
                tableOriginalList.setModel(tableOriginalListModel);
            }
        });
        menuSave.addActionListener(e -> {
            if (fileSaver.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
                BibSaver.save(bibliographyNew,fileSaver.getSelectedFile().getPath()); //todo Fix correct path + file name
            }
        });
        tabbedPane1.addChangeListener(e -> {
            if (tabbedPane1.isEnabled()){
                tabbedPane2.setEnabled(true);
            } else {
                tabbedPane2.setEnabled(false);
            }
        });
        toolsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
    }
}
