package Controllers;

import Model.ChangeOrPayModel;
import Model.IModel;
import Model.Message;
import Model.Request;
import View.AView;
import View.ChangeOrPayViewController;

import java.util.List;

public class ChangeOrPayController extends AController {
    private ChangeOrPayModel changeOrPayModel;
    private ChangeOrPayViewController changeOrPayViewController;

    public ChangeOrPayController(IModel model, AView view){
        changeOrPayModel=(ChangeOrPayModel)model;
        changeOrPayViewController=(ChangeOrPayViewController)view;
    }




    public void PayWithCash(Request request) {
        changeOrPayModel.payWithCash(request);
    }

    public void ChangeVacation(Message message) {
        changeOrPayModel.changeVacation(message);
    }

    public List<String> setVacations(String userName) {
        return changeOrPayModel.setVacations(userName);
    }
}
