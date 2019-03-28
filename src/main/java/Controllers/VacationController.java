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


    public void Create(String varSubject,String varSubTopic,String varPrice,String varLocation,
                       String varNumber,String varTime,String varDate){

       //connected so can publish vacation
            Vacation vacation=new Vacation(varSubject,varSubTopic,varPrice,varLocation,varNumber,varTime,
                    varDate,user.getUserName());
            boolean flag= vacationModel.CreateTicket(vacation);
            if(flag){
                Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                success.setHeaderText("Action Succeeded");
                success.setContentText("New vacation added successfully! ");
                searchView.setAlert(success);
            }

    }

    public List<Vacation> Search(String subject, String date){
        List<Vacation> vacations= vacationModel.findVacations(subject,date);
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
