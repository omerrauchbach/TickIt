package Model;

import java.sql.*;
import java.util.Vector;

public class Message {

    private int id;
    private int kind;//0-cash,1-change
    private String sender;
    private String reciever;
    private int seen;
    // 0 not seen, replacement.
    // 1 seen and replace accepted by reciever.
    // 2 seen and replace canceled by reciever.
    // 3 not seen, pay cash.
    // 4 seen and accepted by reciever
    // 5 seen and canceled by reciever
    // 6 reciever wait for get his money from buyer
    // 7 don't do nothing
    private String isOpended;
    private int vacationIDSource;
    private int vacationIDDest;



    public Message(String sender, String reciever,int seen,int vacationIdSource,int vacationIdDest, int kind) {
        this.sender = sender;
        this.reciever = reciever;
        this.vacationIDSource=vacationIdSource;
        this.vacationIDDest=vacationIdDest;
        this.kind=kind;
        id=getFromDataBaseAndUpdate();
        this.seen=seen;
        if(seen==7)
            isOpended="ReadUser";
        else{
            isOpended="Unread";
        }
    }
    public Message(int id,String sender, String reciever,int seen,int vacationIdSource,int vacationIdDest, int kind) {
        this.sender = sender;
        this.reciever = reciever;
        this.vacationIDSource=vacationIdSource;
        this.vacationIDDest=vacationIdDest;
        this.kind=kind;
        this.id=id;
        this.seen=seen;
        if(seen==7)
            isOpended="ReadUser";
        else{
            isOpended="unread";
        }
    }

    public int getKind() {
        return kind;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public int getSeen() {
        return seen;
    }

    public String getIsOpended() {
        return isOpended;
    }

    public int getVacationIDSource() {
        return vacationIDSource;
    }

    public int getVacationIDDest() {
        return vacationIDDest;
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
