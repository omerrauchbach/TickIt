package View;

import Controllers.Controller;
import Controllers.VacationController;
import Controllers.MessagesController;
import Model.IModel;
import Model.Model;
import Model.MessageModel;
import Model.VacationModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import Model.User;

import javafx.event.ActionEvent;

import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class ViewController extends AView {

    private User user;


    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void openReadForm(){
        IModel model = new Model();
        MainViewController mainview=new MainViewController();
        Controller controller = new Controller(model,mainview);
        mainview.setController(controller);
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/ReadUser-OLD.fxml").openStream());
            stage.setTitle("Vacation4U - ReadUser");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 500, 500));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



    /**
     *
     * @param event
     * the function opens the update form of the user
     */
    @FXML
    private void openUpdateForm(ActionEvent event){
        IModel model = new Model();
        MainViewController mainview=new MainViewController();
        Controller controller = new Controller(model,mainview);
        mainview.setController(controller);
        try{
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/UpdateUser-OLD.fxml").openStream());
            UpdateController controller1=fxmlLoader.<UpdateController>getController();
            controller1.setUser(user);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("UpdateUser User");
            stage.setScene(new Scene(root1));
            stage.show();
            controller1.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    /**
     *
     * @param event by choosing the delete option in view.fxml
     *              The function open new scene.
     */
    @FXML
    private void openDeleteForm(ActionEvent event) {
        IModel model = new Model();
        MainViewController mainview=new MainViewController();
        Controller controller = new Controller(model,mainview);
        mainview.setController(controller);
        try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/DeleteUser-OLD.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("DeleteUser Form");
            stage.setScene(new Scene(root1));
            stage.show();


        }
        catch(IOException e){
            e.printStackTrace();

        }
    }


    public void openSearchVacationForm(ActionEvent actionEvent) {
        VacationModel model = new VacationModel();
        SearchVacController view=new SearchVacController();
        VacationController controller = new VacationController(user,model,view);
        view.setController(controller);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/SearchVacation.fxml").openStream());
            SearchVacController controller1=fxmlLoader.<SearchVacController>getController();
            controller1.setUser(user);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Search For Ticket");
            stage.setScene(new Scene(root1));
            stage.show();

        }
        catch (IOException e){

        }

    }

    public void openMessages(ActionEvent actionEvent) {
        MessageModel model = new MessageModel();
        MessagesViewController view=new MessagesViewController ();
        MessagesController controller = new MessagesController(model,view);
        view.setController(controller);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/Messages.fxml").openStream());
            MessagesViewController controller1=fxmlLoader.<MessagesViewController>getController();
            controller1.setUser(user);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Messages:");
            stage.setScene(new Scene(root1));
            stage.show();
        }

        catch (IOException e){

        }

    }

    public void openRequests(ActionEvent actionEvent) {
        MessageModel model = new MessageModel();
        MessagesViewController view=new MessagesViewController ();
        MessagesController controller = new MessagesController(model,view);
        view.setController(controller);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/Messages.fxml").openStream());
            MessagesViewController controller1=fxmlLoader.<MessagesViewController>getController();
            controller1.setUser(user);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Messages:");
            stage.setScene(new Scene(root1));
            stage.show();
        }

        catch (IOException e){

        }

    }
    public void openNewVacationForm(ActionEvent actionEvent) {
        VacationModel model = new VacationModel();
        SearchVacController view=new SearchVacController();
        VacationController controller = new VacationController(user,model,view);
        view.setController(controller);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/CreateVac.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("CreateMessage New Vacation");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e){

        }

    }
}
