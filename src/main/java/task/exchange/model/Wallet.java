package task.exchange.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    private String secret_key;
    private double BTC = 0.0;
    private double TON = 0.0;
    private double RUB = 0.0;

    public Wallet(String secret_key) {
        this.secret_key = secret_key;
    }

    public Wallet(String secret_key, double BTC, double TON, double RUB) {
        this.secret_key = secret_key;
        this.BTC = BTC;
        this.TON = TON;
        this.RUB = RUB;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public double getBTC() {
        return BTC;
    }

    public void setBTC(double BTC) {
        this.BTC = BTC;
    }

    public double getTON() {
        return TON;
    }

    public void setTON(double TON) {
        this.TON = TON;
    }

    public double getRUB() {
        return RUB;
    }

    public void setRUB(double RUB) {
        this.RUB = RUB;
    }
}
