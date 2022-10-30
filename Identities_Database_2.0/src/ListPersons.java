/**
 * File: GUICardLayout.java
 * This class creates a list of the person class, which will be used in the Tree Map in the Database class
 * Proper Javadoc are missing, they will be added later
 * @author Tarek Solamy (Alsolame)
 * @version 2.0 10/30/2022
 */


public class ListPersons {
        private String name;
        private Person character;

        ListPersons(String name,  Person character) {
            this.name = name;
            this.character = character;
        }

        @Override
        public String toString() {
            // You can change this to suite the presentation of a list item
            String information= "First Name: "+ character.getFirstName()+ "." + System.lineSeparator() +
                                "Middle Name: " + character.getMiddleName() + "." + System.lineSeparator() +
                                "Last Name: " + character.getLastName() + "." + System.lineSeparator() +
                                "Alias: " + character.getAlias() + "." + System.lineSeparator() +
                                "Data of Birth: " + character.getBirthDate() + "." + System.lineSeparator() +
                                "Country of Birth: " + character.getCountryOfBirth() + "." + System.lineSeparator() +
                                "City of Birth: " + character.getCityOfBirth() + "." + System.lineSeparator() +
                                "Town of Birth: " + character.getTownOfBirth() + "." + System.lineSeparator() +
                                "Race: " + character.getRace() + "." + System.lineSeparator() +
                                "Religion: "  + character.getReligion() + "." + System.lineSeparator();
            return information;
        }
}
