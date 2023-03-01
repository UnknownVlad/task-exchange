package task.exchange.model;



import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {
    private String username;
    private String email;
    @Id
    private String secret_key;

    private String role;


    public User(String username, String email, String secret_key, String role) {
        this.username = username;
        this.email = email;
        this.secret_key = secret_key;
        this.role = role;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", secret_key=" + secret_key +
                ", role=" + role +
                '}';
    }


}
