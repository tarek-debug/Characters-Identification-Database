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

public class Database {

    private Person character;
    private TreeMap<String, List<Person>> directory;
    private TreeMap<String, List<Person>> searchDirectory;
    private String name;
    Vector<ListPersons> items;
    private ArrayList<String []> units;

    public Database(){
        directory = new TreeMap<String, List<Person>>();
        items= new Vector<ListPersons>();
        units= new ArrayList<String []>();
    }
    public Database(String name) {
        this.name = name;
        directory = new TreeMap<String, List<Person>>();
        items= new Vector<ListPersons>();
        units= new ArrayList<String []> ();

    }

    public TreeMap<String, List<Person>> getDirectory() {
        return directory;
    }

    public String getName() {
        return name;
    }


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

    public void clearDatabase(){
        directory.clear();
    }

    public void lookup(String name) {
        if (directory.isEmpty()) {
            System.out.println("The database is currently empty.");
        } else if (directory.containsKey(name)) {
            Iterator itr = directory.get(name).iterator();
            while (itr.hasNext()) {
                //System.out.println(getKey() + "->" + itr.next());
                return;
            }
        } else {
            System.out.println("It doesn't exist.");
        }
    }

    public void display() {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }
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

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }
        return false;
    }


    public ArrayList<String []> displayBasedOnCountry(String country){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCountryOfBirth().equals(country)) {
                        searchDirectory.put(current.getFirstName(),entry.getValue());
                        //List<Person> found = directory.get(name);
                        //found.add(current);
                        //searchDirectory.e.put(found);
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        return mapToListInClass(searchDirectory);
    }
    void deleteBasedOnCountry(String country){
        //searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCountryOfBirth().equals(country)) {
                        //searchDirectory.put(current.getFirstName(),entry.getValue());
                        //List<Person> found = directory.get(name);
                        //found.add(current);
                        //directory.remove(current.getFirstName(),entry.getValue());
                        itr.remove();

                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

    }
    public boolean checkCity(String city) throws IOException {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCityOfBirth().equals(city)) {
                        return true;
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }
        return false;
    }


    public ArrayList<String []> displayBasedOnCity(String city){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCityOfBirth().equals(city)) {
                        searchDirectory.put(current.getFirstName(),entry.getValue());
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        return mapToListInClass(searchDirectory);
    }
    void deleteBasedOnCity(String city){
        //searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getCityOfBirth().equals(city)) {
                        //searchDirectory.put(current.getFirstName(),entry.getValue());
                        //List<Person> found = directory.get(name);
                        //found.add(current);
                        //directory.remove(current.getFirstName(),entry.getValue());
                        itr.remove();

                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

    }
    public boolean checkRace(String race) throws IOException {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getRace().equals(race)) {
                        return true;
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }
        return false;
    }


    public ArrayList<String []> displayBasedOnRace(String race){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getRace().equals(race)) {
                        searchDirectory.put(current.getFirstName(),entry.getValue());

                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        return mapToListInClass(searchDirectory);
    }

    public ArrayList<String []> deleteBasedOnRace(String race){
        //searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getRace().equals(race)) {
                        //directory.remove(current.getFirstName(),entry.getValue());
                        itr.remove();

                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        return mapToListInClass(searchDirectory);
    }

    public boolean checkReligion(String religion) throws IOException {
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getReligion().equals(religion)) {
                        return true;
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }
        return false;
    }


    public ArrayList<String []> displayBasedOnReligion(String religion){
        searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getReligion().equals(religion)) {
                        searchDirectory.put(current.getFirstName(),entry.getValue());
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        return mapToListInClass(searchDirectory);
    }
    public ArrayList<String []> deleteBasedOnReligion(String religion){
        //searchDirectory= new TreeMap<String, List<Person>> ();
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getReligion().equals(religion)) {
                        //directory.remove(current.getFirstName(),entry.getValue());
                        itr.remove();
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }
        /*
               Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            Iterator itr = entry.getValue().iterator();
            while (itr.hasNext()) {
                Person current = (Person) itr.next();
                {
                    if (current.getRace().equals(race)) {
                        directory.remove(current.getFirstName(),entry.getValue());
                    }
                }

            }

            //System.out.println(entry.getKey() + "->" + entry.getValue());
        }

         */

        return mapToListInClass(searchDirectory);
    }

/*
    public int numberOfEntries() {
        int size = 0;
        Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            for (Person person : entry.getValue()) {
                ++size;
            }

        }
        return size;

    }

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
        //return newOne;
        return false;
    }

    // finish load method
    public boolean load(String fileName) throws IOException {
        //Database loadedDatabase = new Database(name);
        //loadedDatabase.setName(fileName);
        //List<String> results = new ArrayList<String>();

        List<Person> newList = new ArrayList<>();
        Person toFind= new Person();
        //newList.add(newPerson);
        Path path = Paths.get("Databases\\" + name +".txt");
        //String[] words=null;  //Intialize the word Array

        if(Files.exists(path)) {
            name = fileName;
            directory = new TreeMap<String, List<Person>>();
            File toLoad = new File("Databases\\" + fileName + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(toLoad));

            // Declaring a string variable
            String st;
            int lineNumber = 1;
            boolean foundElement = false;
            // Condition holds true till
            // there is character in a string
            Person toLoadFromFile = new Person();
            Scanner scanner = new Scanner(toLoad);

            /*
            s.useDelimiter(":;");
            while(s.hasNext()) {
                System.out.println(s.next());
            }
            */
            String text = scanner.useDelimiter("\\A").next();
            scanner.close();
            textExtractor (toLoadFromFile, text);
            Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
            for (Map.Entry<String, List<Person>> entry : entries) {
                System.out.println(entry.getKey() + "->" + entry.getValue());
            }
            //System.out.println(toLoadFromFile.toString());

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
                toLoadTo.setFirstName(m.group(1));

                counter++;
            } else if (counter==2){
                toLoadTo.setMiddleName(m.group(1));

                counter++;
            }else if (counter==3){
                toLoadTo.setLastName(m.group(1));

                counter++;
            }else if (counter==4){
                toLoadTo.setAlias(m.group(1));

                counter++;
            }else if (counter==5){
                toLoadTo.setBirthDate(m.group(1));

                counter++;
            }else if (counter==6){
                toLoadTo.setCountryOfBirth(m.group(1));

                counter++;
            }else if (counter==7){
                toLoadTo.setCityOfBirth(m.group(1));

                counter++;
            }else if (counter==8){
                toLoadTo.setTownOfBirth(m.group(1));

                counter++;
            }else if (counter==9){
                toLoadTo.setRace(m.group(1));

                counter++;
            }else if (counter==10){
                toLoadTo.setReligion(m.group(1));
                insert(toLoadTo.getFirstName() , toLoadTo);
                //System.out.println(toLoadTo.toString());
                toLoadTo= new Person();
                counter=1;
            }

            System.out.println(m.group(1));
        }
    }





        /*
        File[] files = new File("\\Identities_Database\\Databases").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {

            }
        }
        }
        */



    public static ArrayList<String>  loadAllDatabases() throws IOException {

        createNewFile ("location_finder");
        File toLoad = new File("Databases\\location_finder.txt");
        Path path = Paths.get("Databases\\location_finder.txt");

        System.out.println(toLoad.getAbsolutePath());
        StringBuffer sb= new StringBuffer(toLoad.getAbsolutePath());
        //invoking the method
        for (int i = 0; i< 20 ; i++){
            sb.deleteCharAt(sb.length()-1);

        }
        //prints the string after deleting the character
        //System.out.println(String.valueOf(sb));
        removeDatabase("location_Finder");
        File directoryPath = new File (String.valueOf(sb));


        //List of all files and directories
        String[] contents = directoryPath.list();

        //System.out.println("List of files and directories in the specified directory:");
        //assert contents != null;
        //System.out.println(contents[0]);
        //System.out.println(content);
        assert contents != null;
        return new ArrayList<String>(Arrays.asList(contents));
        /*
        results.add("hi");
        File[] files = new File("\\Databases").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.
        System.out.println(Arrays.deepToString(results.toArray()));

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                //results.add(file.getName());
                System.out.print(file.getName());
            }
        }
        */

        //return results;
    }
    public static void removeDatabase(String fileName) throws SecurityException{
        // scan the files for the name of the database and once it finds it, it deletes the file

        File file
                = new File("Databases\\" + fileName +".txt");
        //File toRemove= new File(file.getAbsolutePath());

        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }

        /*
        File[] files = new File("\\Identities_Database\\Databases").listFiles();
        assert files != null;
        for (File file : files) {
            if (file.getName().equals(database.getName())) {
                    file.delete();
            }
        }

         */
    }

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

        /*
        assert files != null;
        for (File file : files) {
            if (file.isFile() && database.getName().equals(file.getName())) {
                replace= new File(file.getName());
                file.delete();
            }
        }
        */

    }

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
         JOptionPane pane= new JOptionPane();
         int result = pane.showConfirmDialog(null, myPanel,
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
         Person newPerson= new Person(firstName.getText(), middleName.getText(), lastName.getText(), alias.getText(),
                 birthDate.getText(),countryOfBirth.getText(), cityOfBirth.getText(), townOfBirth.getText(), race.getText(), religion.getText());
         //insert(firstName.getText(), newPerson);
         return newPerson;

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
        //String resulting= JOptionPane.showInputDialog(null,myPanel,"Please Enter the character's information" ,JOptionPane.OK_CANCEL_OPTION);
        JOptionPane pane= new JOptionPane();
        int result = pane.showConfirmDialog(null, myPanel,
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
        Person newPerson= new Person(firstName.getText(), middleName.getText(), lastName.getText(), alias.getText(),
                birthDate.getText(),countryOfBirth.getText(), cityOfBirth.getText(), townOfBirth.getText(), race.getText(), religion.getText());
        //insert(firstName.getText(), newPerson);
        return newPerson;

    }

    public Vector<ListPersons> mapToVector(){
        items = new Vector<ListPersons>();
        if (!directory.isEmpty()) {
            Set<Map.Entry<String, List<Person>>> entries = directory.entrySet();
            for (Map.Entry<String, List<Person>> entry : entries) {
                for (Person person : entry.getValue()) {
                    items.add(new ListPersons(entry.getKey(), person));
                }

            }
        }
        return items;
    }

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
                    //items.add(new ListPersons(entry.getKey(), person));
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
                    //items.add(new ListPersons(entry.getKey(), person));
                }

            }
        }
        return units;
    }

}

