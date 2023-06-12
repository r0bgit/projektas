package filmai.utils;

public class Password {
    private static int workLoad = 12;

    /**
     * Funkcija užšifruojanti vartotojo įvėstą slaptažodį
     * @param passwordPlainText vartotojo įvestas slaptažodis
     * @return užšifruotas slaptažodis (su druska)
     */
    public static String hashPassword(String passwordPlainText) {
        String salt = BCrypt.gensalt(workLoad);
        String hashedPassword = BCrypt.hashpw(passwordPlainText, salt);
        return hashedPassword;
    }

    /**
     * Funkcija patikrinant ar vartotojo 5vestas slaptažodis atitinka užšifruotą slaptažodį
     * @param passwordPlainText vartotojo įvestas slaptažodis
     * @param storedHash užkoduotas slaptažodis
     * @return true - jeigu atitinka, false - priešingu atveju
     */
    public static boolean checkPassword(String passwordPlainText, String storedHash) {
        boolean passwordVarified = false;
        if (storedHash == null || !storedHash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Neteisinga bCrypt koduotė palyginimui");
        }
        passwordVarified = BCrypt.checkpw(passwordPlainText, storedHash);
        return passwordVarified;
    }
}