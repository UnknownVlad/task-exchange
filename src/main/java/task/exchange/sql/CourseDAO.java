package task.exchange.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.exchange.db_connection.DBConnection;
import task.exchange.model.Course;
import task.exchange.model.Wallet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Currency;
import java.util.Map;

@Component
public class CourseDAO {
    @Autowired
    private DBConnection dbConnection;

    public void saveCourse(Course course) {
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String sql = "INSERT INTO courses (currency, btc, rub, ton) VALUES " +
                    "(" +
                    "'"+course.getCurrency()+"'"+", "+
                    course.getBTC() + ", " +
                    course.getRUB() + ", " +
                    course.getTON()+
                    ")";
            statement.executeUpdate(sql);
//            String sql = "INSERT INTO wallets (secret_key, btc, rub, ton) VALUES (?, ?, ?,?)";
//            PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);
//
//            stmt.setString(1, wallet.getSecret_key());
//            stmt.setDouble(2, wallet.getBTC());
//            stmt.setDouble(3, wallet.getRUB());
//            stmt.setDouble(4, wallet.getTON());
//            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Course findCourse(String currency) {
        Course course = null;
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String SQL = "SELECT * FROM courses WHERE currency = "+"'"+currency+"'";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                 course = new Course(
                         currency,
                        resultSet.getDouble("btc"),
                        resultSet.getDouble("ton"),
                        resultSet.getDouble("rub")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return course;
    }

    public Course changeCourse(Map<String, String> form) {
        String currency = form.get("base_currency");

        deleteCourse(currency);

        Course course = new Course(currency);
        if(form.containsValue("TON")) {
            course.setBTC(Double.parseDouble(form.get("BTC")));
            course.setRUB(Double.parseDouble(form.get("RUB")));
        }
        if(form.containsValue("BTC")) {
            course.setTON(Double.parseDouble(form.get("TON")));
            course.setRUB(Double.parseDouble(form.get("RUB")));
        }
        if(form.containsValue("RUB")) {
            course.setTON(Double.parseDouble(form.get("TON")));
            course.setBTC(Double.parseDouble(form.get("BTC")));
        }
        saveCourse(course);
        return course;
    }
    public void deleteCourse(String currency){
        Wallet wallet = null;
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String SQL = "DELETE FROM courses WHERE currency = "+"'"+currency+"'";
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
