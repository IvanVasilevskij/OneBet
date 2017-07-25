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

    private Transaction() {}

    public static Builder Builder() {
        return new Transaction().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder client(ClientImpl client) {
            Transaction.this.client = client;
            return this;
        }

        public Builder admin(Admin admin) {
            Transaction.this.admin = admin;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            Transaction.this.amount = amount;
            return this;
        }

        public Builder date(LocalDateTime date) {
            Transaction.this.date = date;
            return this;
        }

        public Transaction build() {
            return Transaction.this;
        }
    }
}
