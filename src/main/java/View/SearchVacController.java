package View;

import Controllers.ChangeOrPayController;
import Controllers.VacationController;
import Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SearchVacController extends AView {

    private User user;
    @FXML
    private AnchorPane extendableSearchPane;
    @FXML
    private TitledPane accord;
    private Rectangle clipRect;

    //Vacation information:
    @FXML
    private ComboBox<String> destination;
    @FXML
    private TableColumn destionationCol;

    @FXML
    private TextField AdultNum;
    int i_AdultNum = 0;
    private final int maxAd = 10;
    private final int minAd = 0;
    @FXML
    private TableColumn adultCol;

    @FXML
    private TextField ChildNum;
    int i_ChildNum = 0;
    private final int maxCh = 10;
    private final int minCh = 0;
    @FXML
    private TableColumn childCol;

    @FXML
    private TextField BabyNum;
    int i_BabyNum = 0;
    private final int maxBa = 10;
    private final int minBa = 0;
    @FXML
    private TableColumn babyCol;

    @FXML
    private DatePicker DepartureDate;
    @FXML
    private TableColumn depDateCol;

    @FXML
    private ComboBox<String> includeReturn;
    @FXML
    private TableColumn withReturnCol;

    @FXML
    private DatePicker ReturnDate;
    @FXML
    private TableColumn returnDateCol;

    @FXML
    private ComboBox<String> vacationType;
    @FXML
    private TableColumn vacationTypeCol;

    @FXML
    private ComboBox<String> flightComp;
    @FXML
    private TableColumn flightCompCol;

    @FXML
    private ComboBox<String> includeBag;
    @FXML
    private TableColumn includeBagCol;

    @FXML
    private ComboBox<String> includeHotel;
    @FXML
    private TableColumn includeHotCol;

    @FXML
    private ComboBox<String> hotelType;
    @FXML
    private TableColumn hotelTypeCol;

    @FXML
    private ComboBox<String> hotelStars;
    @FXML
    private TableColumn hotelRankCol;
    @FXML
    private Button searchNotEx;


    @FXML
    private TableView<Vacation> vacTable;
    private boolean isExtend=false; // true if now its extend mode , false if not.

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void initialize() {
        double widthInitial = 200;
        double heightInitial = 200;
        clipRect = new Rectangle();
        clipRect.setWidth(widthInitial);
        clipRect.setHeight(0);
        clipRect.translateYProperty().set(heightInitial);
        extendableSearchPane.setClip(clipRect);
        extendableSearchPane.translateYProperty().set(-heightInitial);
        extendableSearchPane.prefHeightProperty().set(0);
        accord.setExpanded(false);
        clipRect.setWidth(extendableSearchPane.getWidth());
        toggleExtendableSearch();
        searchNotEx.setVisible(true);
        i_AdultNum = 0;
        i_ChildNum = 0;
        i_BabyNum = 0;
        TableColumn actionCol = new TableColumn("Action");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Vacation, String>, TableCell<Vacation, String>> cellFactory
                = //
                new Callback<TableColumn<Vacation, String>, TableCell<Vacation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Vacation, String> param) {
                        final TableCell<Vacation, String> cell = new TableCell<Vacation, String>() {

                            final Button btn = new Button("Submit a\nrequest");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Vacation vacation = getTableView().getItems().get(getIndex());
                                        if(user!=null&&!user.getUserName().equals(vacation.getUsername())) {//user get sell for himself
                                            ChangeOrPayModel model = new ChangeOrPayModel();
                                            ChangeOrPayViewController view=new ChangeOrPayViewController ();
                                            ChangeOrPayController controller = new ChangeOrPayController(model,view);
                                            view.setController(controller);
                                            FXMLLoader fxmlLoader = new FXMLLoader();
                                            Parent root1 = null;
                                            try {
                                                root1 = fxmlLoader.load(getClass().getResource("/ChangeOrPayVacation.fxml").openStream());
//                                                ViewController controller1=fxmlLoader.<ViewController>getController();
//                                                controller1.setUserName(userName);
                                                ChangeOrPayViewController controller1=fxmlLoader.<ChangeOrPayViewController>getController();
                                                controller1.setUser(user);
                                                controller1.setVacation(vacation);
                                                controller1.setListVacations(user.getUserName());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            Stage stage = new Stage();
                                            stage.initModality(Modality.APPLICATION_MODAL);
                                            stage.setTitle("Choose action");
                                            stage.setScene(new Scene(root1, 500, 500));
                                            stage.show();
                                        }
                                        else{
                                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                            if(user==null) {//not connected
                                                errorAlert.setHeaderText("You are not connected to our system.");
                                                errorAlert.setContentText("Please close all the windows,\n" +
                                                        "and sign in first. ");
                                            }
                                            else if(user.getUserName().equals(vacation.getUsername())) {
                                                //the user can't buy from himself
                                                errorAlert.setHeaderText("Its your vacation!");
                                                errorAlert.setContentText("You can't buy you own vacation!");
                                            }
                                            errorAlert.show();

                                        }


                                        /*

                                        Vacation vacation = getTableView().getItems().get(getIndex());
                                        if(!userName.equals("")){
                                            MessageModel model = new MessageModel();
                                            model.CreateMessage(new Message(userName,vacation.getSeller(),0,vacation.getId()));
                                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                                            success.setHeaderText("The request has submitted.");
                                            success.setContentText("If the seller will agree,\n" +
                                                    "a payment request will wait for you in the mail box. ");
                                            success.show();
                                        }
                                        else{
                                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                            errorAlert.setHeaderText("You are not connected to our system.");
                                            errorAlert.setContentText("Please close all the windows,\n" +
                                                    "and sign in first. ");
                                            errorAlert.show();

                                        }
                                        */
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        VacationController controller = (VacationController) this.controller;
        actionCol.setCellFactory(cellFactory);
        List<Vacation> vacList = controller.Search("", "", "", "",
                "", "", -1, -1, -1, "", "", -1,"");


        ObservableList<Vacation> vacObsList = FXCollections.observableArrayList();

        vacTable.setItems(vacObsList);
        vacTable.getColumns().addAll(actionCol);

    }


    @FXML
    public void toggleExtendableSearch() {

        clipRect.setWidth(extendableSearchPane.getWidth());

        if (clipRect.heightProperty().get() != 0) {
            isExtend=false;
            searchNotEx.setVisible(true);
            // Animation for scroll up.
            Timeline timelineUp = new Timeline();

            // Animation of sliding the search pane up, implemented via
            // clipping.
            final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
            final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), extendableSearchPane.getHeight());

            // The actual movement of the search pane. This makes the table
            // grow.
            final KeyValue kvUp4 = new KeyValue(extendableSearchPane.prefHeightProperty(), 0);
            final KeyValue kvUp3 = new KeyValue(extendableSearchPane.translateYProperty(), -extendableSearchPane.getHeight());

            final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2, kvUp3, kvUp4);
            timelineUp.getKeyFrames().add(kfUp);
            timelineUp.play();
            accord.setExpanded(false);
        } else {
            isExtend=true;
            searchNotEx.setVisible(false);
            // Animation for scroll down.
            Timeline timelineDown = new Timeline();

            // Animation for sliding the search pane down. No change in size,
            // just making the visible part of the pane
            // bigger.
            final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), extendableSearchPane.getHeight());
            final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);

            // Growth of the pane.
            final KeyValue kvDwn4 = new KeyValue(extendableSearchPane.prefHeightProperty(), extendableSearchPane.getHeight());
            final KeyValue kvDwn3 = new KeyValue(extendableSearchPane.translateYProperty(), 0);

            final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), createBouncingEffect(extendableSearchPane.getHeight()), kvDwn1, kvDwn2,
                    kvDwn3, kvDwn4);
            timelineDown.getKeyFrames().add(kfDwn);

            timelineDown.play();
        }
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
    private void search(ActionEvent ae) {
        String flightCompany = "", departureDate = "", backDate = "", baggageIncluded = "",
                Country = "", flightBackIncluded = "", vacationKind = "", hotelIncluded = "", hotelKind="";
        int numOfTicketsAdult = -1, numOfTicketsChild = -1, numOfTicketsBaby = -1, rankOfHotel = -1;
        /**get the information from the view objects:**/
        {
            if (destination.getValue() != null && !destination.getValue().equals("Anywhere")) {
                Country = destination.getValue();
            }
            if (DepartureDate.getValue() != null) {
                departureDate = DepartureDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
            if(isExtend) {
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


                if (includeReturn.getValue() != null && !includeReturn.getValue().equals("not must")) {
                    flightBackIncluded = includeReturn.getValue();
                    if (ReturnDate.getValue() != null) { //todo - add check to returndate bigger then departure date
                        backDate = ReturnDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }
                }

                if (vacationType.getValue() != null && !vacationType.getValue().equals("Exotic or Urbanic")) {
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
            }
        }//get the information from the view object
        VacationController controller = (VacationController) this.controller;
        List<Vacation> vacList = controller.Search(flightCompany, departureDate, backDate, baggageIncluded,
                Country, flightBackIncluded, numOfTicketsAdult, numOfTicketsChild, numOfTicketsBaby, vacationKind, hotelIncluded, rankOfHotel,hotelKind);


        ObservableList<Vacation> vacObsList = FXCollections.observableArrayList();
        vacObsList.addAll(vacList);
        {


            destionationCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
            vacTable.setItems(vacObsList);

            adultCol.setCellValueFactory(new PropertyValueFactory<>("numOfTicketsAdult"));
            vacTable.setItems(vacObsList);

            childCol.setCellValueFactory(new PropertyValueFactory<>("numOfTicketsKid"));
            vacTable.setItems(vacObsList);

            babyCol.setCellValueFactory(new PropertyValueFactory<>("numOfTicketsBaby"));
            vacTable.setItems(vacObsList);

            depDateCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
            vacTable.setItems(vacObsList);

            withReturnCol.setCellValueFactory(new PropertyValueFactory<>("flightBackIncluded"));
            vacTable.setItems(vacObsList);

            returnDateCol.setCellValueFactory(new PropertyValueFactory<>("backDate"));
            vacTable.setItems(vacObsList);

            vacationTypeCol.setCellValueFactory(new PropertyValueFactory<>("vacationKind"));
            vacTable.setItems(vacObsList);

            flightCompCol.setCellValueFactory(new PropertyValueFactory<>("flightCompany"));
            vacTable.setItems(vacObsList);

            includeBagCol.setCellValueFactory(new PropertyValueFactory<>("baggageIncluded"));
            vacTable.setItems(vacObsList);

            includeHotCol.setCellValueFactory(new PropertyValueFactory<>("hotelIncluded"));
            vacTable.setItems(vacObsList);


            hotelRankCol.setCellValueFactory(new PropertyValueFactory<>("rankOfHotel"));
            vacTable.setItems(vacObsList);

            hotelTypeCol.setCellValueFactory(new PropertyValueFactory<>("kindOfHotel"));
            vacTable.setItems(vacObsList);

        }


    }

    private EventHandler<ActionEvent> createBouncingEffect(double height) {
        final Timeline timelineBounce = new Timeline();
        timelineBounce.setCycleCount(2);
        timelineBounce.setAutoReverse(true);
        final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (height - 15));
        final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
        final KeyValue kv3 = new KeyValue(extendableSearchPane.translateYProperty(), -15);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
        timelineBounce.getKeyFrames().add(kf1);

        // Event handler to call bouncing effect after the scroll down is
        // finished.
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timelineBounce.play();
            }
        };
        return handler;
    }
}

