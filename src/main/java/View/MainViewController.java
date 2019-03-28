package View;

import Controllers.Controller;
import Model.IModel;
import Model.Model;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class MainViewController extends AView {


    @FXML
    public TextField user;
    @FXML
    public PasswordField password;


    /**
     *
     */
    public void Login(ActionEvent actionEvent){

        if(user.getText().length()==0||password.getText().length()==0){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("One or more of the fields are'nt filled.\n Fill all fields and try again.");
            errorAlert.showAndWait();
        }
        else if(!validUserName(user.getText())){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("Unvalid user name");
            errorAlert.showAndWait();
        }
        else {
            if(true){//success
                user.setText("");
                password.setText("");
                IModel model = new Model();
                ViewController mainview=new ViewController();
                Controller controller = new Controller(model,mainview);
                mainview.setController(controller);
                try{
                    FXMLLoader fxmlLoader=new FXMLLoader();
                    Parent root1 = fxmlLoader.load(getClass().getResource("/View.fxml").openStream());
                    ViewController controller1=fxmlLoader.<ViewController>getController();
                    controller1.setUser(new User(user.getText()));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Welcome to Tick-It ! ! !");
                    Scene scene1 = new Scene(root1,500,500);
                    scene1.getStylesheets().add(getClass().getResource("/home_stylesheet.css").toExternalForm());
                    stage.setScene(scene1);
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

    private Boolean validUserName(String userName){

        String name ;
        String email;
        if(userName.contains("@post.bgu.ac.il") && userName.length()>10 && userName.charAt(0) != '@' &&
                userName.charAt(userName.length()-1) != '@'){
           int indexUser = userName.indexOf("@");
           name = userName.substring(0,indexUser-1);
           email = userName.substring(indexUser+1 , userName.length()-1);
           if(email.equals("post.bgu.ac.il")&& name.length()>3)
                return true;
        }
        return true;
    }
}
