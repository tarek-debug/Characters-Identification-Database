/**
 * File: GUICardLayout.java
 * This class creates the Person will all the variables needed and the setters and getters required.
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

    /**
     * Instantiates a new Person.
     */
    public Person() {
    }

    /**
     * Instantiates a new Person.
     *
     * @param firstName      the first name
     * @param middleName     the middle name
     * @param lastName       the last name
     * @param alias          the alias
     * @param birthDate      the birth date
     * @param countryOfBirth the country of birth
     * @param cityOfBirth    the city of birth
     * @param townOfBirth    the town of birth
     * @param race           the race
     * @param religion       the religion
     */
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


    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets middle name.
     *
     * @param middleName the middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets alias.
     *
     * @param alias the alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets country of birth.
     *
     * @return the country of birth
     */
    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    /**
     * Sets country of birth.
     *
     * @param countryOfBirth the country of birth
     */
    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    /**
     * Gets city of birth.
     *
     * @return the city of birth
     */
    public String getCityOfBirth() {
        return cityOfBirth;
    }

    /**
     * Sets city of birth.
     *
     * @param cityOfBirth the city of birth
     */
    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    /**
     * Gets town of birth.
     *
     * @return the town of birth
     */
    public String getTownOfBirth() {
        return townOfBirth;
    }

    /**
     * Sets town of birth.
     *
     * @param townOfBirth the town of birth
     */
    public void setTownOfBirth(String townOfBirth) {
        this.townOfBirth = townOfBirth;
    }

    /**
     * Gets race.
     *
     * @return the race
     */
    public String getRace() {
        return race;
    }

    /**
     * Sets race.
     *
     * @param race the race
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * Gets religion.
     *
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Sets religion.
     *
     * @param religion the religion
     */
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
