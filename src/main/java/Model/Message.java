package Model;

import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.Vector;

public class Message {


    private String sender;
    private String reciever;
    private String text;
    private StringProperty senderProperty ;
    private StringProperty recieverProperty ;
    private StringProperty textProperty;



    public Message(String sender, String reciever,String text) {
        this.sender = sender;
        this.reciever = reciever;
        this.text = text;
        this.senderProperty.setValue(sender);
        this.recieverProperty.setValue(reciever);
        this.textProperty.setValue(text);
    }





    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public String getText(){return text;}

    public StringProperty getRecieverProperty() {
        return recieverProperty;
    }

    public StringProperty getsenderProperty(){
        return senderProperty;
    }

    public StringProperty getTextProperty(){
        return textProperty;
    }

    public int getFromDataBaseAndUpdate() {
        int num=-1;
        //find the current msg number
        String sql = "SELECT msg_num FROM Details WHERE id = ? ";
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
        String sql2 = "UPDATE Details SET msg_num = "
                +num_plus_1+" WHERE id = 0";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {

            // set the corresponding param
            //pstmt.setString(1, String.valueOf(seen));
            //pstmt.setString(2, String.valueOf(id));

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
