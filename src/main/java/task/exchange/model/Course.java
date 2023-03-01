package task.exchange.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String currency;
    private double BTC = 0.0;
    private double TON = 0.0;
    private double RUB = 0.0;

    public Course(String currency, double BTC, double TON, double RUB) {
        this.currency = currency;
        this.BTC = BTC;
        this.TON = TON;
        this.RUB = RUB;
    }
    public Course(String currency) {
        this.currency = currency;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
