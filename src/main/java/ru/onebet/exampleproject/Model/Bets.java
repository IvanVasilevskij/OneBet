package ru.onebet.exampleproject.Model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries(
        @NamedQuery(
                name = Bets.FindBySearchingMark,
                query = "select b from Bets b where b.searchingMark = :searchingMark"
        )
)
@Table(name = "BETS")
public class Bets {

    public static final String FindBySearchingMark = "Bets.findBySearchingMark";

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

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ComandOfDota getComandOne() {
        return comandOne;
    }

    public void setComandOne(ComandOfDota comandOne) {
        this.comandOne = comandOne;
    }

    public ComandOfDota getComandTwo() {
        return comandTwo;
    }

    public void setComandTwo(ComandOfDota comandTwo) {
        this.comandTwo = comandTwo;
    }

    public double getPersentComandOne() {
        return persentComandOne;
    }

    public void setPersentComandOne(double persentComandOne) {
        this.persentComandOne = persentComandOne;
    }

    public double getPersentToDrow() {
        return persentToDrow;
    }

    public void setPersentToDrow(double persentToDrow) {
        this.persentToDrow = persentToDrow;
    }

    public double getPersentComandTwo() {
        return persentComandTwo;
    }

    public void setPersentComandTwo(double persentComandTwo) {
        this.persentComandTwo = persentComandTwo;
    }

    public String getSearchingMark() {
        return searchingMark;
    }

    public void setSearchingMark(String searcingMark) {
        this.searchingMark = searcingMark;
    }
}
