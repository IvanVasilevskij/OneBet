package ru.onebet.exampleproject.Model;

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

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ComandOfDota getTakeOnComand() {
        return takeOnComand;
    }

    public void setTakeOnComand(ComandOfDota takeOnComand) {
        this.takeOnComand = takeOnComand;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bets getBet() {
        return bet;
    }

    public void setBet(Bets bet) {
        this.bet = bet;
    }
}
