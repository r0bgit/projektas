package filmai.controller;

import filmai.model.RequestFormDAO;
import filmai.model.User;
import filmai.model.UserDAO;
import filmai.utils.Password;
import filmai.utils.Validation;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    Button login_button;
    @FXML
    Button button_register;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label login_error;

    public void onLoginButtonClick(ActionEvent actionEvent) {
        // Aprašomas mygtuko paspaudimas
        // Aprašomi veiksmai, kurie bus atlikti paspaudus mygtuką
        // Atliekama vartotojo įvestų duomenų validacija
        if (Validation.isValidUsername(username.getText()) && Validation.isValidPassword(password.getText())) {
            try {
                // TODO: Kadangi vartotojo slaptažodis duomenų bazėje yra šifruotas
                // O vartotojas įveda plain text (Nešifruotą)
                // Reikia patikrinti ar jie sutampa (password.checkPassword(vartotojo įvestas, šifruotas db))
                // Todėl reikės gauti vartotojo šifruotą slaptažodį iš db ir perduoti aukščiau parašytam metodui
                // Jeigu jie sutaps vartotojas yra autentifikuotas
                User vartotojas = UserDAO.userLogin(username.getText(), password.getText());
                if (vartotojas == null){
                    login_error.setText("Wrong username or password");
                } else {
                    // Konstruojamas user singleton objektas, kad galėtume perduoti informaciją tarp skirtingų langų
                    // Kuriant objekto kopijas nepavyks dasboard lange atvaizduoti prisijungusio vartotojo informacijos
                    User vartotojasSingleton = User.getInstance();
                    vartotojasSingleton.setUserID(vartotojas.getUserID());
                    vartotojasSingleton.setUsername(vartotojas.getUsername());
                    vartotojasSingleton.setPassword(vartotojas.getPassword());
                    vartotojasSingleton.setEmail(vartotojas.getEmail());
                    vartotojasSingleton.setAdmin(vartotojas.isAdmin());
                    goToDashboard(actionEvent);
                }
            } catch (SQLException throwables) {
                login_error.setText("Klaida ok");
            }
            // TODO: Jeigu vartotojas praeina front-end validacija, tada darome back-end validacija

            // Tai yra: kreiptis į duomenų bazę ir patikrinti ar egzistuoja toks vartotojas (username ir password)
            // Jeigu egzistuoja sukuriamas objektas
            // Jei gerai įvesti duomenys reikės pereiti iš login ekrano į pagrindinį langą
        } else {
            login_error.setText("Wrong username or password");
        }
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) {
        // Mes esame kontrolerio aplanke, bet vaizdo čia nėra, todėl mes turime pakilti vienu aplanku į viršų (../)
        try {
            // Sukuriamas dashboard langas
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/register.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene((root), 550, 450));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToDashboard(Event event) {
        // Mes esame kontrolerio aplanke, bet vaizdo čia nėra, todėl mes turime pakilti vienu aplanku į viršų (../)
        try {
            // Sukuriamas dashboard langas
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 800));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (Validation.isValidUsername(username.getText()) && Validation.isValidPassword(password.getText())) {
                // Jei gerai įvesti duomenys reikės pereiti iš login ekrano į pagrindinį langą
                try {
                    // TODO: Kadangi vartotojo slaptažodis duomenų bazėje yra šifruotas
                    // O vartotojas įveda plain text (Nešifruotą)
                    // Reikia patikrinti ar jie sutampa (password.checkPassword(vartotojo įvestas, šifruotas db))
                    // Todėl reikės gauti vartotojo šifruotą slaptažodį iš db ir perduoti aukščiau parašytam metodui
                    // Jeigu jie sutaps vartotojas yra autentifikuotas
                    User vartotojas = UserDAO.userLogin(username.getText(), password.getText());
                    if (vartotojas == null){
                        login_error.setText("Wrong username or password");
                    } else {
                        // Konstruojamas user singleton objektas, kad galėtume perduoti informaciją tarp skirtingų langų
                        // Kuriant objekto kopijas nepavyks dasboard lange atvaizduoti prisijungusio vartotojo informacijos
                        User vartotojasSingleton = User.getInstance();
                        vartotojasSingleton.setUserID(vartotojas.getUserID());
                        vartotojasSingleton.setUsername(vartotojas.getUsername());
                        vartotojasSingleton.setPassword(vartotojas.getPassword());
                        vartotojasSingleton.setEmail(vartotojas.getEmail());
                        vartotojasSingleton.setAdmin(vartotojas.isAdmin());
                        goToDashboard(keyEvent);
                    }
                } catch (SQLException throwables) {
                    login_error.setText("Klaida ok");
                }
            } else {
                login_error.setText("Wrong username or password");
            }
        }
    }
}
