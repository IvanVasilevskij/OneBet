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
@Table(name = "ADMINS")
public class Admin implements User{
    public static final String FindByLogin = "Admin.findByLogin";
    public static final String RootAdminName = "Vasilevskij.Ivan.admin";

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

    public Admin() {
    }

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

    private Admin(final Builder builder) {
        this.login = builder.login;
        this.password = builder.password;
        this.balance = builder.balance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Mutator mutator(Admin admin) {
        return new Mutator(admin);
    }

    public static class Builder {
        private String login;
        private String password;
        private BigDecimal balance;

        public Builder() {}

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }

    public static class Mutator {
        private Admin admin;

        public Mutator(Admin admin) {
            this.admin = admin;
        }

        public Mutator withPassword(String password) {
            this.admin.password = password;
            return this;
        }

        public Mutator withFirstName(String firstName) {
            this.admin.firstName = firstName;
            return this;
        }

        public Mutator withLastName(String lastName) {
            this.admin.lastName = lastName;
            return this;
        }

        public Mutator withBalance(BigDecimal balance) {
            this.admin.balance = balance;
            return this;
        }

        public Mutator withEmail(String email) {
            this.admin.email = email;
            return this;
        }

        public Mutator withTransactions(List<Transaction> transactions) {
            this.admin.transactions = transactions;
            return this;
        }

        public Admin mutate() {
            return admin;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;

        Admin admin = (Admin) o;

        if (getId() != admin.getId()) return false;
        if (getLogin() != null ? !getLogin().equals(admin.getLogin()) : admin.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(admin.getPassword()) : admin.getPassword() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(admin.getFirstName()) : admin.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(admin.getLastName()) : admin.getLastName() != null)
            return false;
        if (getBalance() != null ? !getBalance().equals(admin.getBalance()) : admin.getBalance() != null) return false;
        if (getEmail() != null ? !getEmail().equals(admin.getEmail()) : admin.getEmail() != null) return false;
        return getTransactions() != null ? getTransactions().equals(admin.getTransactions()) : admin.getTransactions() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getBalance() != null ? getBalance().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getTransactions() != null ? getTransactions().hashCode() : 0);
        return result;
    }
}

