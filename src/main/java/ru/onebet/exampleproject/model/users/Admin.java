package ru.onebet.exampleproject.model.users;


import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NamedQueries(
        @NamedQuery(
                name = Admin.FindByLogin,
                query = "select u from Admin u where u.login = :login"
        )
)
@Table(name = "CLIENTS")
public class Admin implements User{
    public static final String FindByLogin = "Admin.findByLogin";
    public static final String RootAdminName = "Vasilevskij.Ivan";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;

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

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public int getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    private Admin() {}

    public static Builder Builder() {
        return new Admin().new Builder();
    }

    public static Mutator Mutate(Admin admin) {
        return admin.new Mutator();
    }

    public class Builder {

        private Builder() {}

        public Builder login(String login) {
            Admin.this.login = login;
            return this;
        }

        public Builder password(String password) {
            Admin.this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            Admin.this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            Admin.this.lastName = lastName;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            Admin.this.balance = balance;
            return this;
        }

        public Builder email(String email) {
            Admin.this.email = email;
            return this;
        }

        public Builder transaction(List<Transaction> transactions) {
            Admin.this.transactions = transactions;
            return this;
        }

        public Admin build() {
            return Admin.this;
        }
    }

    public class Mutator {

        private Mutator() {}

        public Mutator login(String login) {
            Admin.this.login = login;
            return this;
        }

        public Mutator password(String password) {
            Admin.this.password = password;
            return this;
        }

        public Mutator firstName(String firstName) {
            Admin.this.firstName = firstName;
            return this;
        }

        public Mutator lastName(String lastName) {
            Admin.this.lastName = lastName;
            return this;
        }

        public Mutator balance(BigDecimal balance) {
            Admin.this.balance = balance;
            return this;
        }

        public Mutator email(String email) {
            Admin.this.email = email;
            return this;
        }

        public Mutator transaction(List<Transaction> transactions) {
            Admin.this.transactions = transactions;
            return this;
        }

        public Admin mutate() {
            return Admin.this;
        }
    }
}

