package View;

import Controllers.ChangeOrPayController;
import Model.Message;
import Model.User;
import Model.Vacation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.List;

public class ChangeOrPayViewController extends AView {
    @FXML
    private Button payButton;
    @FXML
    private Button changeButton;
    @FXML
    private ComboBox listVacations;

    private String buyer;

    private String seller;

    private ChangeOrPayController changeOrPayController;

    private User user;

    private Vacation vacation;

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public void setBuyerName(String userName){
        this.buyer=userName;
    }

    public void setSellerName(String userName){
        this.buyer=userName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setListVacations(String userName) {
        changeOrPayController = (ChangeOrPayController) this.controller;
        List<String> list = changeOrPayController.setVacations(userName);
        ObservableList<String> obList = FXCollections.observableArrayList(list);
        obList.sort(String::compareToIgnoreCase);
        listVacations.setItems(obList);
    }

    @FXML
    private void PayCash(ActionEvent event) throws IOException {
        Message message = new Message(user.getUserName(),vacation.getSeller(),3,vacation.getId(),0,0);
        changeOrPayController.PayWithCash(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("A message send to the seller");
        alert.showAndWait();
    }

    @FXML
    private void ChangeVacation(ActionEvent event) throws IOException {
        if(listVacations.getItems().size()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You don't have any vacation to replace.");
            alert.showAndWait();
        }
        else {
            String chosenLine=(String)listVacations.getSelectionModel().getSelectedItem();
            if(chosenLine==null||chosenLine.equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose a vacation you want to replace with.");
                alert.showAndWait();
                return;
            }
            String[] splitedLine=chosenLine.split(",");
            String[] idString = splitedLine[0].split("=");
            int id = Integer.parseInt(idString[1]);
            Message message = new Message(user.getUserName(), vacation.getSeller(), 0, vacation.getId(), id, 1);//todo check if getId is correct
            changeOrPayController.ChangeVacation(message);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("A message send to the seller");
            alert.showAndWait();
        }
    }
}
