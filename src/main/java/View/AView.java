package View;


import Controllers.AController;
import javafx.scene.control.Alert;

public class AView {
    protected static AController controller;
    protected static Alert alert;

    public AView() {

    }
    public void setController(AController controller2){
        controller=controller2;
    }

    public void setAlert(Alert alert2){
        alert=alert2;
    }
    public void ShowAlert(){
        alert.showAndWait();
    }

}
