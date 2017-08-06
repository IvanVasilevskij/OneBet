package ru.onebet.exampleproject.model.users;


import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.Transaction;
import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NamedQueries(
        @NamedQuery(
                name = ClientImpl.FindByLogin,
                query = "select u from ClientImpl u where u.login = :login"
        )
)
@Table(name = "CLIENTS")
public class ClientImpl implements Client<MakedBetsOfDota>{
    public static final String FindByLogin = "ClientImpl.findByLogin";
    public static final String ClientForEmitMoneyOperations = "Vasilevskij.Ivan.client";


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
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<MakedBetsOfDota> bets;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public ClientImpl() {
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
    public List<MakedBetsOfDota> getBets() {
        return bets;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    private ClientImpl(final Builder builder) {
        this.login = builder.login;
        this.password = builder.password;
        this.balance = builder.balance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Mutator mutator(ClientImpl client) {
        return new Mutator(client);
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

        public ClientImpl build() {
            return new ClientImpl(this);
        }
    }

    public static class Mutator {
        private ClientImpl client;

        public Mutator(ClientImpl client) {
            this.client = client;
        }

        public Mutator withPassword(String password) {
            this.client.password = password;
            return this;
        }

        public Mutator withFirstName(String firstName) {
            this.client.firstName = firstName;
            return this;
        }

        public Mutator withLastName(String lastName) {
            this.client.lastName = lastName;
            return this;
        }

        public Mutator withBalance(BigDecimal balance) {
            this.client.balance = balance;
            return this;
        }

        public Mutator withEmail(String email) {
            this.client.email = email;
            return this;
        }

        public Mutator withBets(List<MakedBetsOfDota> bets) {
            this.client.bets = bets;
            return this;
        }

        public Mutator withTransactions(List<Transaction> transactions) {
            this.client.transactions = transactions;
            return this;
        }

        public ClientImpl mutate() {
            return client;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientImpl)) return false;

        ClientImpl client = (ClientImpl) o;

        if (getId() != client.getId()) return false;
        if (getLogin() != null ? !getLogin().equals(client.getLogin()) : client.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(client.getPassword()) : client.getPassword() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(client.getFirstName()) : client.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(client.getLastName()) : client.getLastName() != null)
            return false;
        if (getBalance() != null ? !getBalance().equals(client.getBalance()) : client.getBalance() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(client.getEmail()) : client.getEmail() != null) return false;
        if (getBets() != null ? !getBets().equals(client.getBets()) : client.getBets() != null) return false;
        return getTransactions() != null ? getTransactions().equals(client.getTransactions()) : client.getTransactions() == null;
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
        result = 31 * result + (getBets() != null ? getBets().hashCode() : 0);
        result = 31 * result + (getTransactions() != null ? getTransactions().hashCode() : 0);
        return result;
    }
}

