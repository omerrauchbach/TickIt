package View;

import Controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Vector;
import Model.User;
public class UpdateController extends AView{
    @FXML
    private GridPane resultsView;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField conpassword;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField city;
    @FXML
    private TextField birthdate;
    @FXML
    private Label sorrymessage;


    User user;


    public void setUser(User user) {
        this.user= user;
    }

    public void start(){
        Controller controller=(Controller)this.controller;
        Vector<String> ans = controller.Read(user.getUserName());
        sorrymessage.setVisible(false);
        resultsView.setVisible(false);
        if (ans.size() != 0) {
            username.setText(ans.elementAt(0));
            password.setText(ans.elementAt(1));
            conpassword.setText(ans.elementAt(1));
            firstname.setText(ans.elementAt(2));
            lastname.setText(ans.elementAt(3));
            city.setText(ans.elementAt(4));
            birthdate.setText(ans.elementAt(5));
            resultsView.setVisible(true);
        } else {
            sorrymessage.setVisible(true);
        }
    }

    public void update(ActionEvent actionEvent) {
        String usernameS,passwordS,conpasswordS,firstnameS,lastnameS,birthdateS,cityS;

        usernameS=username.getText();
        passwordS=password.getText();
        conpasswordS = conpassword.getText();
        firstnameS=firstname.getText();
        lastnameS=lastname.getText();
        birthdateS=birthdate.getText();
        cityS=city.getText();


            Controller controller=(Controller)this.controller;
            controller.Delete2(user.getUserName());
            controller.Create(usernameS, passwordS,conpasswordS, firstnameS, lastnameS, birthdateS, cityS);
            if(alert.getAlertType().equals(Alert.AlertType.ERROR)||alert.getAlertType().equals(Alert.AlertType.WARNING)){
                this.ShowAlert();;

            }
            else{
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Action Succeeded");
                alert.setContentText("User updated successfully");
                alert.showAndWait();

            }
    resultsView.setVisible(false);
    }
}
