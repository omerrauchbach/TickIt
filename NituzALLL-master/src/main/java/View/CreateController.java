package View;

import Controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CreateController extends AView{

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField city;
    @FXML
    private DatePicker birthdate ;



    /**
     *
     * @param event
     * @throws IOException
     * this function create user on the database
     */
    @FXML
    private void createUser(ActionEvent event) throws IOException {
        String usernameS, passwordS, confirmS, firstS, lastS, cityS, dateS;
        passwordS = password.getText();
        confirmS = confirmpassword.getText();
        usernameS = username.getText();
        firstS = firstname.getText();
        lastS = lastname.getText();
        cityS = city.getText();

        if(birthdate.getValue()!=null) {
            dateS = birthdate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Controller controller=(Controller)this.controller;
            controller.Create(usernameS, passwordS, confirmS, firstS, lastS, dateS, cityS);
            this.ShowAlert();
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("At least one of the fields are empty.\nFill all fields and try again ");
            errorAlert.showAndWait();
        }


    }

}
