/**
 * File: GUICardLayout.java
 * This class contains the GUI for creating and modifying a database.
 * @author Tarek Solamy (Alsolame)
 * @version 2.0 10/30/2022
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Gui card layout.
 */
public class GUICardLayout {
    private JPanel NewLoadPanel;
    private JButton New;
    private JButton Load;
    private JPanel bodyLayout;
    private JPanel databasePanel;
    private JPanel titlePanel;
    private JButton saveButton;
    private JPanel methodsPanel;
    private JButton insertNewPersonButton;
    private JButton lookUpByCityButton;
    private JButton lookUpByRaceButton;
    private JButton lookUpByReligionButton;
    private JButton displayAllButton;
    private JButton lookUpByCountryButton;
    private JPanel executionsPanel;
    private JPanel displayAllCard;
    private JPanel countryCard;
    private JPanel cityCard;
    private JPanel raceCard;
    private JPanel religionCard;
    private JPanel startUpCard;
    private JPanel mainPanel;
    private JButton displayAllDatabasesButton;
    private JPanel loadAllDatabases;
    private JList databasesList;
    private JLabel databaseTitle;
    private JPanel emptyBox;
    private JTable displayTable;
    private JScrollPane displayScroll;
    private JTextField DisplayAllSearchField;
    private JLabel DisplayAllSearchLabel;
    private JTextField CountryCardSearch;
    private JTable countryDisplay;
    private JScrollPane countryScroll;
    private JTextField CityCardSearch;
    private JTable cityDisplay;
    private JScrollPane cityScroll;
    private JTextField raceCardSearch;
    private JTable raceDisplay;
    private JTextField religionSearchCard;
    private JTable religionDisplay;
    private JScrollPane raceScroll;
    private JScrollPane religionScroll;
    private JLabel CountryCardSearchLabel;
    private JLabel cityCardSearchLabel;
    private JLabel raceCardSearchLabel;
    private JLabel ReligionCardSearchLabel;
    private JTextField databaseSearch;

    private String databaseName;
    private Database workingDatabase;
    private Database tempDatabase;


    private DefaultListModel<ListPersons> model;
    private DefaultListModel<String> listModel;
    private JPopupMenu popupMenu;
    private JMenuItem jmi1, jmi2, jmi3; // for display databases pop up menu
    private ArrayList<String[]> starting;
    private String[][] viewingData;
    private DefaultTableModel viewElementsModel;
    private JPopupMenu popupMenuDisplay;
    private Person toUpdate;
    private Person toRemove;
    private int currentRowTake;
    private String tempName;
    private DefaultTableModel tempModel;
    private MouseEvent popup; // for display database popup menu
    private boolean loadedFile;
    private boolean databasePanelUsed = false;


    /**
     * Instantiates a new Gui card layout.
     */
    public GUICardLayout() {

        New.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = JOptionPane.showInputDialog(null, "Please enter the name of your new database.");
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: No name provided", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    databasePanelUsed = true;
                    if (Database.createNewFile(name)) {
                        workingDatabase = new Database(name);
                        bodyLayout.removeAll();
                        bodyLayout.add(databasePanel);
                        bodyLayout.repaint();
                        bodyLayout.revalidate();
                        databaseTitle.setText(name);
                        databasePanelUsed = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "A database with this name already exists. " +
                                "please enter another name", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        Load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(null, "Please enter the name of the database.");
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: No name provided", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                executionsPanel.removeAll();
                executionsPanel.repaint();
                executionsPanel.revalidate();

                workingDatabase = new Database(name);
                //System.out.println(name);
                try {
                    databasePanelUsed = true;
                    loadedFile = true;
                    if (!workingDatabase.load(name)){
                        JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: The File Doesn't exist", JOptionPane.ERROR_MESSAGE);
                    } else {
                        bodyLayout.removeAll();
                        bodyLayout.add(databasePanel);
                        bodyLayout.repaint();
                        bodyLayout.revalidate();
                        databaseTitle.setText(name);
                    }


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        displayAllDatabasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    System.out.println(databasePanelUsed);
                    if (databasePanelUsed) {
                        int option = JOptionPane.showConfirmDialog(null, "Do you want to save the file?", "Save File", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            workingDatabase.saveDatabase();
                            databasePanelUsed = false;
                        }
                    }
                    Database.loadAllDatabases();
                    executionsPanel.removeAll();
                    executionsPanel.add(emptyBox);

                    bodyLayout.removeAll();
                    bodyLayout.add(loadAllDatabases);
                    bodyLayout.repaint();
                    bodyLayout.revalidate();
                    //javax.swing.JList<String> databasesList;
                    Vector<String> vectors = new Vector<String>(Database.loadAllDatabases());
                    listModel.removeAllElements();
                    listModel.addAll(vectors);
                    databasesList.setModel(listModel);
                    popupMenu = new JPopupMenu();
                    popupMenu.add(jmi1 = new JMenuItem("Enter"));
                    popupMenu.add(new JPopupMenu.Separator());
                    popupMenu.add(jmi2 = new JMenuItem("Remove"));
                    popupMenu.add(new JPopupMenu.Separator());
                    popupMenu.add(jmi3 = new JMenuItem("Rename"));

                    MouseListener mouseListener = new MouseAdapter() {
                        public void mouseClicked(MouseEvent mouseEvent) {
                            databasesList = (JList) mouseEvent.getSource();
                            if (mouseEvent.getClickCount() == 2) {
                                String fileName;
                                int index = databasesList.locationToIndex(mouseEvent.getPoint());
                                if (index >= 0) {
                                    Object o = databasesList.getModel().getElementAt(index);
                                    System.out.println("Double-clicked on: " + o.toString());
                                    try {
                                        StringBuilder sb = new StringBuilder(o.toString());
                                        //invoking the method
                                        for (int i = 0; i < 4; i++) {
                                            sb.deleteCharAt(sb.length() - 1);

                                        }
                                        fileName = sb.toString();
                                        workingDatabase = new Database(fileName);
                                        workingDatabase.load(fileName);
                                        //loadedFile=true;
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    bodyLayout.removeAll();
                                    bodyLayout.add(databasePanel);
                                    databasePanelUsed = true;
                                    bodyLayout.repaint();
                                    bodyLayout.revalidate();
                                    databaseTitle.setText(fileName);

                                }
                            }
                        }
                    };
                    databasesList.addMouseListener(mouseListener);

                    MouseAdapter menuMouse = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mousePressed(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mouseReleased(MouseEvent me) {
                            checkPopup(me);
                        }

                        public void checkPopup(MouseEvent me) {
                            popup = me;
                            if (SwingUtilities.isRightMouseButton(me)
                                    && !databasesList.isSelectionEmpty()
                                    && databasesList.locationToIndex(me.getPoint())
                                    == databasesList.getSelectedIndex()) {
                                popupMenu.show(databasesList, me.getX(), me.getY());
                            }
                        }
                    };
                    databasesList.addMouseListener(menuMouse);
                    jmi1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String fileName;
                            int index = databasesList.locationToIndex(popup.getPoint());
                            // popup= null;
                            //if (index >= 0) {
                            Object o = databasesList.getModel().getElementAt(index);
                            try {
                                StringBuffer sb = new StringBuffer(o.toString());
                                //invoking the method
                                for (int i = 0; i < 4; i++) {
                                    sb.deleteCharAt(sb.length() - 1);
                                }
                                fileName = sb.toString();
                                workingDatabase = new Database(fileName);
                                workingDatabase.load(fileName);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            //
                            databasePanelUsed = true;

                            bodyLayout.removeAll();
                            bodyLayout.add(databasePanel);
                            bodyLayout.repaint();
                            bodyLayout.revalidate();
                            databaseTitle.setText(fileName);


                        }
                    });

                    jmi2.addActionListener(e119 -> {

                        String fileName;
                        int index = databasesList.locationToIndex(popup.getPoint());
                        //popup=null;
                        //if (index >= 0) {
                        Object o = databasesList.getModel().getElementAt(index);
                        System.out.println("Double-clicked on: " + o.toString());
                        StringBuilder sb = new StringBuilder(o.toString());
                        //invoking the method
                        for (int i = 0; i < 4; i++) {
                            sb.deleteCharAt(sb.length() - 1);

                        }
                        fileName = sb.toString();
                        System.out.println(fileName);
                        Database.removeDatabase(fileName);
                        //int removeDatabase=databasesList.locationToIndex(popup.getPoint());

                        listModel.remove(index);
                        bodyLayout.removeAll();
                        bodyLayout.add(loadAllDatabases);
                        bodyLayout.repaint();
                        bodyLayout.revalidate();
                        Vector<String> vectors1 = null;
                        try {
                            vectors1 = new Vector<>(Database.loadAllDatabases());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        listModel.removeAllElements();
                        listModel.addAll(vectors1);
                        databasesList.setModel(listModel);
                    });
                    jmi3.addActionListener(e120 -> {

                        String fileName;
                        int index = databasesList.locationToIndex(popup.getPoint());
                        //popup= null;
                        //if (index >= 0) {
                        Object o = databasesList.getModel().getElementAt(index);
                        String newName = JOptionPane.showInputDialog(null, "Please enter the new name.");
                        if (newName.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: No name provided", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Database.renameFIle(o.toString(), newName);

                        bodyLayout.removeAll();
                        bodyLayout.add(loadAllDatabases);
                        bodyLayout.repaint();
                        bodyLayout.revalidate();
                        Vector<String> vectors12 = null;
                        try {
                            vectors12 = new Vector<>(Database.loadAllDatabases());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        listModel.removeAllElements();
                        listModel.addAll(vectors12);
                        databasesList.setModel(listModel);

                    });

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        displayAllButton.addActionListener(new ActionListener() {
            /**
             * This contains all the buttons related to the database methods ( insert new person, display database, look up country
             * look up by city, look up by race, look up by religion)
             * @param e the event to be processed
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                executionsPanel.removeAll();
                executionsPanel.add(displayAllCard);
                executionsPanel.repaint();
                executionsPanel.revalidate();
                viewingData = workingDatabase.mapToList().toArray(String[][]::new);
                String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                        "City of Birth", " Town of Birth", "Race", "Religion"};
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                displayTable.setModel(viewElementsModel);
                displayTable.setAutoCreateRowSorter(true);
                viewElementsModel.fireTableDataChanged();
                popupMenuDisplay = new JPopupMenu();
                JMenuItem menuItemUpdate = new JMenuItem("Update Character");

                JMenuItem menuItemRemove = new JMenuItem("Remove Selected Character(s)");

                JMenuItem menuItemRemoveAll = new JMenuItem("Clear Database");

                popupMenuDisplay.add(menuItemUpdate);
                popupMenuDisplay.add(menuItemRemove);
                popupMenuDisplay.add(menuItemRemoveAll);

                displayTable.setRowSelectionAllowed(true);
                displayTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


                MouseAdapter menuMouse = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        checkPopup(me);
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                        checkPopup(me);
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                        checkPopup(me);
                    }

                    public void checkPopup(MouseEvent me) {
                        Point point = me.getPoint();

                        int currentRow = displayTable.rowAtPoint(point);
                        currentRowTake = displayTable.rowAtPoint(point);
                        if (me.isPopupTrigger() && SwingUtilities.isRightMouseButton(me)) {
                            if (displayTable.rowAtPoint(me.getPoint()) != currentRow) {
                                displayTable.setRowSelectionInterval(currentRow, displayTable.rowAtPoint(me.getPoint()));
                                //currentRowTake=displayTable.rowAtPoint(me.getPoint());
                            } else {
                                displayTable.setRowSelectionInterval(currentRow, currentRow);
                            }

                            popupMenuDisplay.show(displayTable, me.getX(), me.getY());
                        }
                    }
                };
                displayTable.setComponentPopupMenu(popupMenuDisplay);

                displayTable.addMouseListener(menuMouse);
                menuItemUpdate.addActionListener(e1 -> {
                    int checking = currentRowTake;
                    //System.out.println(currentRowTake);
                    System.out.println(checking);

                    String firstName = (String) displayTable.getValueAt(checking, 0);
                    String middleName = (String) displayTable.getValueAt(checking, 1);
                    String lastName = (String) displayTable.getValueAt(checking, 2);
                    String alias = (String) displayTable.getValueAt(checking, 3);
                    String birthdate = (String) displayTable.getValueAt(checking, 4);
                    String countryOfBirth = (String) displayTable.getValueAt(checking, 5);
                    String cityOfBirth = (String) displayTable.getValueAt(checking, 6);
                    String townOfBirth = (String) displayTable.getValueAt(checking, 7);
                    String race = (String) displayTable.getValueAt(checking, 8);
                    String religion = (String) displayTable.getValueAt(checking, 9);
                    toUpdate = new Person(firstName, middleName, lastName, alias,
                            birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                    //workingDatabase.update(toUpdate);
                    if (workingDatabase.update(toUpdate)) {
                        viewingData = workingDatabase.mapToList().toArray(String[][]::new);

                        viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                        displayTable.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();


                    }
                });


                menuItemRemove.addActionListener(e12 -> {
                    int[] rows = displayTable.getSelectedRows();
                    for (int row : rows) {
                        String firstName = (String) displayTable.getValueAt(row, 0);
                        String middleName = (String) displayTable.getValueAt(row, 1);
                        String lastName = (String) displayTable.getValueAt(row, 2);
                        String alias = (String) displayTable.getValueAt(row, 3);
                        String birthdate = (String) displayTable.getValueAt(row, 4);
                        String countryOfBirth = (String) displayTable.getValueAt(row, 5);
                        String cityOfBirth = (String) displayTable.getValueAt(row, 6);
                        String townOfBirth = (String) displayTable.getValueAt(row, 7);
                        String race = (String) displayTable.getValueAt(row, 8);
                        String religion = (String) displayTable.getValueAt(row, 9);
                        toRemove = new Person(firstName, middleName, lastName, alias,
                                birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                        workingDatabase.remove(toRemove);

                    }
                    viewingData = workingDatabase.mapToList().toArray(String[][]::new);
                    viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                    displayTable.setModel(viewElementsModel);
                    viewElementsModel.fireTableDataChanged();
                });

                menuItemRemoveAll.addActionListener(e13 -> {
                    workingDatabase.clearDatabase();
                    viewingData = workingDatabase.mapToList().toArray(String[][]::new);
                    viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                    displayTable.setModel(viewElementsModel);
                    viewElementsModel.fireTableDataChanged();
                });

            }
        });
        lookUpByCountryButton.addActionListener(e -> {
            String countryName = JOptionPane.showInputDialog(null, "Please enter the country name.");
            tempName = countryName;
            if (countryName.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: No name provided", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                // System.out.println(Arrays.deepToString(workingDatabase.mapToList().toArray()));
                System.out.print(workingDatabase.checkCountry(countryName));

                if (workingDatabase.checkCountry(countryName)) {
                    executionsPanel.removeAll();
                    executionsPanel.add(countryCard);
                    executionsPanel.repaint();
                    executionsPanel.revalidate();
                    viewingData = workingDatabase.displayBasedOnCountry(countryName).toArray(String[][]::new);
                    String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                            "City of Birth", " Town of Birth", "Race", "Religion"};
                    viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                    tempModel = viewElementsModel;
                    countryDisplay.setModel(viewElementsModel);
                    countryDisplay.setAutoCreateRowSorter(true);
                    viewElementsModel.fireTableDataChanged();
                    popupMenuDisplay = new JPopupMenu();
                    JMenuItem menuItemUpdate = new JMenuItem("Update Character");

                    JMenuItem menuItemRemove = new JMenuItem("Remove Selected Character(s)");

                    JMenuItem menuItemRemoveAll = new JMenuItem("Clear Database");

                    popupMenuDisplay.add(menuItemUpdate);
                    popupMenuDisplay.add(menuItemRemove);
                    popupMenuDisplay.add(menuItemRemoveAll);

                    countryDisplay.setRowSelectionAllowed(true);
                    countryDisplay.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                    MouseAdapter menuMouse = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mousePressed(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mouseReleased(MouseEvent me) {
                            checkPopup(me);
                        }

                        public void checkPopup(MouseEvent me) {
                            Point point = me.getPoint();

                            int currentRow = countryDisplay.rowAtPoint(point);
                            currentRowTake = countryDisplay.rowAtPoint(point);
                            if (me.isPopupTrigger() && SwingUtilities.isRightMouseButton(me)) {
                                if (countryDisplay.rowAtPoint(me.getPoint()) != currentRow) {
                                    countryDisplay.setRowSelectionInterval(currentRow, countryDisplay.rowAtPoint(me.getPoint()));
                                    //currentRowTake=countryDisplay.rowAtPoint(me.getPoint());
                                } else {
                                    countryDisplay.setRowSelectionInterval(currentRow, currentRow);
                                }

                                popupMenuDisplay.show(countryDisplay, me.getX(), me.getY());
                            }
                        }
                    };
                    countryDisplay.setComponentPopupMenu(popupMenuDisplay);

                    countryDisplay.addMouseListener(menuMouse);
                    menuItemUpdate.addActionListener(e14 -> {
                        int checking = currentRowTake;
                        //System.out.println(currentRowTake);
                        System.out.println(checking);

                        String firstName = (String) countryDisplay.getValueAt(checking, 0);
                        String middleName = (String) countryDisplay.getValueAt(checking, 1);
                        String lastName = (String) countryDisplay.getValueAt(checking, 2);
                        String alias = (String) countryDisplay.getValueAt(checking, 3);
                        String birthdate = (String) countryDisplay.getValueAt(checking, 4);
                        String countryOfBirth = (String) countryDisplay.getValueAt(checking, 5);
                        String cityOfBirth = (String) countryDisplay.getValueAt(checking, 6);
                        String townOfBirth = (String) countryDisplay.getValueAt(checking, 7);
                        String race = (String) countryDisplay.getValueAt(checking, 8);
                        String religion = (String) countryDisplay.getValueAt(checking, 9);
                        toUpdate = new Person(firstName, middleName, lastName, alias,
                                birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                        //workingDatabase.update(toUpdate);
                        if (workingDatabase.update(toUpdate)) {
                            viewingData = workingDatabase.displayBasedOnCountry(countryName).toArray(String[][]::new);

                            viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                            countryDisplay.setModel(viewElementsModel);
                            viewElementsModel.fireTableDataChanged();


                        }
                    });


                    menuItemRemove.addActionListener(e15 -> {
                        int[] rows = countryDisplay.getSelectedRows();
                        for (int row : rows) {
                            String firstName = (String) countryDisplay.getValueAt(row, 0);
                            String middleName = (String) countryDisplay.getValueAt(row, 1);
                            String lastName = (String) countryDisplay.getValueAt(row, 2);
                            String alias = (String) countryDisplay.getValueAt(row, 3);
                            String birthdate = (String) countryDisplay.getValueAt(row, 4);
                            String countryOfBirth = (String) countryDisplay.getValueAt(row, 5);
                            String cityOfBirth = (String) countryDisplay.getValueAt(row, 6);
                            String townOfBirth = (String) countryDisplay.getValueAt(row, 7);
                            String race = (String) countryDisplay.getValueAt(row, 8);
                            String religion = (String) countryDisplay.getValueAt(row, 9);
                            toRemove = new Person(firstName, middleName, lastName, alias,
                                    birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                            workingDatabase.remove(toRemove);

                        }
                        viewingData = workingDatabase.displayBasedOnCountry(countryName).toArray(String[][]::new);
                        viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                        countryDisplay.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();
                    });

                    menuItemRemoveAll.addActionListener(e16 -> {
                        workingDatabase.deleteBasedOnCountry(countryName);
                        //viewingData=
                        viewElementsModel = new DefaultTableModel(workingDatabase.displayBasedOnCountry(countryName).toArray(String[][]::new), columnNames);
                        countryDisplay.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();
                    });


                } else {
                    JOptionPane.showMessageDialog(null, "This country's name doesn't exist in this database. " +
                            "please enter another name", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        });
        // need to fix remove and clear database
        lookUpByCityButton.addActionListener(e -> {
            String cityName = JOptionPane.showInputDialog(null, "Please enter the country name.");
            tempName = cityName;
            if (cityName.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: No name provided", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (workingDatabase.checkCity(cityName)) {
                    executionsPanel.removeAll();
                    executionsPanel.add(cityCard);
                    executionsPanel.repaint();
                    executionsPanel.revalidate();
                    viewingData = workingDatabase.displayBasedOnCity(cityName).toArray(String[][]::new);
                    String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                            "City of Birth", " Town of Birth", "Race", "Religion"};
                    viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                    cityDisplay.setModel(viewElementsModel);
                    cityDisplay.setAutoCreateRowSorter(true);
                    viewElementsModel.fireTableDataChanged();
                    popupMenuDisplay = new JPopupMenu();
                    JMenuItem menuItemUpdate = new JMenuItem("Update Character");

                    JMenuItem menuItemRemove = new JMenuItem("Remove Selected Character(s)");

                    JMenuItem menuItemRemoveAll = new JMenuItem("Clear Database");

                    popupMenuDisplay.add(menuItemUpdate);
                    popupMenuDisplay.add(menuItemRemove);
                    popupMenuDisplay.add(menuItemRemoveAll);

                    cityDisplay.setRowSelectionAllowed(true);
                    cityDisplay.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                    MouseAdapter menuMouse = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mousePressed(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mouseReleased(MouseEvent me) {
                            checkPopup(me);
                        }

                        public void checkPopup(MouseEvent me) {
                            Point point = me.getPoint();

                            int currentRow = cityDisplay.rowAtPoint(point);
                            currentRowTake = cityDisplay.rowAtPoint(point);
                            if (me.isPopupTrigger() && SwingUtilities.isRightMouseButton(me)) {
                                if (cityDisplay.rowAtPoint(me.getPoint()) != currentRow) {
                                    cityDisplay.setRowSelectionInterval(currentRow, cityDisplay.rowAtPoint(me.getPoint()));
                                    //currentRowTake=countryDisplay.rowAtPoint(me.getPoint());
                                } else {
                                    cityDisplay.setRowSelectionInterval(currentRow, currentRow);
                                }

                                popupMenuDisplay.show(cityDisplay, me.getX(), me.getY());
                            }
                        }
                    };
                    cityDisplay.setComponentPopupMenu(popupMenuDisplay);

                    cityDisplay.addMouseListener(menuMouse);
                    menuItemUpdate.addActionListener(e17 -> {
                        int checking = currentRowTake;
                        //System.out.println(currentRowTake);
                        System.out.println(checking);

                        String firstName = (String) cityDisplay.getValueAt(checking, 0);
                        String middleName = (String) cityDisplay.getValueAt(checking, 1);
                        String lastName = (String) cityDisplay.getValueAt(checking, 2);
                        String alias = (String) cityDisplay.getValueAt(checking, 3);
                        String birthdate = (String) cityDisplay.getValueAt(checking, 4);
                        String countryOfBirth = (String) cityDisplay.getValueAt(checking, 5);
                        String cityOfBirth = (String) cityDisplay.getValueAt(checking, 6);
                        String townOfBirth = (String) cityDisplay.getValueAt(checking, 7);
                        String race = (String) cityDisplay.getValueAt(checking, 8);
                        String religion = (String) cityDisplay.getValueAt(checking, 9);
                        toUpdate = new Person(firstName, middleName, lastName, alias,
                                birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                        //workingDatabase.update(toUpdate);
                        if (workingDatabase.update(toUpdate)) {
                            //viewingData= ;

                            viewElementsModel = new DefaultTableModel(workingDatabase.displayBasedOnCity(cityName).toArray(String[][]::new), columnNames);
                            cityDisplay.setModel(viewElementsModel);
                            viewElementsModel.fireTableDataChanged();
                        }
                    });

                    menuItemRemove.addActionListener(e18 -> {
                        int[] rows = cityDisplay.getSelectedRows();
                        for (int row : rows) {
                            String firstName = (String) cityDisplay.getValueAt(row, 0);
                            String middleName = (String) cityDisplay.getValueAt(row, 1);
                            String lastName = (String) cityDisplay.getValueAt(row, 2);
                            String alias = (String) cityDisplay.getValueAt(row, 3);
                            String birthdate = (String) cityDisplay.getValueAt(row, 4);
                            String countryOfBirth = (String) cityDisplay.getValueAt(row, 5);
                            String cityOfBirth = (String) cityDisplay.getValueAt(row, 6);
                            String townOfBirth = (String) cityDisplay.getValueAt(row, 7);
                            String race = (String) cityDisplay.getValueAt(row, 8);
                            String religion = (String) cityDisplay.getValueAt(row, 9);
                            toRemove = new Person(firstName, middleName, lastName, alias,
                                    birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                            workingDatabase.remove(toRemove);

                        }
                        viewElementsModel = new DefaultTableModel((workingDatabase.displayBasedOnCity(cityName).toArray(String[][]::new)), columnNames);
                        cityDisplay.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();
                    });

                    menuItemRemoveAll.addActionListener(e19 -> {
                        workingDatabase.deleteBasedOnCity(cityName);
                        //viewingData=
                        viewElementsModel = new DefaultTableModel(workingDatabase.displayBasedOnCity(cityName).toArray(String[][]::new), columnNames);
                        cityDisplay.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();
                    });


                } else {
                    JOptionPane.showMessageDialog(null, "This city's name doesn't exist in this database. " +
                            "please enter another name", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        lookUpByRaceButton.addActionListener(e -> {
            String raceName = JOptionPane.showInputDialog(null, "Please enter the name of the race.");
            tempName = raceName;

            if (raceName.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: No name provided", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (workingDatabase.checkRace(raceName)) {
                    executionsPanel.removeAll();
                    executionsPanel.add(raceCard);
                    executionsPanel.repaint();
                    executionsPanel.revalidate();
                    //viewingData= ;
                    String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                            "City of Birth", " Town of Birth", "Race", "Religion"};
                    viewElementsModel = new DefaultTableModel(workingDatabase.displayBasedOnRace(raceName).toArray(String[][]::new), columnNames);
                    raceDisplay.setModel(viewElementsModel);
                    raceDisplay.setAutoCreateRowSorter(true);
                    viewElementsModel.fireTableDataChanged();
                    popupMenuDisplay = new JPopupMenu();
                    JMenuItem menuItemUpdate = new JMenuItem("Update Character");

                    JMenuItem menuItemRemove = new JMenuItem("Remove Selected Character(s)");

                    JMenuItem menuItemRemoveAll = new JMenuItem("Clear Database");

                    popupMenuDisplay.add(menuItemUpdate);
                    popupMenuDisplay.add(menuItemRemove);
                    popupMenuDisplay.add(menuItemRemoveAll);

                    raceDisplay.setRowSelectionAllowed(true);
                    raceDisplay.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                    MouseAdapter menuMouse = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mousePressed(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mouseReleased(MouseEvent me) {
                            checkPopup(me);
                        }

                        public void checkPopup(MouseEvent me) {
                            Point point = me.getPoint();

                            int currentRow = raceDisplay.rowAtPoint(point);
                            currentRowTake = raceDisplay.rowAtPoint(point);
                            if (me.isPopupTrigger() && SwingUtilities.isRightMouseButton(me)) {
                                if (raceDisplay.rowAtPoint(me.getPoint()) != currentRow) {
                                    raceDisplay.setRowSelectionInterval(currentRow, raceDisplay.rowAtPoint(me.getPoint()));
                                    //currentRowTake=countryDisplay.rowAtPoint(me.getPoint());
                                } else {
                                    raceDisplay.setRowSelectionInterval(currentRow, currentRow);
                                }

                                popupMenuDisplay.show(raceDisplay, me.getX(), me.getY());
                            }
                        }
                    };
                    raceDisplay.setComponentPopupMenu(popupMenuDisplay);

                    raceDisplay.addMouseListener(menuMouse);
                    menuItemUpdate.addActionListener(e110 -> {
                        int checking = currentRowTake;
                        //System.out.println(currentRowTake);
                        System.out.println(checking);

                        String firstName = (String) raceDisplay.getValueAt(checking, 0);
                        String middleName = (String) raceDisplay.getValueAt(checking, 1);
                        String lastName = (String) raceDisplay.getValueAt(checking, 2);
                        String alias = (String) raceDisplay.getValueAt(checking, 3);
                        String birthdate = (String) raceDisplay.getValueAt(checking, 4);
                        String countryOfBirth = (String) raceDisplay.getValueAt(checking, 5);
                        String cityOfBirth = (String) raceDisplay.getValueAt(checking, 6);
                        String townOfBirth = (String) raceDisplay.getValueAt(checking, 7);
                        String race = (String) raceDisplay.getValueAt(checking, 8);
                        String religion = (String) raceDisplay.getValueAt(checking, 9);
                        toUpdate = new Person(firstName, middleName, lastName, alias,
                                birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                        //workingDatabase.update(toUpdate);
                        if (workingDatabase.update(toUpdate)) {
                            //viewingData= ;

                            viewElementsModel = new DefaultTableModel(workingDatabase.displayBasedOnRace(raceName).toArray(String[][]::new), columnNames);
                            raceDisplay.setModel(viewElementsModel);
                            viewElementsModel.fireTableDataChanged();
                        }
                    });

                    menuItemRemove.addActionListener(e111 -> {
                        int[] rows = raceDisplay.getSelectedRows();
                        for (int row : rows) {
                            String firstName = (String) raceDisplay.getValueAt(row, 0);
                            String middleName = (String) raceDisplay.getValueAt(row, 1);
                            String lastName = (String) raceDisplay.getValueAt(row, 2);
                            String alias = (String) raceDisplay.getValueAt(row, 3);
                            String birthdate = (String) raceDisplay.getValueAt(row, 4);
                            String countryOfBirth = (String) raceDisplay.getValueAt(row, 5);
                            String cityOfBirth = (String) raceDisplay.getValueAt(row, 6);
                            String townOfBirth = (String) raceDisplay.getValueAt(row, 7);
                            String race = (String) raceDisplay.getValueAt(row, 8);
                            String religion = (String) raceDisplay.getValueAt(row, 9);
                            toRemove = new Person(firstName, middleName, lastName, alias,
                                    birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                            workingDatabase.remove(toRemove);

                        }
                        viewElementsModel = new DefaultTableModel((workingDatabase.displayBasedOnRace(raceName).toArray(String[][]::new)), columnNames);
                        raceDisplay.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();
                    });

                    menuItemRemoveAll.addActionListener(e112 -> {
                        workingDatabase.deleteBasedOnRace(raceName);
                        //viewingData=
                        viewElementsModel = new DefaultTableModel(workingDatabase.displayBasedOnRace(raceName).toArray(String[][]::new), columnNames);
                        raceDisplay.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();
                    });


                } else {
                    JOptionPane.showMessageDialog(null, "This race doesn't exist in this database. " +
                            "please enter another name", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        });
        lookUpByReligionButton.addActionListener(e -> {
            String religionName = JOptionPane.showInputDialog(null, "Please enter the name of the religion.");
            tempName = religionName;

            if (religionName.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error: No name provided", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (workingDatabase.checkReligion(religionName)) {
                    executionsPanel.removeAll();
                    executionsPanel.add(religionCard);
                    executionsPanel.repaint();
                    executionsPanel.revalidate();
                    String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                            "City of Birth", " Town of Birth", "Race", "Religion"};
                    DefaultTableModel viewElementsModels = new DefaultTableModel(workingDatabase.displayBasedOnReligion(religionName).toArray(String[][]::new), columnNames);
                    religionDisplay.setModel(viewElementsModels);
                    religionDisplay.setAutoCreateRowSorter(true);
                    viewElementsModel.fireTableDataChanged();
                    popupMenuDisplay = new JPopupMenu();
                    JMenuItem menuItemUpdate = new JMenuItem("Update Character");

                    JMenuItem menuItemRemove = new JMenuItem("Remove Selected Character(s)");

                    JMenuItem menuItemRemoveAll = new JMenuItem("Clear Database");

                    popupMenuDisplay.add(menuItemUpdate);
                    popupMenuDisplay.add(menuItemRemove);
                    popupMenuDisplay.add(menuItemRemoveAll);

                    religionDisplay.setRowSelectionAllowed(true);
                    religionDisplay.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                    MouseAdapter menuMouse = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mousePressed(MouseEvent me) {
                            checkPopup(me);
                        }

                        @Override
                        public void mouseReleased(MouseEvent me) {
                            checkPopup(me);
                        }

                        public void checkPopup(MouseEvent me) {
                            Point point = me.getPoint();

                            int currentRow = religionDisplay.rowAtPoint(point);
                            currentRowTake = religionDisplay.rowAtPoint(point);
                            if (me.isPopupTrigger() && SwingUtilities.isRightMouseButton(me)) {
                                if (religionDisplay.rowAtPoint(me.getPoint()) != currentRow) {
                                    religionDisplay.setRowSelectionInterval(currentRow, religionDisplay.rowAtPoint(me.getPoint()));
                                    //currentRowTake=countryDisplay.rowAtPoint(me.getPoint());
                                } else {
                                    religionDisplay.setRowSelectionInterval(currentRow, currentRow);
                                }

                                popupMenuDisplay.show(religionDisplay, me.getX(), me.getY());
                            }
                        }
                    };
                    religionDisplay.setComponentPopupMenu(popupMenuDisplay);

                    religionDisplay.addMouseListener(menuMouse);
                    menuItemUpdate.addActionListener(e113 -> {
                        int checking = currentRowTake;
                        //System.out.println(currentRowTake);
                        System.out.println(checking);

                        String firstName = (String) religionDisplay.getValueAt(checking, 0);
                        String middleName = (String) religionDisplay.getValueAt(checking, 1);
                        String lastName = (String) religionDisplay.getValueAt(checking, 2);
                        String alias = (String) religionDisplay.getValueAt(checking, 3);
                        String birthdate = (String) religionDisplay.getValueAt(checking, 4);
                        String countryOfBirth = (String) religionDisplay.getValueAt(checking, 5);
                        String cityOfBirth = (String) religionDisplay.getValueAt(checking, 6);
                        String townOfBirth = (String) religionDisplay.getValueAt(checking, 7);
                        String race = (String) religionDisplay.getValueAt(checking, 8);
                        String religion = (String) religionDisplay.getValueAt(checking, 9);
                        toUpdate = new Person(firstName, middleName, lastName, alias,
                                birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                        //workingDatabase.update(toUpdate);
                        if (workingDatabase.update(toUpdate)) {
                            //viewingData= ;

                            viewElementsModel = new DefaultTableModel(workingDatabase.displayBasedOnReligion(religionName).toArray(String[][]::new), columnNames);
                            religionDisplay.setModel(viewElementsModel);
                            viewElementsModel.fireTableDataChanged();
                        }
                    });

                    menuItemRemove.addActionListener(e114 -> {
                        int[] rows = religionDisplay.getSelectedRows();
                        for (int row : rows) {
                            String firstName = (String) religionDisplay.getValueAt(row, 0);
                            String middleName = (String) religionDisplay.getValueAt(row, 1);
                            String lastName = (String) religionDisplay.getValueAt(row, 2);
                            String alias = (String) religionDisplay.getValueAt(row, 3);
                            String birthdate = (String) religionDisplay.getValueAt(row, 4);
                            String countryOfBirth = (String) religionDisplay.getValueAt(row, 5);
                            String cityOfBirth = (String) religionDisplay.getValueAt(row, 6);
                            String townOfBirth = (String) religionDisplay.getValueAt(row, 7);
                            String race = (String) religionDisplay.getValueAt(row, 8);
                            String religion = (String) religionDisplay.getValueAt(row, 9);
                            toRemove = new Person(firstName, middleName, lastName, alias,
                                    birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                            workingDatabase.remove(toRemove);

                        }
                        viewElementsModel = new DefaultTableModel((workingDatabase.displayBasedOnReligion(religionName).toArray(String[][]::new)), columnNames);
                        religionDisplay.setModel(viewElementsModel);
                        viewElementsModel.fireTableDataChanged();
                    });

                    menuItemRemoveAll.addActionListener(e115 -> {
                        workingDatabase.deleteBasedOnReligion(religionName);
                        //viewingData=
                        DefaultTableModel viewElementsModels1 = new DefaultTableModel(workingDatabase.displayBasedOnReligion(religionName).toArray(String[][]::new), columnNames);
                        religionDisplay.setModel(viewElementsModels1);
                        viewElementsModel.fireTableDataChanged();
                    });


                } else {
                    JOptionPane.showMessageDialog(null, "This religion doesn't exist in this database. " +
                            "please enter another name", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        insertNewPersonButton.addActionListener(e -> {
            JPanel informationPanel = new JPanel();
            Person newPerson = workingDatabase.fillInformation(informationPanel);
            if (newPerson != null) {
                workingDatabase.insert(newPerson.getFirstName(), newPerson);
            }
            workingDatabase.display();
            executionsPanel.removeAll();
            executionsPanel.add(displayAllCard);
            executionsPanel.repaint();
            executionsPanel.revalidate();
            viewingData = workingDatabase.mapToList().toArray(String[][]::new);
            String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                    "City of Birth", " Town of Birth", "Race", "Religion"};
            viewElementsModel = new DefaultTableModel(viewingData, columnNames);
            displayTable.setModel(viewElementsModel);
            displayTable.setAutoCreateRowSorter(true);
            viewElementsModel.fireTableDataChanged();
            popupMenuDisplay = new JPopupMenu();
            JMenuItem menuItemUpdate = new JMenuItem("Update Character");

            JMenuItem menuItemRemove = new JMenuItem("Remove Selected Character(s)");

            JMenuItem menuItemRemoveAll = new JMenuItem("Clear Database");

            popupMenuDisplay.add(menuItemUpdate);
            popupMenuDisplay.add(menuItemRemove);
            popupMenuDisplay.add(menuItemRemoveAll);

            displayTable.setRowSelectionAllowed(true);
            displayTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            MouseAdapter menuMouse = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    checkPopup(me);
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    checkPopup(me);
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    checkPopup(me);
                }

                public void checkPopup(MouseEvent me) {
                    Point point = me.getPoint();

                    int currentRow = displayTable.rowAtPoint(point);
                    currentRowTake = displayTable.rowAtPoint(point);
                    if (me.isPopupTrigger() && SwingUtilities.isRightMouseButton(me)) {
                        if (displayTable.rowAtPoint(me.getPoint()) != currentRow) {
                            displayTable.setRowSelectionInterval(currentRow, displayTable.rowAtPoint(me.getPoint()));
                            //currentRowTake=displayTable.rowAtPoint(me.getPoint());
                        } else {
                            displayTable.setRowSelectionInterval(currentRow, currentRow);
                        }

                        popupMenuDisplay.show(displayTable, me.getX(), me.getY());
                    }
                }
            };
            displayTable.setComponentPopupMenu(popupMenuDisplay);

            displayTable.addMouseListener(menuMouse);
            menuItemUpdate.addActionListener(e116 -> {
                int checking = currentRowTake;
                //System.out.println(currentRowTake);
                System.out.println(checking);

                String firstName = (String) displayTable.getValueAt(checking, 0);
                String middleName = (String) displayTable.getValueAt(checking, 1);
                String lastName = (String) displayTable.getValueAt(checking, 2);
                String alias = (String) displayTable.getValueAt(checking, 3);
                String birthdate = (String) displayTable.getValueAt(checking, 4);
                String countryOfBirth = (String) displayTable.getValueAt(checking, 5);
                String cityOfBirth = (String) displayTable.getValueAt(checking, 6);
                String townOfBirth = (String) displayTable.getValueAt(checking, 7);
                String race = (String) displayTable.getValueAt(checking, 8);
                String religion = (String) displayTable.getValueAt(checking, 9);
                toUpdate = new Person(firstName, middleName, lastName, alias,
                        birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                //workingDatabase.update(toUpdate);
                if (workingDatabase.update(toUpdate)) {
                    viewingData = workingDatabase.mapToList().toArray(String[][]::new);

                    viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                    displayTable.setModel(viewElementsModel);
                    viewElementsModel.fireTableDataChanged();

                }
            });

            menuItemRemove.addActionListener(e117 -> {
                int[] rows = displayTable.getSelectedRows();
                for (int row : rows) {
                    String firstName = (String) displayTable.getValueAt(row, 0);
                    String middleName = (String) displayTable.getValueAt(row, 1);
                    String lastName = (String) displayTable.getValueAt(row, 2);
                    String alias = (String) displayTable.getValueAt(row, 3);
                    String birthdate = (String) displayTable.getValueAt(row, 4);
                    String countryOfBirth = (String) displayTable.getValueAt(row, 5);
                    String cityOfBirth = (String) displayTable.getValueAt(row, 6);
                    String townOfBirth = (String) displayTable.getValueAt(row, 7);
                    String race = (String) displayTable.getValueAt(row, 8);
                    String religion = (String) displayTable.getValueAt(row, 9);
                    toRemove = new Person(firstName, middleName, lastName, alias,
                            birthdate, countryOfBirth, cityOfBirth, townOfBirth, race, religion);
                    workingDatabase.remove(toRemove);

                }
                viewingData = workingDatabase.mapToList().toArray(String[][]::new);
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                displayTable.setModel(viewElementsModel);
                viewElementsModel.fireTableDataChanged();
            });

            menuItemRemoveAll.addActionListener(e118 -> {
                workingDatabase.clearDatabase();
                viewingData = workingDatabase.mapToList().toArray(String[][]::new);
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                displayTable.setModel(viewElementsModel);
                viewElementsModel.fireTableDataChanged();
            });
        });
        saveButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs. This will save the database information into the corresponding file.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    workingDatabase.saveDatabase();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        databaseSearch.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed. This filters the data presented in the table based on the user's input.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String filter = databaseSearch.getText();
                try {
                    listModel((DefaultListModel<String>) databasesList.getModel(), filter);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }

            private void listModel(DefaultListModel<String> model, String filter) throws IOException {
                for (String s : Database.loadAllDatabases()) {
                    if (!s.startsWith(filter)) {
                        if (model.contains(s)) {
                            model.removeElement(s);
                        }
                    } else {
                        if (!model.contains(s)) {
                            model.addElement(s);
                        }
                    }
                }

            }
        });
        DisplayAllSearchField.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                viewingData = workingDatabase.mapToList().toArray(String[][]::new);
                String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                        "City of Birth", " Town of Birth", "Race", "Religion"};
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                final TableRowSorter<TableModel> sorter = new TableRowSorter<>(viewElementsModel);
                displayTable.setRowSorter(sorter);
                String text = DisplayAllSearchField.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch (PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
            }
        });
        CountryCardSearch.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                viewingData = workingDatabase.displayBasedOnCountry(tempName).toArray(String[][]::new);
                String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                        "City of Birth", " Town of Birth", "Race", "Religion"};
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                final TableRowSorter<TableModel> sorter = new TableRowSorter<>(viewElementsModel);
                countryDisplay.setRowSorter(sorter);
                String text = CountryCardSearch.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch (PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
            }
        });
        CityCardSearch.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed. When the user clicks on search by city button.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                viewingData = workingDatabase.displayBasedOnCity(tempName).toArray(String[][]::new);
                String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                        "City of Birth", " Town of Birth", "Race", "Religion"};
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                final TableRowSorter<TableModel> sorter = new TableRowSorter<>(viewElementsModel);
                cityDisplay.setRowSorter(sorter);
                String text = CityCardSearch.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch (PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
            }
        });
        raceCardSearch.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                viewingData = workingDatabase.displayBasedOnRace(tempName).toArray(String[][]::new);
                String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                        "City of Birth", " Town of Birth", "Race", "Religion"};
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                final TableRowSorter<TableModel> sorter = new TableRowSorter<>(viewElementsModel);
                raceDisplay.setRowSorter(sorter);
                String text = raceCardSearch.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch (PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
            }
        });
        religionSearchCard.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed. When the user clicks on search by religion button.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                viewingData = workingDatabase.displayBasedOnReligion(tempName).toArray(String[][]::new);
                String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                        "City of Birth", " Town of Birth", "Race", "Religion"};
                viewElementsModel = new DefaultTableModel(viewingData, columnNames);
                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(viewElementsModel);
                religionDisplay.setRowSorter(sorter);
                String text = religionSearchCard.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch (PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        listModel = new DefaultListModel<>();
        databasesList = new JList<>();
        databasesList.setModel(listModel);
        displayScroll = new JScrollPane();
        String[] columnNames = {"First Name", "Middle Name", "Last Name", "Alias", " Date of Birth", "Country of Birth",
                "City of Birth", " Town of Birth", "Race", "Religion"};
        viewElementsModel = new DefaultTableModel(viewingData, columnNames);
        starting = new ArrayList<>();
        viewingData = starting.toArray(String[][]::new);
        displayTable = new JTable(viewElementsModel);

        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(viewElementsModel);
        displayTable.setRowSorter(sorter);


    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("GUICardLayout");
        frame.setContentPane(new GUICardLayout().mainPanel);
        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

    }
}

