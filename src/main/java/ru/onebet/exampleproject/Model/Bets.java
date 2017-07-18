package ru.onebet.exampleproject.Model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Bets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSCTION_ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "DATE_OF_BET")
    @NotNull
    private Date date;

    @Column(name = "COMAND_ONE")
    @ManyToOne(optional = false)
    @NotNull
    private Comand comandOne;

    @Column(name = "COMAND_TWO")
    @ManyToOne(optional = false)
    @NotNull
    private Comand comandTwo;

    @Column(name = "PERSENT_TO_COMAND_ONE")
    @NotNull
    private double persentComandOne;

    @Column(name = "PERSENT_TO_DROW")
    private double persentToDrow;

    @Column(name = "PERSENT_TO_COMAND_TWO")
    @NotNull
    private double persentComandTwo;

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

    public Comand getComandOne() {
        return comandOne;
    }

    public void setComandOne(Comand comandOne) {
        this.comandOne = comandOne;
    }

    public Comand getComandTwo() {
        return comandTwo;
    }

    public void setComandTwo(Comand comandTwo) {
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
}
