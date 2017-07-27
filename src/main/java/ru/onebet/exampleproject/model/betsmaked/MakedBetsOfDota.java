package ru.onebet.exampleproject.model.betsmaked;

import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MAKING_BETS")
public class MakedBetsOfDota implements MakedBets<DotaTeam, DotaEvent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "DATE_OF_BET")
    @NotNull
    private final LocalDateTime date;

    @OneToOne
    private final DotaTeam bettingTeam;

    @ManyToOne(optional = false)
    private final ClientImpl client;

    @ManyToOne(optional = false)
    private final DotaEvent event;

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

    public ClientImpl getClient() {
        return client;
    }

    @Override
    public DotaEvent getEvent() {
        return event;
    }

    private MakedBetsOfDota(final Builder builder) {
        this.event = builder.event;
        this.date = builder.date;
        this.bettingTeam = builder.bettingTeam;
        this.client = builder.client;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime date;
        private DotaTeam bettingTeam;
        private ClientImpl client;
        private DotaEvent event;

        public Builder() {}

        public Builder withDate(final LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withBettingTeam(final DotaTeam bettingTeam) {
            this.bettingTeam = bettingTeam;
            return this;
        }

        public Builder withClient(final ClientImpl clientImpl) {
            this.client = clientImpl;
            return this;
        }

        public Builder withEvent(final DotaEvent event) {
            this.event = event;
            return this;
        }

        public MakedBetsOfDota build() {
            return new MakedBetsOfDota(this);
        }
    }
}
