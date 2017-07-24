package ru.onebet.exampleproject.model;


import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal balance;

    @Column(name = "EMAIL")
    @NotNull
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MakedBetsOfDota> bets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public List<MakedBetsOfDota> getBets() {
        return bets;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    private User() {}

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public static Builder mutate(User user) {
        return user.new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder login(String login) {
            User.this.login = login;
            return this;
        }

        public Builder password(String password) {
            User.this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            User.this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            User.this.lastName = lastName;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            User.this.balance = balance;
            return this;
        }

        public Builder email(String email) {
            User.this.email = email;
            return this;
        }

        public Builder bets(List<MakedBetsOfDota> bets) {
            User.this.bets = bets;
            return this;
        }

        public Builder transaction(List<Transaction> transactions) {
            User.this.transactions = transactions;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}

