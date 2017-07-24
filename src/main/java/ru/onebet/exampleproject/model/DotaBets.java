package ru.onebet.exampleproject.model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries(
        @NamedQuery(
                name = DotaBets.FindBySearchingMark,
                query = "select b from DotaBets b where b.searchingMark = :searchingMark"
        )
)
@Table(name = "BETS")
public class DotaBets {

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
    private DotaTeam temaTwo;

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

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public DotaTeam getTeamOne() {
        return teamOne;
    }

    public DotaTeam getTemaTwo() {
        return temaTwo;
    }

    public double getPercentForTeamOne() {
        return percentForTeamOne;
    }

    public double getPersentForDrow() {
        return persentForDrow;
    }

    public double getPercentForTeamTwo() {
        return percentForTeamTwo;
    }

    public String getSearchingMark() {
        return searchingMark;
    }

    private DotaBets() {}

    public static Builder newBuilder() {
        return new DotaBets().new Builder();
    }

    public static Builder mutate(DotaBets bet) {
        return bet.new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder date(Date date) {
            DotaBets.this.date = date;
            return this;
        }

        public Builder comandOne(DotaTeam comandOne) {
            DotaBets.this.teamOne = comandOne;
            return this;
        }

        public Builder comandTwo(DotaTeam comandTwo) {
            DotaBets.this.temaTwo = comandTwo;
            return this;
        }

        public Builder persentComandOne(double persentComandOne) {
            DotaBets.this.percentForTeamOne = persentComandOne;
            return this;
        }

        public Builder persentToDrow(double persentToDrow) {
            DotaBets.this.persentForDrow = persentToDrow;
            return this;
        }

        public Builder persentComandTwo(double persentComandTwo) {
            DotaBets.this.percentForTeamTwo = persentComandTwo;
            return this;
        }

        public Builder searchingMark(String searchingMark) {
            DotaBets.this.searchingMark = searchingMark;
            return this;
        }

        public DotaBets build() {
            return DotaBets.this;
        }
    }
}
