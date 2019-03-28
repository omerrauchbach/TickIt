package View;

import Controllers.MessagesController;
import Model.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.Optional;
import Model.User;

public class MessagesViewController extends AView {


    @FXML private AnchorPane pane;
    @FXML private Button msg;
    TableView<Message> tableView;


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


    public ObservableList<Message> getMessages(String reciever){//reciever=userName
        ObservableList<Message> observableList= FXCollections.observableArrayList();
        MessagesController messagesController=(MessagesController)this.controller;
        List<Message> messages=messagesController.getRecieverMessages(reciever);
        for(int i=0;i<messages.size();i++){
            observableList.add(messages.get(i));
        }
        return observableList;
    }
    public void setMsg(){
        TableColumn<Message,String> senderCol=new TableColumn<>("Sender");
        senderCol.setMinWidth(200);
        senderCol.setCellValueFactory(new PropertyValueFactory<Message,String>("sender"));

        TableColumn<Message,String> seenCol=new TableColumn<>("ReadUser/Unread");
        seenCol.setMinWidth(200);
        seenCol.setCellValueFactory(new PropertyValueFactory<Message,String>("isOpended"));

        TableColumn<Message,Integer> vacIdSource=new TableColumn<>("VacationSource");
        vacIdSource.setMinWidth(200);
        vacIdSource.setCellValueFactory(new PropertyValueFactory<Message,Integer>("vacationIDSource"));

        TableColumn<Message,Integer> vacIdDest=new TableColumn<>("VacationDest");
        vacIdDest.setMinWidth(200);
        vacIdDest.setCellValueFactory(new PropertyValueFactory<Message,Integer>("vacationIDDest"));
        if(tableView==null){
            tableView=null;
        }
        tableView=new TableView<>();
        tableView.setItems(getMessages(user_Reciever.getUserName()));
        tableView.getColumns().addAll(senderCol,seenCol,vacIdSource,vacIdDest);
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
        msg.setVisible(false);
    }

    public void seeMsg(ActionEvent actionEvent) {
        setMsg();
    }

    public void update_message(){
        MessagesController msgs = (MessagesController) controller;
        msgs.updateSeenToMessage(id_to_update, 3);
        //setMsg();

    }
}
