package Model;

public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String birthdate;

    public User(String userName){
        this.userName=userName;
    }

    public User(String userName,String password,String firstName,String lastName,String city,String birthdate){
        this.userName=userName;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.city=city;
        this.birthdate=birthdate;
    }


    public String getUserName() {
        return userName;
    }
}
