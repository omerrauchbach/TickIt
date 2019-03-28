package View;

import Controllers.VacationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class CreateVacController extends AView {



    //Vacation information:
    @FXML
    private ComboBox<String> subject;
    @FXML
    private ComboBox<String> numberOfTicket;
    @FXML
    private ComboBox<String> time;
    @FXML
    private TextField subTopic;
    @FXML
    private TextField price;
    @FXML
    private TextField location;
    @FXML
    private DatePicker date;
    @FXML
    private TableView vacTable;


    @FXML
    private void publishTicket(ActionEvent ae) {
        String varSubject=subject.getValue();
        String varSubTopic=subTopic.getText();
        String varPrice=price.getText();
        String varLocation=location.getText();
        String varNumber=numberOfTicket.getValue();
        String varTime=time.getValue();
        String varDate=date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        VacationController controller = (VacationController) this.controller;
        controller.Create(varSubject,varSubTopic,varPrice,varLocation,varNumber,varTime,varDate);

        this.ShowAlert();


    }
}

