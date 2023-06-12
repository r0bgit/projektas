package filmai.controller;

import com.sun.xml.internal.bind.v2.TODO;
import filmai.model.RequestForm;
import filmai.model.RequestFormDAO;
import filmai.model.User;
import filmai.utils.Validation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardController {
    @FXML
    TextField id;
    @FXML
    private Label message;
    @FXML
    private TextField category;
    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField rating;
    @FXML
    private Label username_label;
    @FXML
    private Label role_label;
    //    @FXML
    //    private Button return_button;
    @FXML
    private Button button_search;
    @FXML
    private Button button_edit;
    @FXML
    private Button button_delete;
    @FXML
    private Button button_create;
    @FXML
    private TableView table;
    private ResultSet rsAllEntries;
    private ObservableList<ObservableList> data = FXCollections.observableArrayList();
    // Jei nerodo ikonėlių prie FXML elementų patikrinti ar FXML'e yra nurodytas kontroleris

    public void initialize() {
        this.username_label.setText(User.getInstance().getUsername());
        if (User.getInstance().isAdmin()) {
            this.role_label.setText("Admin");
        } else {
            this.role_label.setText("User");
            this.button_edit.setDisable(true);
            this.button_delete.setDisable(true);
        }
    }
    /**
     * Funkcija grąžinantį vartotoją į login langą
     */
    public void goToLogin(ActionEvent actionEvent) {
        try {
            // Sukuriamas dashboard langas
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/login.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 550, 450));
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public void onCreateAction(ActionEvent actionEvent) {
        //this.message.setText("");
        String category = "";
        if (Validation.isValidCategory(this.category.getText())) {
            category = this.category.getText();
        } else {
            this.message.setText("Category is required");
            return;
        }

        String title = "";
        if (Validation.isTitleValid(this.title.getText())) {
            title = this.title.getText();
        } else {
            this.message.setText("Title is not valid");
            return;
        }

        String description = "";
        if (Validation.isDesciptionValid(this.description.getText())) {
            description = this.description.getText();
        } else {
            this.message.setText("Description is not valid");
            return;
        }

        String rating = "";
        if (Validation.isRatingValid(this.rating.getText())) {
            rating = this.rating.getText();
        } else {
            this.message.setText("Rating is not valid");
            return;
        }


        try {
            RequestFormDAO.create(new RequestForm(category, title, description, rating, User.getInstance().getUserID()));
            this.message.setTextFill(Color.GREEN);
            this.message.setText("Successfully added new entry to database");
        } catch (SQLException throwables) {
            this.message.setText("Failed to add an entry to database");
            throwables.printStackTrace();
        }
    }

    public void onActionEdit(ActionEvent actionEvent) {
        int id = 0;
        if (Validation.isIdValid(this.id.getText())) {
            id = Integer.parseInt(this.id.getText());
        } else {
            this.message.setText("Id is not valid");
        }

        String category = "";
        if (Validation.isValidCategory(this.category.getText())) {
            category = this.category.getText();
        } else {
            this.message.setText("Category is required");
            return;
        }

        String title = "";
        if (Validation.isTitleValid(this.title.getText())) {
            title = this.title.getText();
        } else {
            this.message.setText("Title is not valid");
            return;
        }

        String description = "";
        if (Validation.isDesciptionValid(this.description.getText())) {
            description = this.description.getText();
        } else {
            this.message.setText("Description is not valid");
            return;
        }

        String rating = "";
        if (Validation.isRatingValid(this.rating.getText())) {
            rating = this.rating.getText();
        } else {
            this.message.setText("Rating is not valid");
            return;
        }

        try {


            RequestFormDAO.update(new RequestForm(id, category, title, description, rating, 1));
            this.message.setTextFill(Color.GREEN);
            this.message.setText("Successfully updated entry");
        } catch (SQLException throwables) {
            this.message.setText("Failed to update entry");
        }
    }

    public void onActionDelete(ActionEvent actionEvent) {
        if (Validation.isIdValid(this.id.getText())) {
            try {
                RequestFormDAO.delete(Integer.parseInt(this.id.getText()));
                this.message.setTextFill(Color.GREEN);
                this.message.setText("Form deleted successfully");
            } catch (SQLException throwables) {
                this.message.setText("Failed to delete a request");
            }
        } else {
            this.message.setText("ID is not valid");
        }
    }

    public void onSearchAction(ActionEvent actionEvent) {
        try {
            String title = this.title.getText();

            updateTableFromDB(title);
        } catch (SQLException throwables) {
            this.message.setText("Search failed");
            throwables.printStackTrace();
        }
    }

    private void updateTableFromDB(String title) throws SQLException {
        this.rsAllEntries = RequestFormDAO.search(title);
        this.fetchColumnList();
        this.fetchRowList();
    }

    //only fetch columns
    private void fetchColumnList() {
        try {
            table.getColumns().clear();
            //SQL FOR SELECTING ALL OF ENTRIES
            for (int i = 0; i < rsAllEntries.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rsAllEntries.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().removeAll(col);
                table.getColumns().addAll(col);
            }
        } catch (SQLException e) {
            this.message.setText("Failure in getting all entries");
        }
    }

    //fetches rows and data from the list
    private void fetchRowList() {
        try {
            data.clear();
            while (rsAllEntries.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rsAllEntries.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rsAllEntries.getString(i));
                }
                data.add(row);
            }
            //Connects table with list
            table.setItems(data);
        } catch (SQLException ex) {
            this.message.setText("Failure in getting all entries");
        }
    }
}
