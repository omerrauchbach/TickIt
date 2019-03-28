package View;

import Controllers.Controller;
import Controllers.LoginController;
import Model.IModel;
import Model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.User;

import java.io.IOException;


public class LoginViewController extends AView{

    @FXML
    private TextField user;

    @FXML TextField password;

    @FXML
    StackPane pane;


    public void login(ActionEvent actionEvent) {

        LoginController loginController=(LoginController)this.controller;
        if(user.getText().length()==0||password.getText().length()==0){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("One or more of the fields are'nt filled.\n Fill all fields and try again.");
            errorAlert.showAndWait();

        }
        else {
            boolean flag = loginController.login(user.getText(),password.getText());
            if(flag){//success
                Stage stage=(Stage)pane.getScene().getWindow();
                stage.hide();
                IModel model = new Model();
                ViewController mainview=new ViewController();
                Controller controller = new Controller(model,mainview);
                mainview.setController(controller);
                try{
                    FXMLLoader fxmlLoader=new FXMLLoader();
                    Parent root1 = fxmlLoader.load(getClass().getResource("/View.fxml").openStream());
                    ViewController controller1=fxmlLoader.<ViewController>getController();
                    controller1.setUser(new User(user.getText()));
                    stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Welcome to Vacation 4 U");
                    stage.setScene(new Scene(root1,500,500));
                    stage.show();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
            else{//failure
                this.ShowAlert();
            }
        }

    }

}
