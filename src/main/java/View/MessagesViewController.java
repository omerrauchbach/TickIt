package View;

import Controllers.Controller;
import Controllers.MessagesController;
import Model.IModel;
import Model.Message;
import Model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.User;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MessagesViewController extends AView {

    public User user_Reciever;
    @FXML
    private AnchorPane pane;
    @FXML
    public TableView<Message> tableView;

    @FXML
    public TableColumn<Message , String > colFrom;
    @FXML
    public TableColumn<Message , String> colText;





    public void initializeMessages(){

        MessagesController Messagescontroller = ( MessagesController)this.controller;
        List<Message> messages = ((MessagesController) this.controller).getMessages(user_Reciever.getUserName());
        ObservableList<Message> list = FXCollections.observableArrayList(messages);

        colFrom.setCellValueFactory(cellData -> cellData.getValue().getsenderProperty());
        colText.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());

        Callback<TableColumn<Message, String>, TableCell<Message, String>> cellFactory
                = //
                new Callback<TableColumn<Message, String>, TableCell<Message, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Message, String> param) {
                        final TableCell<Message, String> cell = new TableCell<Message, String>() {

                            final Button btn = new Button("response");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Message message = getTableView().getItems().get(getIndex());
                                        onSendMesage(message);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

    }

    public void setUser(User reciever) {
        this.user_Reciever=reciever;
    }

    public void onSendMesage( Message message){

        IModel model = new Model();
        sendMessageController mainview= new sendMessageController(message);
        Controller controller = new Controller(model,mainview);
        mainview.setController(controller);
        try{
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/sendMessage.fxml").openStream());
            sendMessageController controller1=fxmlLoader.<sendMessageController>getController();
            controller1.setMessage(message);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root1,400,250);
            scene1.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
            stage.setScene(scene1);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }



/*
    User user_Reciever; //own- he is now the reciever
    String payed="false";
    boolean need_to_update=false;
    int id_to_update;

    public void setUser(User reciever) {
        this.user_Reciever=reciever;
    }

    public void setPayed(String payed){
        this.payed=payed;
    }


    public ObservableList<Message> getMessages(String reciever) {//reciever=userName
        ObservableList<Message> observableList = FXCollections.observableArrayList();
        MessagesController messagesController = (MessagesController) this.controller;
        List<Message> messages = messagesController.getRecieverMessages(reciever);
        for (int i = 0; i < messages.size(); i++) {
            observableList.add(messages.get(i));
        }
        return observableList;
    }

    public void setMsg(){
        tableView.setRowFactory(tv -> {
            TableRow<Message> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Message clickedRow = row.getItem();
                if(clickedRow.getSeen()==0){//need to open confirmation to vacation request. updates only if user confirmed or denied request!!
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setHeaderText("Confirm");
                    confirmation.setContentText("Do you want to approve replace request from " + clickedRow.getSender()+ " about the vacation ID "+clickedRow.getVacationIDDest()+" ?\n Click OK to approve, cancel to dent and x to exit.");
                    Optional<ButtonType> result= confirmation.showAndWait();
                    MessagesController msgs=(MessagesController)controller;
                    if (result.get() == ButtonType.OK){//Replace is approved
                        msgs.CreateMessage(clickedRow.getReciever(),clickedRow.getSender(),1,clickedRow.getVacationIDSource(),clickedRow.getVacationIDDest(),clickedRow.getKind());
                        msgs.updateSeenToMessage(clickedRow.getId(),7);
                        msgs.changeVacations(clickedRow);

                    } else {
                        msgs.CreateMessage(clickedRow.getReciever(),clickedRow.getSender(),2,clickedRow.getVacationIDSource(),clickedRow.getVacationIDDest(),clickedRow.getKind());
                        msgs.updateSeenToMessage(clickedRow.getId(),7);

                    }
                }
                else if(clickedRow.getSeen()==1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Replacement succeed!");
                    alert.setContentText("The Replacement request was approved and yours vacation are replaced.");
                    alert.showAndWait();
                    MessagesController msgs=(MessagesController)controller;
                    msgs.updateSeenToMessage(clickedRow.getId(),7);

                }
                else if(clickedRow.getSeen()==2){
                    Alert error = new Alert(Alert.AlertType.INFORMATION);
                    error.setHeaderText("Sorry");
                    error.setContentText("Vacation replacement request were denied by user.");
                    error.showAndWait();
                    MessagesController msgs=(MessagesController)controller;
                    msgs.updateSeenToMessage(clickedRow.getId(),7);
                }
                else if(clickedRow.getSeen()==3){
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setHeaderText("Confirm");
                    confirmation.setContentText("Do you want to approve cash request from " + clickedRow.getSender()+" ?\n Click OK to approve, cancel to dent and x to exit.");
                    Optional<ButtonType> result= confirmation.showAndWait();
                    MessagesController msgs=(MessagesController)controller;
                    if (result.get() == ButtonType.OK){//cash is approved
                        msgs.CreateMessage(clickedRow.getReciever(),clickedRow.getSender(),4,clickedRow.getVacationIDSource(),clickedRow.getVacationIDDest(),clickedRow.getKind());
                        msgs.updateSeenToMessage(clickedRow.getId(),6);
                        msgs.changeVacations(clickedRow);

                    } else {
                        msgs.CreateMessage(clickedRow.getReciever(),clickedRow.getSender(),5,clickedRow.getVacationIDSource(),clickedRow.getVacationIDDest(),clickedRow.getKind());
                        msgs.updateSeenToMessage(clickedRow.getId(),7);
                    }
                }
                else if(clickedRow.getSeen()==4){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Request is approved!");
                    alert.setContentText("The request was approved.\nGo to the seller and give him the money!");
                    alert.showAndWait();
                    MessagesController msgs=(MessagesController)controller;
                    msgs.updateSeenToMessage(clickedRow.getId(),7);
                }
                else if(clickedRow.getSeen()==5){
                    Alert error = new Alert(Alert.AlertType.INFORMATION);
                    error.setHeaderText("Sorry");
                    error.setContentText("Vacation request were denied by user.");
                    error.showAndWait();
                    MessagesController msgs=(MessagesController)controller;
                    msgs.updateSeenToMessage(clickedRow.getId(),7);
                }
                else if(clickedRow.getSeen()==6){
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setHeaderText("Confirm");
                    confirmation.setContentText("Did you get the money from sender and you are approving the selling?\n Click OK to approve, cancel to dent and x to exit.");
                    Optional<ButtonType> result= confirmation.showAndWait();
                    MessagesController msgs=(MessagesController)controller;
                    if (result.get() == ButtonType.OK){//cash is approved
                        msgs.updateSeenToMessage(clickedRow.getId(),7);
                        msgs.changeOwnerOfVacation(clickedRow);
                    } else {
                        msgs.updateSeenToMessage(clickedRow.getId(),7);
                    }
                }
                else{//alreadyseen
                    Alert error = new Alert(Alert.AlertType.INFORMATION);
                    error.setHeaderText("Seen Message");
                    error.setContentText("You already checked this message.");
                    error.showAndWait();
                }
            });

            return row ;
        });
        pane.getChildren().add(tableView);
    }

    public void update_message(){
        MessagesController msgs = (MessagesController) controller;
        msgs.updateSeenToMessage(id_to_update, 3);
        //setMsg();

    }
    */
}
