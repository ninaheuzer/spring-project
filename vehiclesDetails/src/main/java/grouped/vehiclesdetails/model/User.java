package grouped.vehiclesdetails.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String pseudo;
    private Date birthday;
    private String address;
    private String email;
    private String password;
    private boolean isAdmin;
    private USER_TYPES userType;

    public static enum USER_TYPES {
        CLIENT("client"),
        ADMIN("admin");
        String userType;

        USER_TYPES(String userType){
            this.userType = userType;
        }
    }
}
