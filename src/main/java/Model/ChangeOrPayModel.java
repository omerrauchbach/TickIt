package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeOrPayModel implements IModel
{
    @Override
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

    public void payWithCash(Request message){//buy

        String sql = "INSERT INTO Requests(request_id, sender, reciever ,closed, trade,request_ticket_id, offered_ticket_id) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(message.getRequestId()));
            pstmt.setString(2, message.getSender());
            pstmt.setString(3, message.getReciever());
            pstmt.setString(4, String.valueOf(message.getClosed()));
            pstmt.setString(5, String.valueOf(message.getisTrade()));
            pstmt.setString(6, String.valueOf(message.getrequestedTicketId()));
            pstmt.setString(7, String.valueOf(message.getOfferedTicketId()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * create a request message of replacement vacations to the seller
     * @param message -the message that will be create in the DB
     */
    public void changeVacation(Message message){
        /*
        String sql = "INSERT INTO Messages(id, sender, reciever ,seen, vacation_ID_source, vacation_ID_dest, kind) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(message.getId()));
            pstmt.setString(2, message.getSender());
            pstmt.setString(3, message.getReciever());
            pstmt.setString(4, String.valueOf(message.getSeen()));
            pstmt.setString(5, String.valueOf(message.getVacationIDSource()));
            pstmt.setString(6, String.valueOf(message.getVacationIDDest()));
            pstmt.setString(7, String.valueOf(message.getKind()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        */
    }

    /**
     * get all the vacations of the connected user
     * @return all the vacations of the user
     */
    public List<String> setVacations(String userName) {
        String sql = "SELECT id, country FROM Vacations Where user_name = ? ";
        List<String> Ids = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String id=rs.getString(1);
                String country=rs.getString(2);
                Ids.add("ID="+ id+", to "+ country);
            }
            return Ids;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Ids;
    }
}
