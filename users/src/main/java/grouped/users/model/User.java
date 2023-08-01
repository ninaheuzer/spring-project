package grouped.users.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String pseudo;
    private Date birthday;
    private String address;
    @Column(unique=true)
    private String email;
    private String password;
    private USER_TYPES userType;

    private enum USER_TYPES {
        CLIENT("client"),
        ADMIN("admin");
        String userType;
        USER_TYPES(String userType){
            this.userType = userType;
        }
    }
}

