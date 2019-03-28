package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacationModel implements IModel{

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

    //create
    public boolean CreateTicket(Vacation vacation) {

        String sql = "INSERT INTO Vacations(id,subject,sub_topic,number_of_ticket,price,location,vdate,vtime,user_name) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
  
            pstmt.setString(1, String.valueOf(vacation.getId()));


                pstmt.setString(2, vacation.getVarSubject());


                pstmt.setString(3, vacation.getVarSubTopic());


                pstmt.setString(4, vacation.getVarNumber());

                pstmt.setString(5,vacation.getVarPrice());


                 pstmt.setString(6,vacation.getVarLocation());


                pstmt.setString(7,vacation.getVarDate());

                pstmt.setString(8,String.valueOf(vacation.getVarTime()));

            pstmt.setString(9, String.valueOf(vacation.getSeller()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
/*

String sql = "SELECT userName, password FROM users WHERE userName = ? and password = ?";
        String sqlLog =  "INSERT INTO login(userName) VALUES(?)" ;
        String sqlcurUser =  "INSERT INTO curUser(userName) VALUES(?)" ;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt1 = conn.prepareStatement(sqlLog);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlcurUser)) {
            // set the corresponding param
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs=pstmt.executeQuery();
            if(!rs.next()){
                ans[0]="F";
                ans[1]="Wrong password";
                return ans;
            }

            pstmt1.setString(1,userName);
            pstmt1.executeUpdate();
            pstmt2.setString(1,userName);
            pstmt2.executeUpdate();
            ans[0] = "S";
            ans[1] = "Login successful";

            return ans;
        } catch (SQLException e) {
            ans[0] = "F";
            ans[1] =  "fail to search the user";
            return ans;
        }


 */
    //read
    public List<Vacation> findVacations(String subject,String date)
    {
        String sql="SELECT * FROM Vacations WHERE subject = ? and vdate = ? ";
        List<Vacation> lv = new ArrayList<Vacation>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the values
            pstmt.setString(1, subject);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                    int id_=Integer.parseInt(rs.getString(1));
                    String subject_=rs.getString(2);
                    String sub_topic_= rs.getString(3);

                    String number_of_ticket_=rs.getString(4);
                    String price_=rs.getString(5);
                    String location_=rs.getString(6);
                    String vdate_=rs.getString(7);
                    String vtime_=rs.getString(8);
                    String user_name_=rs.getString(9);
                    Vacation vacation = new Vacation(id_, subject_,sub_topic_,number_of_ticket_,price_,location_,vdate_,
                            vtime_,user_name_);
                    lv.add(vacation);
                }
                return lv;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }



        return null;

    }
}
