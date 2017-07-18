package ru.onebet.exampleproject.Model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(
        @NamedQuery(
                name = User.FindByLogin,
                query = "select u from User u where u.login = :login"
        )
)
@Table(name = "USERS")
public class User {
    public static final String RootUserName = "root";
    public static final String FindByLogin = "User.findByLogin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private int userId;

    @Column(name = "LOGIN")
    @NotNull
    private String login;

    @Column(name = "FIRST_NAME")
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    private String lastName;

    @Column(name = "BALANCE")
    @NotNull
    private double balance;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bets> bets;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Bets> getBets() {
        return bets;
    }

    public void setBets(List<Bets> bets) {
        this.bets = bets;
    }
}

