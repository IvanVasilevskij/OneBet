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

    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BALANCE")
    @NotNull
    private double balance;

    @Column(name = "EMAIL")
    @NotNull
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bets> bets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

