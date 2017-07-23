package ru.onebet.exampleproject.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID", updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private User root;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "DATE")
    private Date date;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public User getRoot() {
        return root;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    private Transaction() {}

    public static Builder newBuilder() {
        return new Transaction().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder user(User user) {
            Transaction.this.user = user;
            return this;
        }

        public Builder root(User root) {
            Transaction.this.root = root;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            Transaction.this.amount = amount;
            return this;
        }

        public Builder date(Date date) {
            Transaction.this.date = date;
            return this;
        }

        public Transaction build() {
            return Transaction.this;
        }
    }
}
