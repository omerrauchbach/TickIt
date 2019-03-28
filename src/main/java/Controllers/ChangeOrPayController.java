package Controllers;

import Model.ChangeOrPayModel;
import Model.IModel;
import Model.Message;
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




    public void PayWithCash(Message message) {
        changeOrPayModel.payWithCash(message);
    }

    public void ChangeVacation(Message message) {
        changeOrPayModel.changeVacation(message);
    }

    public List<String> setVacations(String userName) {
        return changeOrPayModel.setVacations(userName);
    }
}
