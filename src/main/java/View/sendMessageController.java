package View;

import Controllers.MessagesController;
import Model.Message;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class sendMessageController extends AView {

    public String sender;
    public String reciever;
    public Button send;


    public TextArea textArea;

    public sendMessageController(Message message ){

        this.sender = message.getReciever() ;
        this.reciever = message.getSender();

    }

    public void setMessage(Message message){
        this.sender = message.getReciever() ;
        this.reciever = message.getSender();
    }

    public void onSend(){

        if(textArea.getText().length() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("Field is'nt filled.\n Fill all fields and try again.");
            errorAlert.showAndWait();
        }
        MessagesController Messagescontroller = ( MessagesController)this.controller;
        Messagescontroller.CreateMessage(sender, reciever , textArea.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success!");
        alert.setContentText("the message was successfully send");
        Stage stage = (Stage)send.getScene().getWindow();
        stage.close();


    }
}
