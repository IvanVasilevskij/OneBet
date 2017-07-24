package ru.onebet.exampleproject.model.betsmaked;

import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.User;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MAKING_BETS")
public class MakedBetsOfDota implements MakedBets {
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
    private DotaEvent bet;

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

    public DotaEvent getBet() {
        return bet;
    }

    private MakedBetsOfDota() {}

    public static Builder newBuilder() {
        return new MakedBetsOfDota().new Builder();
    }

    public static Builder mutate(MakedBetsOfDota dotaBetsMaked) {
        return dotaBetsMaked.new Builder();
    }


    public class Builder {

        private Builder() {}

        public Builder date(Date date) {
            MakedBetsOfDota.this.date = date;
            return this;
        }

        public Builder takeOnComand(DotaTeam comandOfDotaImpl) {
            MakedBetsOfDota.this.takeOfTeam = comandOfDotaImpl;
            return this;
        }

        public Builder user(User user) {
            MakedBetsOfDota.this.user = user;
            return this;
        }

        public Builder bet(DotaEvent bet) {
            MakedBetsOfDota.this.bet = bet;
            return this;
        }

        public MakedBetsOfDota build() {
            return MakedBetsOfDota.this;
        }
    }
}
