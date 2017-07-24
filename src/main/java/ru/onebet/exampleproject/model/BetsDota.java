package ru.onebet.exampleproject.model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries(
        @NamedQuery(
                name = BetsDota.FindBySearchingMark,
                query = "select b from BetsDota b where b.searchingMark = :searchingMark"
        )
)
@Table(name = "BETS")
public class BetsDota {

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
    private ComandOfDota comandOne;

    @ManyToOne(optional = false)
    @NotNull
    private ComandOfDota comandTwo;

    @Column(name = "PERSENT_TO_COMAND_ONE")
    @NotNull
    private double persentComandOne;

    @Column(name = "PERSENT_TO_DROW")
    @NotNull
    private double persentToDrow;

    @Column(name = "PERSENT_TO_COMAND_TWO")
    @NotNull
    private double persentComandTwo;

    @Column(name = "SEARCHING_MARK")
    @NotNull
    private String searchingMark;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public ComandOfDota getComandOne() {
        return comandOne;
    }

    public ComandOfDota getComandTwo() {
        return comandTwo;
    }

    public double getPersentComandOne() {
        return persentComandOne;
    }

    public double getPersentToDrow() {
        return persentToDrow;
    }

    public double getPersentComandTwo() {
        return persentComandTwo;
    }

    public String getSearchingMark() {
        return searchingMark;
    }

    private BetsDota() {}

    public static Builder newBuilder() {
        return new BetsDota().new Builder();
    }

    public static Builder mutate(BetsDota bet) {
        return bet.new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder date(Date date) {
            BetsDota.this.date = date;
            return this;
        }

        public Builder comandOne(ComandOfDota comandOne) {
            BetsDota.this.comandOne = comandOne;
            return this;
        }

        public Builder comandTwo(ComandOfDota comandTwo) {
            BetsDota.this.comandTwo = comandTwo;
            return this;
        }

        public Builder persentComandOne(double persentComandOne) {
            BetsDota.this.persentComandOne = persentComandOne;
            return this;
        }

        public Builder persentToDrow(double persentToDrow) {
            BetsDota.this.persentToDrow = persentToDrow;
            return this;
        }

        public Builder persentComandTwo(double persentComandTwo) {
            BetsDota.this.persentComandTwo = persentComandTwo;
            return this;
        }

        public Builder searchingMark(String searchingMark) {
            BetsDota.this.searchingMark = searchingMark;
            return this;
        }

        public BetsDota build() {
            return BetsDota.this;
        }
    }
}
