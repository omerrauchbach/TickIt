package Model;

import java.sql.*;

public class Vacation {

    private String flightCompany;
    private String departureDate;
    private String backDate;
    private String baggageIncluded; //boolean
    private String Country;
    private String flightBackIncluded; //boolean
    private int numOfTicketsAdult;
    private int numOfTicketsKid;
    private int numOfTicketsBaby;
    private String vacationKind;
    private String hotelIncluded; //boolean
    private int rankOfHotel;
    private String kindOfHotel;
    private int id;
    private String seller;

    static int numOfVactions=0;//id

    //string ="" --> empty
    //int =-1 -->empty


    public Vacation(String flightCompany, String departureDate, String backDate, String baggageIncluded, String country, String flightBackIncluded, int numOfTicketsAdult,int numOfTicketsChild,
                    int numOfTicketsBaby, String vacationKind, String hotelIncluded, int rankOfHotel,String kindOfHotel,String seller) {
        this.flightCompany = flightCompany;
        this.departureDate = departureDate;
        this.backDate = backDate;
        this.baggageIncluded = baggageIncluded;
        Country = country;
        this.flightBackIncluded = flightBackIncluded;
        this.numOfTicketsAdult = numOfTicketsAdult;
        this.numOfTicketsKid=numOfTicketsChild;
        this.numOfTicketsBaby=numOfTicketsBaby;
        this.vacationKind = vacationKind;
        this.hotelIncluded = hotelIncluded;
        this.rankOfHotel = rankOfHotel;
        this.kindOfHotel=kindOfHotel;
        this.seller = seller;
        id=getFromDataBaseAndUpdate();

    }

    public Vacation(int id,String flightCompany, String departureDate, String backDate, String baggageIncluded,
                    String country, String flightBackIncluded, int numOfTicketsAdult,int numOfTicketsChild,
                    int numOfTicketsBaby, String vacationKind, String hotelIncluded, int rankOfHotel,String kindOfHotel,String seller) {
        this.flightCompany = flightCompany;
        this.departureDate = departureDate;
        this.backDate = backDate;
        this.baggageIncluded = baggageIncluded;
        Country = country;
        this.flightBackIncluded = flightBackIncluded;
        this.numOfTicketsAdult = numOfTicketsAdult;
        this.numOfTicketsKid=numOfTicketsChild;
        this.numOfTicketsBaby=numOfTicketsBaby;
        this.vacationKind = vacationKind;
        this.hotelIncluded = hotelIncluded;
        this.rankOfHotel = rankOfHotel;
        this.kindOfHotel=kindOfHotel;
        this.seller = seller;
        this.id=id;
        }
    public String getFlightCompany() {
        return flightCompany;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public String getBaggageIncluded() {
        return baggageIncluded;
    }

    public String getCountry() {
        return Country;
    }

    public String getFlightBackIncluded() {
        return flightBackIncluded;
    }

    public int getNumOfTicketsAdult() {
        return numOfTicketsAdult;
    }

    public int getNumOfTicketsKid() {
        return numOfTicketsKid;
    }

    public int getNumOfTicketsBaby() {
        return numOfTicketsBaby;
    }

    public String getVacationKind() {
        return vacationKind;
    }

    public String getHotelIncluded() {
        return hotelIncluded;
    }

    public int getRankOfHotel() {
        return rankOfHotel;
    }

    public String getKindOfHotel() {
        return kindOfHotel;
    }

    public String getSeller() {
        return seller;
    }

    public int getId() {
        return id;
    }

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
