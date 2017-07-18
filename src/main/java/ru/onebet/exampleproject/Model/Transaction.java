package ru.onebet.exampleproject.Model;


import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID", updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USER")
    @ManyToOne(optional = false)
    private User user;

    @Column(name = "ROOT")
    private User root;

    @Column(name = "AMOUNT")
    private double amounr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getRoot() {
        return root;
    }

    public void setRoot(User root) {
        this.root = root;
    }

    public double getAmounr() {
        return amounr;
    }

    public void setAmounr(double amounr) {
        this.amounr = amounr;
    }
}
