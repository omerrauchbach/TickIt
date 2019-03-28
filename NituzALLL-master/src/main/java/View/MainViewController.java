package View;

import Controllers.Controller;
import Controllers.LoginController;
import Controllers.VacationController;
import Model.IModel;
import Model.LoginModel;
import Model.VacationModel;
import Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class MainViewController extends AView {

    Stage loginStage;


    /**
    * @param event
    * @throws IOException
    * opens the form to make new user
    */
@FXML
    private void openCreateForm(ActionEvent event){
    IModel model = new Model();
    MainViewController mainview=new MainViewController();
    Controller controller = new Controller(model,mainview);
    mainview.setController(controller);
        try{
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/Create.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("CreateMessage User");
            stage.setScene(new Scene(root1,500,500));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void openLoginForm(){

        LoginModel model2=new LoginModel();
        LoginViewController loginView=new LoginViewController();
        LoginController loginController=new LoginController(model2,loginView);
        loginView.setController(loginController);


        try{
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/Login.fxml").openStream());
            loginStage = new Stage();

            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setTitle("Login");
           loginStage.setScene(new Scene(root1));
            loginStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
  
    @FXML
    private void openReadForm(){
        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/Read.fxml").openStream());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Vacation4U - ReadUser");
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
        try{
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/Update.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("UpdateUser User");
            stage.setScene(new Scene(root1));
            stage.show();
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
        try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root1 = fxmlLoader.load(getClass().getResource("/Delete.fxml").openStream());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("DeleteForm");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();

        }
    }

    @FXML
    private void startSearch(ActionEvent event) {
        VacationModel model2=new VacationModel();
        SearchVacController loginView=new SearchVacController();
        VacationController loginController=new VacationController(null,model2,loginView);
        loginView.setController(loginController);
        FXMLLoader fxmlLoader=new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/SearchVacation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Search Vacation");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();

    }
}
