package ru.onebet.exampleproject.model.betsmaked;

import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NamedQueries(
        @NamedQuery(
                name = MakedBetsOfDota.FindBetWithClient,
                query = "select e from MakedBetsOfDota e where e.client.login = :login")
)
@Table(name = "MAKING_BETS")
public class MakedBetsOfDota implements MakedBets<DotaTeam, DotaEvent> {
    public static final String FindBetWithClient = "MakedBetsOfDota.FindBetWithClient";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "DATE_OF_BET")
    @NotNull
    private LocalDateTime date;

    @OneToOne
    private DotaTeam bettingTeam;

    @ManyToOne(optional = false)
    private ClientImpl client;

    @ManyToOne(optional = false)
    private DotaEvent event;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    public MakedBetsOfDota() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public DotaTeam getBettingTeam() {
        return bettingTeam;
    }

    @Override
    public ClientImpl getClient() {
        return client;
    }

    @Override
    public DotaEvent getEvent() {
        return event;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    private MakedBetsOfDota(final Builder builder) {
        this.event = builder.event;
        this.date = builder.date;
        this.bettingTeam = builder.bettingTeam;
        this.client = builder.client;
        this.amount = builder.amount;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime date;
        private DotaTeam bettingTeam;
        private ClientImpl client;
        private DotaEvent event;
        private BigDecimal amount;

        public Builder() {}

        public Builder withDate(final LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withBettingTeam(final DotaTeam bettingTeam) {
            this.bettingTeam = bettingTeam;
            return this;
        }

        public Builder withClient(final ClientImpl client) {
            this.client = client;
            return this;
        }

        public Builder withEvent(final DotaEvent event) {
            this.event = event;
            return this;
        }

        public Builder withAmount(final BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public MakedBetsOfDota build() {
            return new MakedBetsOfDota(this);
        }
    }

    @Override
    public String toString() {
        return "CLIENT=" +
                client.getLogin() +
                "___Bet{" +
                "bettingTeam=" + bettingTeam.getTeamName() +
                ", event=" + event +
                ", amount=" + amount +
                '}';
    }
}
