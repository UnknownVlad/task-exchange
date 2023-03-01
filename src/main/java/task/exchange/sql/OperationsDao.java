package task.exchange.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.exchange.db_connection.DBConnection;
import task.exchange.model.Operation;
import task.exchange.model.User;

import java.sql.*;

@Component
public class OperationsDao {

    @Autowired
    private DBConnection dbConnection;

    public void saveOperation(Operation operation) {
        try {
            String sql = "INSERT INTO operations (date) VALUES (?)";
            PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);
            stmt.setDate(1, (Date) operation.getDate());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int countOperations(Date from, Date to){
        int count = 0;
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String sql = "SELECT * FROM users WHERE date >= "+from +" AND date <= " + to;
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                count++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
}
