package filmai.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    // Apsirašomi validacijos šablonai pgal kuruos tikrinsime vartotojo įestus duomenis
    public static final String USERNAME_REGEX_PATTERN = "^[a-zA-Z0-9]{5,13}$";
    public static final String PASSWORD_REGEX_PATTERN = "^[a-zA-Z-0-9!@#$%]{5,13}$";
    public static final String USER_EMAIL_REGEX_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,50}$";
    public static final String CATEGORY_REGEX_PATTERN = "^[a-zA-Z0-9., ]{5,128}$";
    public static final String TITLE_REGEX_PATTERN = "^[a-zA-Z0-9., ]{5,128}$";
    public static final String DESCRIPTION_REGEX_PATTERN = "^[a-zA-Z0-9,.+*@#?! ]{5,512}$";
    public static final String ID_REGEX_PATTERN =  "^[0-9]{1,15}$";
    public static final String RATING_REGEX_PATTERN =  "^[0-9]{1,15}$";

    /**
     * Funkcija tikrinanati ar varototjo įvesti duoemnys prisijungimo vardui atitinka validacijos šabloną
     * @param username vartotojo įvestas prisijungimo vardas
     * @return true - jeigu vartotojo įvestas vardas atitinka šabloną, false - priešingu atveju
     */
    public static boolean isValidUsername(String username) {
        // Pagal 7 eilutes apsirašytą šabloną sukuriamas sukuriamos taisyklės
        Pattern pattern = Pattern.compile(USERNAME_REGEX_PATTERN);
        // Vartotojo įvestas prisijungimo vardas palyginamas su aukščiau sukurtom taisyklėm
        Matcher matcher = pattern.matcher(username);
        // true - jeigu vartotojo įvestas vardas atitinka šabloną, false - priešingu atveju
        return matcher.find();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(USER_EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean isIdValid(String id) {
        Pattern pattern = Pattern.compile(ID_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(id);
        return matcher.find();
    }

    public static boolean isValidCategory(String category) {
        Pattern pattern = Pattern.compile(CATEGORY_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(category);
        return matcher.find();
    }
    // Title laukelio ilgis privalo būti nuo 5 iki 128 simbolių, gali būti raidės, gali būti skaičiai
    // Spec simboliai: , . -
    public static boolean isTitleValid(String title) {
        Pattern pattern = Pattern.compile(TITLE_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(title);
        return matcher.find();
    }

    // Description laukelio ilgis nuo 5 iki 512
    public static boolean isDesciptionValid(String description) {
        Pattern pattern = Pattern.compile(DESCRIPTION_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(description);
        return matcher.find();
    }

    public static boolean isRatingValid(String rating) {
        Pattern pattern = Pattern.compile(RATING_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(rating);
        return matcher.find();
    }
}

