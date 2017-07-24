package ru.onebet.exampleproject.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MAKING_BETS")
public class DotaBetsMaked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSCTION_ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "DATE_OF_BET")
    @NotNull
    private Date date;

    @OneToOne
    private DotaTeam takeOfTeam;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private DotaBets bet;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public DotaTeam getTakeOfTeam() {
        return takeOfTeam;
    }

    public User getUser() {
        return user;
    }

    public DotaBets getBet() {
        return bet;
    }

    private DotaBetsMaked() {}

    public static Builder newBuilder() {
        return new DotaBetsMaked().new Builder();
    }

    public static Builder mutate(DotaBetsMaked dotaBetsMaked) {
        return dotaBetsMaked.new Builder();
    }


    public class Builder {

        private Builder() {}

        public Builder date(Date date) {
            DotaBetsMaked.this.date = date;
            return this;
        }

        public Builder takeOnComand(DotaTeam comandOfDotaImpl) {
            DotaBetsMaked.this.takeOfTeam = comandOfDotaImpl;
            return this;
        }

        public Builder user(User user) {
            DotaBetsMaked.this.user = user;
            return this;
        }

        public Builder bet(DotaBets bet) {
            DotaBetsMaked.this.bet = bet;
            return this;
        }

        public DotaBetsMaked build() {
            return DotaBetsMaked.this;
        }
    }
}
