/**
 * File: GUICardLayout.java
 * This program creates a database that store the information of characters.
 * This class contains the database and all the methods that are used to modify it.
 * Proper Javadoc are missing, they will be added later
 * @author Tarek Solamy (Alsolame)
 * @version 2.0 10/30/2022
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;
import java.util.TreeMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Database.
 */
public class Database {

    private Person character;

    private TreeMap<String, List<Person>> directory;
    private TreeMap<String, List<Person>> searchDirectory;
    private TreeMap<String, List<Person>> tempDirectory;

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     * Sets directory.
     *
     * @param directory the directory
     */
    public void setDirectory(TreeMap<String, List<Person>> directory) {
        this.directory = directory;
    }


    private String name;
    /**
     * The Items.
     */
    Vector<ListPersons> items;
    private ArrayList<String []> units;

    /**
     * Instantiates a new Database.
     */
    public Database(){
        directory = new TreeMap<String, List<Person>>();
        items= new Vector<ListPersons>();
        units= new ArrayList<String []>();
    }

    /**
     * Instantiates a new Database.
     *
     * @param name the name
     */
    public Database(String name) {
        this.name = name;

        directory = new TreeMap<String, List<Person>>(); // the map is made up of the key the person's name and the value is list of their characteristics.
        items= new Vector<ListPersons>();
        units= new ArrayList<String []> ();

    }


    /**
     * Gets directory.
     *
     * @return the directory
     */
    public TreeMap<String, List<Person>> getDirectory() {
        return directory;
    }


    /**
     * Insert a new person to the directory.
     *
     * @param name      the name
     * @param newPerson the new person
     */
    public void insert(String name, Person newPerson) {

        if (directory.containsKey(name)) {
            List<Person> previous = directory.get(name);
            previous.add(newPerson);
            directory.replace(name, previous);
        } else {
            List<Person> newList = new ArrayList<>();
            newList.add(newPerson);
            directory.put(name, newList);
        }
    }

    /**
     * Update a person's informaiton in the map. It returns true if update was successful, otherwise false.
     *
     * @param PersonToUpdate the person to update
     * @return the boolean
     */
    public boolean update(Person PersonToUpdate) {

        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (PersonToUpdate.toString().equals(current.toString())) {
                        Person toReplace= autoInformationFiller(PersonToUpdate);
                        if(toReplace!=null){
                            entry.getValue().remove(current);
                            entry.getValue().add(toReplace);
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Remove a person from the TreeMap. Returns true if successfully removed, otherwise false.
     *
     * @param PersonToRemove the person to remove
     * @return the boolean
     */
    public boolean remove(Person PersonToRemove) {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (PersonToRemove.toString().equals(current.toString())) {
                        // fix this
                        entry.getValue().remove(current);
                        return true;
                    }
                    }
                }

            }
        return false;

    }

    /**
     * Clears database.
     */
    public void clearDatabase(){
        directory.clear();
    }


    /**
     * Display.
     */
    public void display() {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }

    /**
     * This method looks up the people who are from a certain country (based on user input). It returns true if any were found.
     *
     * @param country the country
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean checkCountry(String country) throws IOException {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCountryOfBirth().equals(country)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }


    /**
     * Display based on country array list.
     *
     * @param country the country
     * @return the array list
     */
    public ArrayList<String []> displayBasedOnCountry(String country){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (current.getCountryOfBirth().equals(country)) {
                        searchDirectory.put(current.getFirstName(), entry.getValue());
                    }
                }

            }

        }

        return mapToListInClass(searchDirectory);
    }

    /**
     * Delete based on country.
     *
     * @param country the country
     */
    void deleteBasedOnCountry(String country){
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCountryOfBirth().equals(country)) {

                        itr.remove();

                    }
                }

            }
        }

    }

    /**
     * This method looks up the people who are from a certain city (based on user input). It returns true if any were found.
     *
     * @param city the city
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean checkCity(String city) throws IOException {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (current.getCityOfBirth().equals(city)) {
                        return true;
                    }
                }

            }

        }
        return false;
    }


    /**
     * Display based on city array list.
     *
     * @param city the city
     * @return the array list
     */
    public ArrayList<String []> displayBasedOnCity(String city){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (current.getCityOfBirth().equals(city)) {
                        searchDirectory.put(current.getFirstName(), entry.getValue());
                    }
                }

            }

        }

        return mapToListInClass(searchDirectory);
    }

    /**
     * Delete based on city.
     *
     * @param city the city
     */
    public void deleteBasedOnCity(String city){
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator<Person> itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCityOfBirth().equals(city)) {
                        itr.remove();

                    }
                }

            }

        }

    }

    /**
     * This method looks up the people who are from a certain race (based on user input). It returns true if any were found.
     *
     * @param race the race
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean checkRace(String race) throws IOException {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (current.getRace().equals(race)) {
                        return true;
                    }
                }

            }

        }
        return false;
    }


    /**
     * Display based on race array list.
     *
     * @param race the race
     * @return the array list
     */
    public ArrayList<String []> displayBasedOnRace(String race){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (current.getRace().equals(race)) {
                        searchDirectory.put(current.getFirstName(), entry.getValue());

                    }
                }

            }

        }

        return mapToListInClass(searchDirectory);
    }

    /**
     * Delete based on race array list.
     *
     * @param race the race
     * @return the array list
     */
    public ArrayList<String []> deleteBasedOnRace(String race){
        //searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getRace().equals(race)) {
                        itr.remove();

                    }
                }

            }

        }

        return mapToListInClass(searchDirectory);
    }

    /**
     * This method looks up the people who are from a certain religion (based on user input). It returns true if any were found.
     *
     * @param religion the religion
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean checkReligion(String religion) throws IOException {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (current.getReligion().equals(religion)) {
                        return true;
                    }
                }

            }

        }
        return false;
    }


    /**
     * Display based on religion array list.
     *
     * @param religion the religion
     * @return the array list
     */
    public ArrayList<String []> displayBasedOnReligion(String religion){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person current : entry.getValue()) {
                {
                    if (current.getReligion().equals(religion)) {
                        searchDirectory.put(current.getFirstName(), entry.getValue());
                    }
                }

            }

        }

        return mapToListInClass(searchDirectory);
    }

    /**
     * Delete based on religion array list.
     *
     * @param religion the religion
     * @return the array list
     */
    public ArrayList<String []> deleteBasedOnReligion(String religion){
        //searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator<Person> itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = itr.next();
                {
                    if (current.getReligion().equals(religion)) {
                        itr.remove();
                    }
                }

            }

        }


        return mapToListInClass(searchDirectory);
    }


    /**
     * This method creates a new file in the database folder. It ruturns if file was successfully created.
     *
     * @param name the name
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean createNewFile (String name) throws IOException {

        //Database newOne = new Database(name);

        File myObj = new File("Databases\\" + name +".txt");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
            return true;
        } else {
            System.out.println("File already exists.");
        }
        return false;
    }

    /**
     * This method loads a file from the database folder. It ruturns if file was successfully found.
     *
     * @param fileName the file name
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean load(String fileName) throws IOException {


        //List<Person> newList = new ArrayList<>();
        Person toFind= new Person();
        Path path = Paths.get("Databases\\" + fileName +".txt");
        if(Files.exists(path)) {
            this.name = fileName;
            this.directory = new TreeMap<String, List<Person>>();
            File toLoad = new File("Databases\\" + fileName + ".txt");



            try (BufferedReader br = new BufferedReader(new FileReader(toLoad))) {
                String line = br.readLine();
                if (line == null ||
                        (line.length() == 0 && br.readLine() == null)) {
                    System.out.println("NO ERRORS!");
                } else {
                    Person toLoadFromFile = new Person();
                    Scanner scanner = new Scanner(toLoad);
                    String text = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    textExtractor (toLoadFromFile, text);
                }
            }

            return true;
        }
        return false;

    }

    private void textExtractor (Person toLoadTo, String text){
        // Regex to extract the string
        // between two delimiters
        String regex = "\\:(.*?)\\.";
        //String regex = "\\[(.*?)\\]";

        // Compile the Regex.
        Pattern p = Pattern.compile(regex);

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(text);

        // Get the subsequence
        // using find() method
        int counter= 1;
        while (m.find())
        {
            if (counter==1){
                toLoadTo.setFirstName(m.group(1).substring(1));

                counter++;
            } else if (counter==2){
                toLoadTo.setMiddleName(m.group(1).substring(1));

                counter++;
            }else if (counter==3){
                toLoadTo.setLastName(m.group(1).substring(1));

                counter++;
            }else if (counter==4){
                toLoadTo.setAlias(m.group(1).substring(1));

                counter++;
            }else if (counter==5){
                toLoadTo.setBirthDate(m.group(1).substring(1));

                counter++;
            }else if (counter==6){
                toLoadTo.setCountryOfBirth(m.group(1).substring(1));

                counter++;
            }else if (counter==7){
                toLoadTo.setCityOfBirth(m.group(1).substring(1));

                counter++;
            }else if (counter==8){
                toLoadTo.setTownOfBirth(m.group(1).substring(1));

                counter++;
            }else if (counter==9){
                toLoadTo.setRace(m.group(1).substring(1));

                counter++;
            }else if (counter==10){
                toLoadTo.setReligion(m.group(1).substring(1));
                insert(toLoadTo.getFirstName() , toLoadTo);
                //System.out.println(toLoadTo.toString());
                toLoadTo= new Person();
                counter=1;
            }

            System.out.println(m.group(1));
        }
    }

    /**
     * Load all databases array list.
     *
     * @return the array list
     * @throws IOException the io exception
     */
    public static ArrayList<String>  loadAllDatabases() throws IOException {

        createNewFile ("location_finder");
        File toLoad = new File("Databases\\location_finder.txt");

        System.out.println(toLoad.getAbsolutePath());
        StringBuilder sb= new StringBuilder(toLoad.getAbsolutePath());
        //invoking the method
        for (int i = 0; i< 20 ; i++){
            sb.deleteCharAt(sb.length()-1);

        }

        removeDatabase("location_Finder");
        File directoryPath = new File (String.valueOf(sb));
        //List of all files and directories
        String[] contents = directoryPath.list();
        assert contents != null;
        return new ArrayList<String>(Arrays.asList(contents));

    }

    /**
     * Remove database.
     *
     * @param fileName the file name
     * @throws SecurityException the security exception
     */
    public static void removeDatabase(String fileName) throws SecurityException{
        // scan the files for the name of the database and once it finds it, it deletes the file

        File file
                = new File("Databases\\" + fileName +".txt");
        File toRemove= new File(file.getAbsolutePath());

        if (toRemove.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }


    }

    /**
     * Save database.
     *
     * @throws IOException the io exception
     */
    public void saveDatabase() throws IOException{
        TreeMap<String, List<Person>> toSave= getDirectory();
        //File[] files = new File("\\Identities_Database\\Databases").listFiles();

        //If this pathname does not denote a directory, then listFiles() returns null.
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("Databases\\" + name +".txt"));
        writer.write("");
        writer.flush();
        //file.delete();
        File updated= new File("Databases\\" + name +".txt");
        int entriesCounter=1;
        BufferedWriter bf= new BufferedWriter(new FileWriter(updated));
        for (Map.Entry <String, List<Person>> entry: toSave.entrySet()){
            bf.write("Data entry : " + entriesCounter + System.getProperty("line.separator"));
            bf.write(entry.getValue()+System.getProperty("line.separator"));
            bf.write("==========================");
            bf.newLine();
            entriesCounter++;
        }
        bf.close();

    }

    /**
     * Rename file.
     *
     * @param oldName the old name
     * @param newName the new name
     */
    public static void renameFIle(String oldName, String newName){
        Path oldFile
                = Paths.get("Databases\\"+ oldName);

        try {
            Files.move(oldFile, oldFile.resolveSibling(
                    newName + ".txt"));
            System.out.println("File Successfully Rename");
        }
        catch (IOException e) {
            System.out.println("operation failed");
        }
    }


    /**
     * Fill information person.
     *
     * @param myPanel my panel
     * @return the person
     */
    public Person fillInformation(JPanel myPanel){
        JTextField firstName = new JTextField(5);
        JTextField middleName = new JTextField(5);
        JTextField lastName = new JTextField(5);
        JTextField alias = new JTextField(5);
        JTextField birthDate = new JTextField(5);
        JTextField countryOfBirth = new JTextField(5);
        JTextField cityOfBirth = new JTextField(5);
        JTextField townOfBirth = new JTextField(5);
        JTextField race = new JTextField(5);
        JTextField religion = new JTextField(5);

        myPanel.add(new JLabel("First Name: "));
        myPanel.add(firstName);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Middle Name (N/A if none): "));
        myPanel.add(middleName);
        myPanel.add(new JLabel("Last Name: "));
        myPanel.add(lastName);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Alias (N/A if none): "));
        myPanel.add(alias);
        myPanel.add(new JLabel("Date of Birth (month/day/year): "));
        myPanel.add(birthDate);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Country of Birth: "));
        myPanel.add(countryOfBirth);
        myPanel.add(new JLabel("City of Birth: "));
        myPanel.add(cityOfBirth);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("City of Birth: "));
        myPanel.add(townOfBirth);
        myPanel.add(new JLabel("Race :"));
        myPanel.add(race);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Religion (N/A if none):"));
        myPanel.add(religion);
        //String resulting= JOptionPane.showInputDialog(null,myPanel,"Please Enter the character's information" ,JOptionPane.OK_CANCEL_OPTION);
         //JOptionPane pane= new JOptionPane();
         int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter the character's information", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            while(firstName.getText().equals("") || middleName.getText().equals("") || lastName.getText().equals("") ||
                    alias.getText().equals("") || birthDate.getText().equals("") ||
                    countryOfBirth.getText().equals("") || cityOfBirth.getText().equals("") || townOfBirth.getText().equals("") ||
                    race.getText().equals("") || religion.getText().equals("")){
                JOptionPane.showMessageDialog(null, "please fill out all the information or enter N/A if one is not available!","Message", JOptionPane.WARNING_MESSAGE);
                result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Please Enter the character's information", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.CANCEL_OPTION ) {
                    return null;
                }
            }
        } else{
            return null;
        }

         return new Person(firstName.getText(), middleName.getText(), lastName.getText(), alias.getText(),
                 birthDate.getText(),countryOfBirth.getText(), cityOfBirth.getText(), townOfBirth.getText(), race.getText(), religion.getText());

     }
    private Person autoInformationFiller(Person personToBeFilled){
        JPanel myPanel= new JPanel();
        JTextField firstName = new JTextField(5);
        firstName.setText(personToBeFilled.getFirstName());
        JTextField middleName = new JTextField(5);
        middleName.setText(personToBeFilled.getMiddleName());
        JTextField lastName = new JTextField(5);
        lastName.setText(personToBeFilled.getLastName());
        JTextField alias = new JTextField(5);
        alias.setText(personToBeFilled.getAlias());
        JTextField birthDate = new JTextField(5);
        birthDate.setText(personToBeFilled.getBirthDate());
        JTextField countryOfBirth = new JTextField(5);
        countryOfBirth.setText(personToBeFilled.getCountryOfBirth());
        JTextField cityOfBirth = new JTextField(5);
        cityOfBirth.setText(personToBeFilled.getCityOfBirth());
        JTextField townOfBirth = new JTextField(5);
        townOfBirth.setText(personToBeFilled.getTownOfBirth());
        JTextField race = new JTextField(5);
        race.setText(personToBeFilled.getRace());
        JTextField religion = new JTextField(5);
        religion.setText(personToBeFilled.getReligion());

        myPanel.add(new JLabel("First Name: "));
        myPanel.add(firstName);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Middle Name (N/A if none): "));
        myPanel.add(middleName);
        myPanel.add(new JLabel("Last Name: "));
        myPanel.add(lastName);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Alias (N/A if none): "));
        myPanel.add(alias);
        myPanel.add(new JLabel("Date of Birth (month/day/year): "));
        myPanel.add(birthDate);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Country of Birth: "));
        myPanel.add(countryOfBirth);
        myPanel.add(new JLabel("City of Birth: "));
        myPanel.add(cityOfBirth);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("City of Birth: "));
        myPanel.add(townOfBirth);
        myPanel.add(new JLabel("Race :"));
        myPanel.add(race);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Religion (N/A if none):"));
        myPanel.add(religion);
        JOptionPane pane= new JOptionPane();
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter the character's information", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            while(firstName.getText().equals("") || middleName.getText().equals("") || lastName.getText().equals("") ||
                    alias.getText().equals("") || birthDate.getText().equals("") ||
                    countryOfBirth.getText().equals("") || cityOfBirth.getText().equals("") || townOfBirth.getText().equals("") ||
                    race.getText().equals("") || religion.getText().equals("")){
                JOptionPane.showMessageDialog(null, "please fill out all the information or enter N/A if one is not available!","Message", JOptionPane.WARNING_MESSAGE);
                result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Please Enter the character's information", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.CANCEL_OPTION ) {
                    return null;
                }
            }
        } else{
            return null;
        }
        //insert(firstName.getText(), newPerson);
        return new Person(firstName.getText(), middleName.getText(), lastName.getText(), alias.getText(),
                birthDate.getText(),countryOfBirth.getText(), cityOfBirth.getText(), townOfBirth.getText(), race.getText(), religion.getText());

    }

    /**
     * This method converts the TreeMap to an arraylist. The arraylist will be used in GUI.
     *
     * @return the array list
     */
    public ArrayList<String []> mapToList(){
        units = new  ArrayList<String []>();
        if (!directory.isEmpty()) {
            Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
            for (Map.Entry<String, List<Person>> entry : entries) {
                for (Person person : entry.getValue()) {
                    String [] innerElements={person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getAlias(),
                            person.getBirthDate(), person.getCountryOfBirth(), person.getCityOfBirth(), person.getTownOfBirth(),
                            person.getRace(), person.getReligion()};
                    units.add(innerElements);
                }

            }
        }
        return units;
    }

    private ArrayList<String []> mapToListInClass(TreeMap<String, List<Person>> temp){
        units = new  ArrayList<String []>();
        if (!temp.isEmpty()) {
            Set<Map.Entry<String, List<Person>>> entries = temp.entrySet();
            for (Map.Entry<String, List<Person>> entry : entries) {
                for (Person person : entry.getValue()) {
                    String [] innerElements={person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getAlias(),
                            person.getBirthDate(), person.getCountryOfBirth(), person.getCityOfBirth(), person.getTownOfBirth(),
                            person.getRace(), person.getReligion()};
                    units.add(innerElements);
                }

            }
        }
        return units;
    }

}

