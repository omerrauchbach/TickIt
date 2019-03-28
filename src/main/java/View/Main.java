package View;

import Controllers.Controller;
import Controllers.LoginController;
import Model.Model;
import Model.IModel;
import Model.LoginModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    /**
     *
     * @param primaryStage
     * @throws
     * *The function opens the view form.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{



        try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/Main.fxml").openStream());
            primaryStage.setTitle("Tick-it");
            Scene scene=new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/login_stylesheet.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

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



    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Users.db";

        // SQL statement for creating a new table

        String sql = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "   user_name text PRIMARY KEY\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //
    public static void createNewVacationTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Users.db";

        // SQL statement for creating a new table

        String sql = "CREATE TABLE IF NOT EXISTS Vacations (\n"
                + "   id text PRIMARY KEY,\n"
                + "   subject text,\n"
                + "   sub_topic text,\n"
                + "   number_of_ticket text,\n"
                + "   price text,\n"
                + "   location text,\n"
                + "   vdate text,\n"
                + "   vtime text,\n"
                + "   user_name text NOT NULL \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewRequestTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Users.db";

        // SQL statement for creating a new table

        String sql = "CREATE TABLE IF NOT EXISTS Requests (\n"
                + "   request_id text PRIMARY KEY NOT NULL,\n"
                + "   sender text NOT NULL,\n"
                + "   reciever text NOT NULL,\n"
                + "   closed integer NOT NULL,\n"
                + "   trade integer NOT NULL,\n"
                + "   request_ticket_id integer NOT NULL,\n"
                + "   offered_ticket_id integer\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewMessageTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Users.db";

        // SQL statement for creating a new table

        String sql = "CREATE TABLE IF NOT EXISTS Messages (\n"
                + "   sender text NOT NULL,\n"
                + "   reciever text NOT NULL,\n"
                + "   text text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createdetailsTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Users.db";

        // SQL statement for creating a new table

        String sql = "CREATE TABLE IF NOT EXISTS Details (\n"
                + "   id INTEGER PRIMARY KEY,\n"
                + "   msg_num INTEGER NOT NULL,\n"
                + "   vac_num INTEGER NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        AddRowToDeatils();
    }

    public static void AddRowToDeatils() {
        String sql = "INSERT INTO Details(id, msg_num, vac_num) VALUES(?,?,?)";

        try (Connection conn = connectReturn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(0));
            pstmt.setString(2, String.valueOf(0));
            pstmt.setString(3, String.valueOf(0));
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

        public static Connection connectReturn() {
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

        public static void main(String[] args) {
        connect();
        createNewDatabase("Users.db");
        createNewTable();
        createNewVacationTable();
        createNewMessageTable();
        createNewRequestTable();
        createdetailsTable();
        launch(args);
    }
}