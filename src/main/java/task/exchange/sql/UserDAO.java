package task.exchange.sql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.exchange.db_connection.DBConnection;
import task.exchange.model.User;

import java.sql.*;

@Component
public class UserDAO {

    @Autowired
    private DBConnection dbConnection;

    public void saveUser(User user) {
        try {
            String sql = "INSERT INTO users (username, email, secret_key, role) VALUES (?, ?, ?,?)";
            PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSecret_key());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User findUser(String nameCollumn, String value) {
        User user = null;
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String SQL = "SELECT * FROM users WHERE " + nameCollumn + " = "+ value;
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("secret_key"),
                        resultSet.getString("role")
                );
                System.out.println(resultSet.getString("role"));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


}
