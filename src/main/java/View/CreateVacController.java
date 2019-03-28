package View;

import Controllers.VacationController;
import Model.Vacation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateVacController extends AView {



    //Vacation information:
    @FXML
    private ComboBox<String> destination;
    @FXML
    private TextField AdultNum;
    int i_AdultNum = 0;
    private final int maxAd = 10;
    private final int minAd = 0;
    @FXML
    private TextField ChildNum;
    int i_ChildNum = 0;
    private final int maxCh = 10;
    private final int minCh = 0;
    @FXML
    private TextField BabyNum;
    int i_BabyNum = 0;
    private final int maxBa = 10;
    private final int minBa = 0;
    @FXML
    private DatePicker DepartureDate;
    @FXML
    private ComboBox<String> includeReturn;
    @FXML
    private DatePicker ReturnDate;
    @FXML
    private ComboBox<String> includeHotel;
    @FXML
    private ComboBox<String> hotelStars;
    @FXML
    private ComboBox<String> hotelType;
    @FXML
    private ComboBox<String> flightComp;
    @FXML
    private ComboBox<String> vacationType;
    @FXML
    private ComboBox<String> includeBag;
    @FXML
    private TableView vacTable;


    @FXML
    void initialize() {
        i_AdultNum = 0;
        i_ChildNum = 0;
        i_BabyNum = 0;

    }

    public void withReturn(ActionEvent ae) {

        if (includeReturn.getValue().equals("yes")) {
            ReturnDate.setDisable(false);
        } else {
            ReturnDate.setDisable(true);
        }
    }

    public void withHotel(ActionEvent ae) {

        if (includeHotel.getValue().equals("yes")) {
            hotelStars.setDisable(false);
            hotelType.setDisable(false);
        } else {
            hotelStars.setDisable(true);
            hotelType.setDisable(true);
        }
    }


    public void addAdult(ActionEvent ae) {
        if (i_AdultNum < maxAd) {
            i_AdultNum++;
            AdultNum.setText("" + i_AdultNum);
            AdultNum.setEditable(false);

        }
    }

    public void minusAdult(ActionEvent ae) {
        if (minAd < i_AdultNum) {
            i_AdultNum--;
            AdultNum.setText("" + i_AdultNum);
            AdultNum.setEditable(false);
        }
    }

    public void addChild(ActionEvent ae) {
        if (i_ChildNum < maxCh) {
            i_ChildNum++;
            ChildNum.setText("" + i_ChildNum);
            ChildNum.setEditable(false);

        }
    }

    public void minusChild(ActionEvent ae) {
        if (minCh < i_ChildNum) {
            i_ChildNum--;
            ChildNum.setText("" + i_ChildNum);
            ChildNum.setEditable(false);

        }
    }

    public void addBaby(ActionEvent ae) {
        if (i_BabyNum < maxBa) {
            i_BabyNum++;
            BabyNum.setText("" + i_BabyNum);
            BabyNum.setEditable(false);
        }
    }

    public void minusBaby(ActionEvent ae) {
        if (minBa < i_BabyNum) {
            i_BabyNum--;
            BabyNum.setText("" + i_BabyNum);
            BabyNum.setEditable(false);
        }
    }


    @FXML
    private void createVac(ActionEvent ae) {
        String flightCompany = "", departureDate = "", backDate = "", baggageIncluded = "",
                Country = "", flightBackIncluded = "", vacationKind = "", hotelIncluded = "", hotelKind="";
        int numOfTicketsAdult = -1, numOfTicketsChild = -1, numOfTicketsBaby = -1, rankOfHotel = -1;
        /**get the information from the view objects:**/
        {
            if (destination.getValue() != null) {
                Country = destination.getValue();
            }
            try {
                if (AdultNum.getText() != null && Integer.valueOf(AdultNum.getText()) != 0)
                    numOfTicketsAdult = Integer.valueOf(AdultNum.getText());
            } catch (Exception e) {

            }
            try {
                if (ChildNum.getText() != null && Integer.valueOf(ChildNum.getText()) != 0)
                    numOfTicketsChild = Integer.valueOf(ChildNum.getText());
            } catch (Exception e) {

            }
            try {
                if (BabyNum.getText() != null && Integer.valueOf(BabyNum.getText()) != 0)
                    numOfTicketsBaby = Integer.valueOf(BabyNum.getText());
            } catch (Exception e) {

            }

            if (DepartureDate.getValue() != null) {
                departureDate = DepartureDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
            if (includeReturn.getValue() != null && !includeReturn.getValue().equals("not must")) {
                flightBackIncluded = includeReturn.getValue();
                if (ReturnDate.getValue() != null) { //todo - add check to returndate bigger then departure date
                    backDate = ReturnDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }
            }

            if ( vacationType.getValue() != null && !vacationType.getValue().equals("Exotic or Urbanic")) {
                vacationKind = vacationType.getValue();
            } // else ""

            if (vacationType.getValue() != null && !vacationType.getValue().equals("Exotic or Urbanic")) { //todo!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                vacationKind = vacationType.getValue();
            } // else ""

            if (flightComp.getValue() != null && !flightComp.getValue().equals("All the companies")) {
                flightCompany = flightComp.getValue();
            }
            if (includeHotel.getValue() != null && !includeHotel.getValue().equals("not must")) {
                hotelIncluded = includeHotel.getValue();
                if (hotelStars.getValue() != null && !hotelStars.getValue().equals("Any rank")) {
                    switch ((String) hotelStars.getValue()) {
                        case "★":
                            rankOfHotel = 1;
                            break;
                        case "★★":
                            rankOfHotel = 2;
                            break;
                        case "★★★":
                            rankOfHotel = 3;
                            break;
                        case "★★★★":
                            rankOfHotel = 4;
                            break;
                        case "★★★★★":
                            rankOfHotel = 5;
                            break;
                        default:
                            rankOfHotel = -1;
                    }
                }
                if (hotelType.getValue() != null && !hotelType.getValue().equals("Any type")) {
                    hotelKind=hotelType.getValue();
                }
            }//hotels

            if (includeBag.getValue() != null && !includeBag.getValue().equals("not must")) {
                baggageIncluded = (String) includeBag.getValue();
            }
        }//get the information from the view object

        VacationController controller = (VacationController) this.controller;
        controller.Create(flightCompany,departureDate,Country,flightBackIncluded);


            this.ShowAlert();


    }
}

