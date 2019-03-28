package Controllers;

import Model.LoginModel;
import View.AView;
import javafx.scene.control.Alert;

public class LoginController extends AController {

    LoginModel model ;
    AView view;

    public LoginController(LoginModel model, AView view) {
        this.model = model;
        this.view = view;
    }
    public boolean login(String userName,String password){
        boolean flag=model.Login(userName,password);
        if(!flag){//didnt succeed
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Connection Failed!");
            errorAlert.setContentText("User name or passwords are wrong.\n Please try again! ");
            view.setAlert(errorAlert);
            return false;
        }

        return true;
    }


}
