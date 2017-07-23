package ru.onebet.exampleproject.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MAKING_BETS")
public class MakingBets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSCTION_ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "DATE_OF_BET")
    @NotNull
    private Date date;

    @OneToOne
    private ComandOfDota takeOnComand;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Bets bet;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public ComandOfDota getTakeOnComand() {
        return takeOnComand;
    }

    public User getUser() {
        return user;
    }

    public Bets getBet() {
        return bet;
    }

    private MakingBets() {}

    public static Builder newBuilder() {
        return new MakingBets().new Builder();
    }

    public static Builder newBuilder(MakingBets makingBets) {
        return makingBets.new Builder();
    }


    public class Builder {

        private Builder() {}

        public Builder date(Date date) {
            MakingBets.this.date = date;
            return this;
        }

        public Builder takeOnComand(ComandOfDota comandOfDota) {
            MakingBets.this.takeOnComand = comandOfDota;
            return this;
        }

        public Builder user(User user) {
            MakingBets.this.user = user;
            return this;
        }

        public Builder bet(Bets bet) {
            MakingBets.this.bet = bet;
            return this;
        }

        public MakingBets build() {
            return MakingBets.this;
        }
    }
}
