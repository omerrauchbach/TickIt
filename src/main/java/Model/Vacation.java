package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;

public class Vacation {

    private String varSubject;
    private String varSubTopic;
    private String varPrice;
    private String varLocation;
    private String varNumber;
    private String varTime;
    private String varDate;
    private String seller;
    private int id;


    public Vacation(String varSubject, String varSubTopic, String varPrice, String varLocation,
                    String varNumber, String varTime, String varDate, String username) {
        this.varSubject = varSubject;
        this.varSubTopic = varSubTopic;
        this.varPrice = varPrice;
        this.varLocation = varLocation;
        this.varNumber = varNumber;
        this.varTime = varTime;
        this.varDate = varDate;
        this.seller = username;
        id=getFromDataBaseAndUpdate();
    }

    public Vacation(int id,String varSubject, String varSubTopic, String varPrice, String varLocation,
                    String varNumber, String varTime, String varDate, String username) {
        this.varSubject = varSubject;
        this.varSubTopic = varSubTopic;
        this.varPrice = varPrice;
        this.varLocation = varLocation;
        this.varNumber = varNumber;
        this.varTime = varTime;
        this.varDate = varDate;
        this.seller = username;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public String getVarSubject() {
        return varSubject;
    }

    public String getVarSubTopic() {
        return varSubTopic;
    }

    public String getVarPrice() {
        return varPrice;
    }

    public String getVarLocation() {
        return varLocation;
    }

    public String getVarNumber() {
        return varNumber;
    }

    public String getVarTime() {
        return varTime;
    }

    public String getVarDate() {
        return varDate;
    }

    public String getSeller() {
        return seller;
    }

    ////////////////////////////
    public StringProperty getVarSubjectProperty() {
         return  new SimpleStringProperty(varSubject);
    }

    public StringProperty getVarSubTopicProperty() {
        return new SimpleStringProperty(varSubTopic);
    }

    public StringProperty getVarPriceProperty() {
        return new SimpleStringProperty(varPrice);
    }

    public StringProperty getVarLocationProperty() {
        return new SimpleStringProperty(varLocation);
    }

    public StringProperty getVarNumberProperty() {
        return new SimpleStringProperty(varNumber);
    }

    public StringProperty getVarTimeProperty() {
        return new SimpleStringProperty(varTime);
    }

    public StringProperty getVarDateProperty() {
        return new SimpleStringProperty(varDate);
    }

    public StringProperty getSellerProperty() {
        return new SimpleStringProperty(seller);
    }

    ////////////////

    public int getFromDataBaseAndUpdate() {
        int num=-1;
        //find the current msg number
        String sql = "SELECT vac_num FROM Details WHERE id = ? ";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1,"0");
            ResultSet rs = pstmt.executeQuery();
            num=Integer.parseInt(rs.getString(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //update
        int num_plus_1=num+1;
        String sql2 = "UPDATE Details SET vac_num = "
                +num_plus_1+" WHERE id = 0";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {


            // update
            pstmt.executeUpdate();
        }
        catch(Exception e){

        }
        //return
        return num;
    }

    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:Users.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
