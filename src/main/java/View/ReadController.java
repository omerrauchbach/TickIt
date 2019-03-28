package View;

import Controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Vector;

public class ReadController extends AView {
    @FXML
    private TextField username;
    @FXML
    private GridPane resultsView;
    @FXML
    private TextField username_just4view;
    @FXML
    private TextField password_just4view;
    @FXML
    private TextField firstname_just4view;
    @FXML
    private TextField lastname_just4view;
    @FXML
    private TextField city_just4view;
    @FXML
    private TextField birthdate_just4view;
    @FXML
    private Label sorrymessage;

    @FXML
    private void ReadUser(ActionEvent event) throws IOException {
        String usernameS;
        usernameS = username.getText();
        Controller controller=(Controller)this.controller;
        Vector<String> ans = controller.Read(usernameS);
        sorrymessage.setVisible(false);
        resultsView.setVisible(false);
        if (ans.size() != 0) {
            username_just4view.setText(ans.elementAt(0));
            firstname_just4view.setText(ans.elementAt(2));
            lastname_just4view.setText(ans.elementAt(3));
            city_just4view.setText(ans.elementAt(4));
            birthdate_just4view.setText(ans.elementAt(5));
            resultsView.setVisible(true);
        } else {
            sorrymessage.setVisible(true);
        }
    }

    @FXML
    public void onEnter(ActionEvent ae){
        try {
            ReadUser(ae);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
