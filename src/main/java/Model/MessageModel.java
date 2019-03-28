package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageModel implements IModel{

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


    public boolean CreateMessage(Message message) {
        String sql = "INSERT INTO Messages(sender, reciever , text) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, message.getSender());
            pstmt.setString(2, message.getReciever());
            pstmt.setString(3, String.valueOf(message.getText()));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * returns a list of all the messages of the reciever
     * @param reciever
     * @return
     */
    public List<Message> searchByReciever(String reciever) {

        String sql = "SELECT sender,reciever,text  FROM Messages Where reciever = ? ";//all messages
                List<Message> msgs = new ArrayList<Message>();
                try (Connection conn = this.connect();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, reciever);
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()){
                        String sender= rs.getString(1);
                        String reciever_= rs.getString(2);
                        String text =rs.getString(3);
               Message message=new Message(sender , reciever_ , text);
               msgs.add(message);
            }
            return msgs;
        }
        catch (Exception e){

        }
        return msgs;
    }

    public boolean UpdateSeen(int id,int seen) {//seen=1/2
        String sql = "UPDATE Messages SET seen = "
                +seen+" WHERE id = "+id;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            //pstmt.setString(1, String.valueOf(seen));
            //pstmt.setString(2, String.valueOf(id));

            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void changeVacations(Message message) {
        /*
        String sql ="UPDATE Vacations SET user_name=? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
//            pstmt.setString(1, userName);
            pstmt.setString(1, message.getSender());
            pstmt.setString(2, String.valueOf(message.getVacationIDSource()));
            // update
            pstmt.executeUpdate();
            pstmt.setString(1, message.getReciever());
            pstmt.setString(2, String.valueOf(message.getVacationIDDest()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        */
    }

    public void changeOwnerOfVacation(Message message) {
        /*
        String sql ="UPDATE Vacations SET user_name=? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, message.getSender());
            pstmt.setString(2, String.valueOf(message.getVacationIDSource()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        */
    }
}
