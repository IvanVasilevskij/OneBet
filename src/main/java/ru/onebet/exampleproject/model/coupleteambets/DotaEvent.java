package ru.onebet.exampleproject.model.coupleteambets;


import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries(
        @NamedQuery(
                name = DotaEvent.FindBySearchingMark,
                query = "select b from DotaEvent b where b.searchingMark = :searchingMark"
        )
)
@Table(name = "BETS")
public class DotaEvent implements EventBetweenCoupleTeam {

    public static final String FindBySearchingMark = "BetsDota.findBySearchingMark";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSCTION_ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "DATE_OF_BET")
    @NotNull
    private Date date;

    @ManyToOne(optional = false)
    @NotNull
    private DotaTeam teamOne;

    @ManyToOne(optional = false)
    @NotNull
    private DotaTeam teamTwo;

    @Column(name = "PERSENT_TO_COMAND_ONE")
    @NotNull
    private double percentForTeamOne;

    @Column(name = "PERSENT_TO_DROW")
    @NotNull
    private double persentForDrow;

    @Column(name = "PERSENT_TO_COMAND_TWO")
    @NotNull
    private double percentForTeamTwo;

    @Column(name = "SEARCHING_MARK")
    @NotNull
    private String searchingMark;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public DotaTeam getTeamOne() {
        return teamOne;
    }

    @Override
    public DotaTeam getTeamTwo() {
        return teamTwo;
    }

    @Override
    public double getPercentForTeamOne() {
        return percentForTeamOne;
    }

    @Override
    public double getPersentForDrow() {
        return persentForDrow;
    }

    @Override
    public double getPercentForTeamTwo() {
        return percentForTeamTwo;
    }

    @Override
    public String getSearchingMark() {
        return searchingMark;
    }

    private DotaEvent() {}

    public static Builder newBuilder() {
        return new DotaEvent().new Builder();
    }

    public static Builder mutate(DotaEvent bet) {
        return bet.new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder date(Date date) {
            DotaEvent.this.date = date;
            return this;
        }

        public Builder comandOne(DotaTeam teamOne) {
            DotaEvent.this.teamOne = teamOne;
            return this;
        }

        public Builder comandTwo(DotaTeam teamTwo) {
            DotaEvent.this.teamTwo = teamTwo;
            return this;
        }

        public Builder persentComandOne(double percentForTeamOne) {
            DotaEvent.this.percentForTeamOne = percentForTeamOne;
            return this;
        }

        public Builder persentToDrow(double persentForDrow) {
            DotaEvent.this.persentForDrow = persentForDrow;
            return this;
        }

        public Builder persentComandTwo(double percentForTeamTwo) {
            DotaEvent.this.percentForTeamTwo = percentForTeamTwo;
            return this;
        }

        public Builder searchingMark(String searchingMark) {
            DotaEvent.this.searchingMark = searchingMark;
            return this;
        }

        public DotaEvent build() {
            return DotaEvent.this;
        }
    }
}
