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


    @FXML
    private DatePicker DepartureDate;

    //Vacation information:
    @FXML
    private ComboBox<String> subject;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn subjectCol;
    @FXML
    private TableColumn subTopicCol;
    @FXML
    private TableColumn numberCol;
    @FXML
    private TableColumn priceCol;
    @FXML
    private TableColumn locationCol;
    @FXML
    private TableColumn dateCol;
    @FXML
    private TableColumn timeCol;
    @FXML
    private TableColumn userNameCol;

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
        //accord.setExpanded(false);
        clipRect.setWidth(extendableSearchPane.getWidth());
        toggleExtendableSearch();
        searchNotEx.setVisible(true);
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
                                        if(user!=null&&!user.getUserName().equals(vacation.getSeller())) {//user get sell for himself
                                            ChangeOrPayModel model = new ChangeOrPayModel();
                                            ChangeOrPayViewController view=new ChangeOrPayViewController ();
                                            ChangeOrPayController controller = new ChangeOrPayController(model,view);
                                            view.setController(controller);
                                            FXMLLoader fxmlLoader = new FXMLLoader();
                                            Parent root1 = null;
                                            try {
                                                root1 = fxmlLoader.load(getClass().getResource("/ChangeOrPayVacation.fxml").openStream());
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
                                            else if(user.getUserName().equals(vacation.getSeller())) {
                                                //the user can't buy from himself
                                                errorAlert.setHeaderText("Its your vacation!");
                                                errorAlert.setContentText("You can't buy you own vacation!");
                                            }
                                            errorAlert.show();

                                        }
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

    @FXML
    private void search(ActionEvent ae) {
        String flightCompany = "", departureDate = "", backDate = "", baggageIncluded = "",
                ticket = "", flightBackIncluded = "", vacationKind = "", hotelIncluded = "", hotelKind = "";
        int numOfTicketsAdult = -1, numOfTicketsChild = -1, numOfTicketsBaby = -1, rankOfHotel = -1;
        /**get the information from the view objects:**/
        {
            if (subject.getValue() != null && !subject.getValue().equals("-None")) {
                ticket = subject.getValue();
            }
            if (DepartureDate.getValue() != null) {
                departureDate = DepartureDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
        }//get the information from the view object
        VacationController controller = (VacationController) this.controller;
        List<Vacation> vacList = controller.Search(ticket, departureDate);


        ObservableList<Vacation> vacObsList = FXCollections.observableArrayList();
        vacObsList.addAll(vacList);
        {


            priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
            vacTable.setItems(vacObsList);

            userNameCol.setCellValueFactory(new PropertyValueFactory<>("UserName"));
            vacTable.setItems(vacObsList);

            subjectCol.setCellValueFactory(new PropertyValueFactory<>("Subject"));
            vacTable.setItems(vacObsList);

            subTopicCol.setCellValueFactory(new PropertyValueFactory<>("SubTopic"));
            vacTable.setItems(vacObsList);

            numberCol.setCellValueFactory(new PropertyValueFactory<>("numberOfTickets"));
            vacTable.setItems(vacObsList);

            locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
            vacTable.setItems(vacObsList);

            dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
            vacTable.setItems(vacObsList);

            timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));
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

