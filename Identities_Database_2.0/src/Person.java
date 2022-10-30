/**
 * File: GUICardLayout.java
 * This class creates the Person will all the variables needed and the setters and getters required
 * Proper Javadoc are missing, they will be added later
 * @author Tarek Solamy (Alsolame)
 * @version 2.0 10/30/2022
 */


public class Person {

    private String firstName;
    private String lastName;
    private String middleName;
    private String alias;
    private String birthDate;
    private String countryOfBirth;
    private String cityOfBirth;
    private String townOfBirth;
    private String race;
    private String religion;

    public Person() {
    }

    public Person(String firstName, String middleName, String lastName, String alias, String birthDate,
                  String countryOfBirth, String cityOfBirth, String townOfBirth, String race, String religion) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.alias = alias;
        this.birthDate = birthDate;
        this.countryOfBirth = countryOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.townOfBirth=townOfBirth;
        this.race = race;
        this.religion = religion;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public String getTownOfBirth() {
        return townOfBirth;
    }

    public void setTownOfBirth(String townOfBirth) {
        this.townOfBirth = townOfBirth;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
    @Override
    public String toString() {
        // You can change this to suite the presentation of a list item
        String information= "First Name: "+ getFirstName()+ "." + System.lineSeparator() +
                "Middle Name: " + getMiddleName() + "." + System.lineSeparator() +
                "Last Name: " + getLastName() + "." + System.lineSeparator() +
                "Alias: " + getAlias() + "." + System.lineSeparator() +
                "Date of Birth: " + getBirthDate() + "." + System.lineSeparator() +
                "Country of Birth: " + getCountryOfBirth() + "." + System.lineSeparator() +
                "City of Birth: " + getCityOfBirth() + "." + System.lineSeparator() +
                "Town of Birth: " + getTownOfBirth() + "." + System.lineSeparator() +
                "Race: " + getRace() + "." + System.lineSeparator() +
                "Religion: "  + getReligion() + "." + System.lineSeparator();
        return information;
    }
}
