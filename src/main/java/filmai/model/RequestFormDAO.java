package filmai.model;

import java.sql.*;

public class RequestFormDAO {
    private static final String URL = "jdbc:mysql://localhost/filmai";
    private static final String[] prisijungimas = new String[]{"root", ""} ;

    public static void create(RequestForm requestForm) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO request_form (String category, String title, String description, String rating, int userId) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, requestForm.getCategory());
        preparedStatement.setString(2, requestForm.getTitle());
        preparedStatement.setString(3, requestForm.getDescription());
        preparedStatement.setString(4, requestForm.getRating());
        preparedStatement.setInt(5, requestForm.getUserId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM request_form WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void update(RequestForm requestForm) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE request_form SET category = ?, title = ?, description = ?, rating = ?, WHERE id = ?");
        preparedStatement.setString(1, requestForm.getCategory());
        preparedStatement.setString(2, requestForm.getTitle());
        preparedStatement.setString(3, requestForm.getDescription());
        preparedStatement.setString(4, requestForm.getRating());
        preparedStatement.setInt(5, requestForm.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static ResultSet search(String title) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        // TODO: Jeigu title yra tusčias, suformuoti select kuris grąžins visus duomenis esančius lentelėje. (Nereikia where)
        // tačiau jeigu title nėra tusčias vykdyti select kuris aprašytas eilute žemiau
        PreparedStatement preparedStatement;
        if (User.getInstance().isAdmin()) { // Jeigu administratorius - atvaizduoti visų vartotojų įrašus
            if (title.isEmpty()){
                preparedStatement = connection.prepareStatement("SELECT * FROM request_form");
            } else {
                preparedStatement = connection.prepareStatement("SELECT * FROM request_form WHERE title LIKE ?");
                preparedStatement.setString(1, title);
            }
        } else { // Jeigu vartotojas - atvaizduoti tik jo kurtus įrašus
            if (title.isEmpty()){
                preparedStatement = connection.prepareStatement("SELECT * FROM request_form WHERE user_id = ?");
                preparedStatement.setInt(1, User.getInstance().getUserID());
            } else {
                preparedStatement = connection.prepareStatement("SELECT * FROM request_form WHERE title LIKE ? AND user_id = ?");
                preparedStatement.setString(1, title);
                preparedStatement.setInt(2, User.getInstance().getUserID());
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

}
