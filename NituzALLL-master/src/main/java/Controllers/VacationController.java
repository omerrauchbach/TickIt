package Controllers;

import Model.Vacation;
import Model.VacationModel;
import View.AView;
import javafx.scene.control.Alert;

import java.util.List;
import Model.User;

public class VacationController extends AController {

    boolean isConnected;
    User user;
    VacationModel vacationModel;
    AView searchView;

    public VacationController(User user, VacationModel vacationModel, AView searchView) {
        this.user = user;
        if(user!=null)
                isConnected = false;
        else {
                isConnected = true;
        }
        this.vacationModel = vacationModel;
        this.searchView = searchView;
    }

    public void Connect(){

    }


    public void Create(String flightCompany,String departureDate,String backDate,String baggageIncluded,
                       String Country,String flightBackIncluded,int numOfTicketsAdult,int numOfTicketsChild,
                       int numOfTicketsBaby,String vacationKind,String hotelIncluded,int rankOfHotel,String hotelKind) {
       //connected so can publish vacation
            Vacation vacation=new Vacation(flightCompany,departureDate,backDate,baggageIncluded,Country,flightBackIncluded,numOfTicketsAdult,numOfTicketsChild
                    ,numOfTicketsBaby,vacationKind,hotelIncluded,rankOfHotel,hotelKind,user.getUserName());
            boolean flag=vacationModel.CreateVacation(vacation);
            if(flag){
                Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                success.setHeaderText("Action Succeeded");
                success.setContentText("New vacation added successfully! ");
                searchView.setAlert(success);
            }

    }

    public List<Vacation> Search(String flightCompany, String departureDate, String backDate, String baggageIncluded,
                       String Country, String flightBackIncluded, int numOfTicketsAdult, int numOfTicketsChild, int numOfTicketsBaby, String vacationKind, String hotelIncluded, int rankOfHotel,
                                 String hotelKind){
        List<Vacation> vacations=vacationModel.findVacations(flightCompany,departureDate,backDate,baggageIncluded,
                 Country,flightBackIncluded,numOfTicketsAdult,numOfTicketsChild, numOfTicketsBaby,vacationKind,hotelIncluded, rankOfHotel,hotelKind);
        if(vacations.size()==0){//no vacation founded
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Can't find vaction with those details. Change the details and try again. ");
            searchView.setAlert(errorAlert);
            return vacations;
        }
        return vacations;
    }
}
