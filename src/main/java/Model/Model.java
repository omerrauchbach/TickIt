package Model;

import java.sql.*;
import java.util.Vector;


public class Model implements IModel {

    public Model() {

    }

    /**
     * Connect to the Users.db database
     *
     * @return the Connection object
     */

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

    public boolean CreateUser(String userName, String password, String firstName, String lastName, String birthday, String city) {
        String sql = "INSERT INTO Users(user_name,password,first_name,last_name,city,birthdate) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, city);
            pstmt.setString(6, birthday);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Vector<String> ReadUser(String userName) {
        String sql = "SELECT user_name, password, first_name, last_name, city, birthdate FROM Users WHERE user_name = ? ";
        Vector<String> ans = new Vector<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                for (int i = 1; i <=6  ; i++) {
                    ans.add(rs.getString(i));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }

    /**
     * the function updates the details of an exists user
     *
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthday
     * @param city
     */


    public boolean UpdateUser(String userName, String password, String firstName, String lastName, String birthday, String city) {
        String sql = "UPDATE Users SET password = ? ,first_name = ? ,last_name = ? ,birthdate = ? ,city = ? WHERE user_name = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
//            pstmt.setString(1, userName);
            pstmt.setString(1, password);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, birthday);
            pstmt.setString(5, city);
            pstmt.setString(6, userName);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


   /**
     * Connect to the Users.db database
     *
     * @return the Connection object
     */
/**
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:Users.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connect");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 **/
/**
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
 **/

    /**
     * DeleteUser function get the strings username and password.
     * It checks if the username exists, and also if the password is match.
     * if user was deleted, success alert is shown. if not, the user get error alert.
     */
    public boolean DeleteUser(String userName, String Password) {

        //checking if there is a record in the DB which match to the input userName and Password
        String sql1 = "SELECT user_name FROM Users WHERE user_name = ? AND password = ?";
        Vector<String> ans = new Vector<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {

            // set the value
            pstmt1.setString(1, userName);
            pstmt1.setString(2, Password);
            ResultSet rs = pstmt1.executeQuery();
            while (rs.next()) {
                for (int i = 1; i <=1  ; i++) {
                    ans.add(rs.getString(i));
                }
            }
            //if the query's answer return null- the userName and password weren't found
          if(ans.isEmpty())
                return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //the deletion
        String sql = "DELETE FROM Users WHERE user_name = ? AND password = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, Password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //if the user was deleted successfully
        return true;
    }

    private boolean IsRecordExist(String userName){
        Vector<String> record2delete = ReadUser(userName);
        if(record2delete.isEmpty()) return false;
        return true;
    }

    public boolean Delete2(String userName) {
        //check if exist:
        if(!IsRecordExist(userName)) return false;
        //the deletion:
        String sql = "DELETE FROM Users WHERE user_name = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if the user was deleted successfully
        return true;
    }
}
