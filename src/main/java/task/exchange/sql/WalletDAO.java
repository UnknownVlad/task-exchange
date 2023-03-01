package task.exchange.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.exchange.db_connection.DBConnection;
import task.exchange.model.Wallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Component
public class WalletDAO {

    @Autowired
    private DBConnection dbConnection;

    public void saveWallet(Wallet wallet) {
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String sql = "INSERT INTO wallets (secret_key, btc, rub, ton) VALUES " +
                    "(" +
                    "'"+wallet.getSecret_key()+"'"+", " +
                    wallet.getBTC() + ", " +
                    wallet.getRUB() + ", " +
                    wallet.getTON()+
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

    public Wallet findWallet(String secret_key) {
        Wallet wallet = null;
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String SQL = "SELECT * FROM wallets WHERE secret_key="+"'"+secret_key+"'";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                wallet = new Wallet(
                        resultSet.getString("secret_key"),
                        resultSet.getDouble("btc"),
                        resultSet.getDouble("ton"),
                        resultSet.getDouble("rub")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return wallet;
    }
    public Wallet addCurrencieWallet(Map<String, String> form) {
        String secret_key = form.get("secret_key");
        Wallet wallet = findWallet(secret_key);
        double add = Double.parseDouble(form.getOrDefault("BTC_wallet", "0.0"));
        wallet.setBTC(wallet.getBTC()+add);

        add = Double.parseDouble(form.getOrDefault("TON_wallet", "0.0"));
        wallet.setTON(wallet.getTON()+add);

        add = Double.parseDouble(form.getOrDefault("RUB_wallet", "0.0"));
        wallet.setRUB(wallet.getRUB()+add);

        deleteWallet(secret_key);
        saveWallet(wallet);

        return wallet;
    }

    public boolean haveCurrencie(Map<String, String> form){
        String secret_key = form.get("secret_key");
        Wallet wallet = findWallet(secret_key);

        String currencie = form.get("currency");
        double count = Double.parseDouble(form.get("count"));

        if(currencie.equals("RUB") && wallet.getRUB() < count)
            return false;
        if(currencie.equals("TON") && wallet.getTON() < count)
            return false;
        if(currencie.equals("BTC") && wallet.getBTC() < count)
            return false;

        return true;
    }
    public Wallet sendCurrencieWallet(Map<String, String> form) {
        String secret_key = form.get("secret_key");
        Wallet wallet = findWallet(secret_key);

        String currencie = form.get("currency");
        double count = Double.parseDouble(form.get("count"));

        if(currencie.equals("RUB"))
            wallet.setRUB(wallet.getRUB() - count);
        if(currencie.equals("TON"))
            wallet.setTON(wallet.getTON() - count);
        if(currencie.equals("BTC"))
            wallet.setBTC(wallet.getBTC() - count);

        deleteWallet(secret_key);
        saveWallet(wallet);

        return wallet;
    }

    public void deleteWallet(String secret_key){

        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String SQL = "DELETE FROM wallets WHERE secret_key = "+"'"+secret_key+"'";
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public double getSum(String currency){
        double sum = 0.0;
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            String SQL = "SELECT * FROM wallets";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                sum+=resultSet.getDouble(currency.toLowerCase());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sum;
    }

}
