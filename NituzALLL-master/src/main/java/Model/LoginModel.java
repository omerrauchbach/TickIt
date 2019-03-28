package Model;

import java.sql.*;
import java.util.Vector;

public class LoginModel implements IModel{

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

    public boolean Login(String userName,String password) {
        String sql = "SELECT user_name, password FROM Users WHERE user_name = ? AND password = ?";
        Vector<String> ans = new Vector<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return true;
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
