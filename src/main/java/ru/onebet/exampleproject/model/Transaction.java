package ru.onebet.exampleproject.model;


import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

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
}
