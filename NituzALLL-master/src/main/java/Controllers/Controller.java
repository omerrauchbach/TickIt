package Controllers;

import Model.IModel;
import Model.Model;
import View.AView;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;

public class Controller extends AController{
    Model model;
    AView mainView;

    public Controller(IModel model, AView mainView) {
        this.model = (Model)model;
        this.mainView = mainView;

    }

    public void Create(String usernameS, String passwordS, String confirmS, String firstS, String lastS, String dateS, String cityS) {
        if (usernameS.length() == 0 || passwordS.length() == 0 || confirmS.length() == 0 ||
                firstS.length() == 0 || lastS.length() == 0 || cityS.length() == 0 || dateS.length() == 0) {
            //if one or more deails aren't filled
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("At least one of the fields are empty.\nFill all fields and try again ");
//            errorAlert.showAndWait();
            mainView.setAlert(errorAlert);
        } else {
            //date test - bigger than 18
            int year = Integer.parseInt(dateS.substring(6, 10));
            int month = Integer.parseInt(dateS.substring(3, 5));
            int day = Integer.parseInt(dateS.substring(0, 2));
            LocalDate birthday = LocalDate.of(year, month, day);
            LocalDate today = LocalDate.now();
            Period p = Period.between(birthday, today);
            int yeardif = p.getYears();
            if (yeardif < 18) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("You are not old enough!");
                errorAlert.setContentText("You need to be 18 or more to use the website!");
                mainView.setAlert(errorAlert);
            } else {

                if (!passwordS.equals(confirmS)) {
                    //if passwords dont match
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Passwords doesn't match.\nPlease try again.");
                    mainView.setAlert(errorAlert);

                } else {
                    boolean flag = model.CreateUser(usernameS, passwordS, firstS, lastS, dateS, cityS);
                    if (flag) {
                        Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                        success.setHeaderText("Action Succeeded");
                        success.setContentText("New user created successfuly! ");
//                    success.showAndWait();
                        mainView.setAlert(success);
                    } else {
                        Alert fail = new Alert(Alert.AlertType.ERROR);
                        fail.setHeaderText("Action Failed");
                        fail.setContentText("Username already taken.\nPlease choose different one and try again ");
//                    fail.showAndWait();
                        mainView.setAlert(fail);

                    }
                }
            }
        }

    }


    public Vector<String> Read(String username) {
        return model.ReadUser(username);
    }

    public void Delete(String DeleteUserNameOutput, String DeletePasswordOutput) {
        if (DeleteUserNameOutput.length() == 0 || DeletePasswordOutput.length() == 0) {
            //if one or more deails aren't filled
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("At least one of the fields are empty.\nFill all fields and try again ");
            mainView.setAlert(errorAlert);

        } else {
            boolean flag = model.DeleteUser(DeleteUserNameOutput, DeletePasswordOutput);
            if (flag) {
                Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                success.setHeaderText("Action Succeeded");
                success.setContentText("User was deleted successfuly! ");
//                    success.showAndWait();
                mainView.setAlert(success);


            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Action failed");
                errorAlert.setContentText("Username or Password are incorrect.\n Try again ");
                mainView.setAlert(errorAlert);

            }

        }
    }


    public void Delete2(String DeleteUserNameOutput) {
        if (DeleteUserNameOutput.length() == 0) {
            //do nothing.
        } else {
            model.Delete2(DeleteUserNameOutput);
        }
    }

    public void update(String usernameS, String passwordS, String confirmS, String firstS, String lastS, String dateS, String cityS) {
        if (usernameS.length() == 0 || passwordS.length() == 0 ||
                firstS.length() == 0 || lastS.length() == 0 || cityS.length() == 0 || dateS.length() == 0) {
            //if one or more deails aren't filled
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("At least one of the fields are empty.\nFill all fields and try again ");
            mainView.setAlert(errorAlert);

            //check over 18

        } else {
            if (!checkDate(dateS)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("birthdate is not valid!");
                errorAlert.setContentText("the format is: dd-mm-yyyy.\n Please try again");
                mainView.setAlert(errorAlert);
            } else {
                //date test - bigger than 18
                int year = Integer.parseInt(dateS.substring(6, 10));
                int month = Integer.parseInt(dateS.substring(3, 5));
                int day = Integer.parseInt(dateS.substring(0, 2));
                if (day < 1 || day > 31 || month < 1 || month > 12) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("birthdate is not valid!");
                    errorAlert.setContentText("the format is: dd-mm-yyyy.\n Please try again");
                    mainView.setAlert(errorAlert);
                } else {
                    LocalDate birthday = LocalDate.of(year, month, day);
                    LocalDate today = LocalDate.now();
                    Period p = Period.between(birthday, today);
                    int yeardif = p.getYears();
                    if (yeardif < 18) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("date is not valid!");
                        errorAlert.setContentText("You need to be 18 or more to use the website!");
                        mainView.setAlert(errorAlert);
                    } else {
                        if (!passwordS.equals(confirmS)) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText("password not match!");
                            errorAlert.setContentText("the passwords dont match.\n Please try again");
                            mainView.setAlert(errorAlert);
                        } else {
                            boolean flag = model.UpdateUser(usernameS, passwordS, firstS, lastS, dateS, cityS);
                            if (flag) {
                                Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                                success.setHeaderText("Action Succeeded");
                                success.setContentText("User updated successfuly! ");
//                    success.showAndWait();
                                mainView.setAlert(success);

                            } else {
                                Alert fail = new Alert(Alert.AlertType.ERROR);
                                fail.setHeaderText("Action Failed");
                                fail.setContentText("Username already taken.\nPlease choose different one and try again ");//todo what failed exactly?
//                    fail.showAndWait();
                                mainView.setAlert(fail);

                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkDate (String date){
        if(date.length()!= 10)
            return false;
        String day = date.substring(0,2);
        String month = date.substring(3,5);
        String year = date.substring(6,10);
        return checkAllDigits(day)&&checkAllDigits(month)&&checkAllDigits(year)&&date.charAt(2)=='-'&&date.charAt(5)=='-';
    }

    private boolean checkAllDigits(String str){
        for (int i = 0; i < str.length(); i++) {
            if(!(str.charAt(i)=='0'||str.charAt(i)=='1'||str.charAt(i)=='2'||str.charAt(i)=='3'||str.charAt(i)=='4'
            ||str.charAt(i)=='5'||str.charAt(i)=='6'||str.charAt(i)=='7'||str.charAt(i)=='8'||str.charAt(i)=='9')){
                return false;
            }
        }
        return true;
    }
}
