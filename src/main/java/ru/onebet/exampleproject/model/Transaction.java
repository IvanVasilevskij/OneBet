package ru.onebet.exampleproject.model;


import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(
                name = Transaction.FindByLoginClient,
                query = "select t from Transaction t where t.client.login = :login"
        ),
        @NamedQuery(
                name = Transaction.FindByLoginAdmin,
                query = "select t from Transaction t where t.admin.login = :login"
        )
})
@Table(name = "TRANSACTIONS")
public class Transaction {
    public static final String FindByLoginAdmin = "Transaction.findByLoginAdmin";
    public static final String FindByLoginClient = "Transaction.findByLoginClient";

    @Id
    @Column(name = "ID", updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    private ClientImpl client;
    @ManyToOne(optional = false)
    private Admin admin;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "DATE")
    private LocalDateTime date;

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public ClientImpl getClient() {
        return client;
    }

    public Admin getAdmin() {
        return admin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    private Transaction(Builder builder) {
        this.client = builder.client;
        this.admin = builder.admin;
        this.date = builder.date;
        this.amount = builder.amount;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private ClientImpl client;
        private Admin admin;
        private BigDecimal amount;
        private LocalDateTime date;

        private Builder() {}

        public Builder withClient(ClientImpl client) {
            this.client = client;
            return this;
        }

        public Builder withAdmin(Admin admin) {
            this.admin = admin;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    @Override
    public String toString() {
        return "client=" + client.getLogin() +
                ", admin=" + admin.getLogin() +
                ", amount=" + amount +
                ", date=" + date;
    }
}
