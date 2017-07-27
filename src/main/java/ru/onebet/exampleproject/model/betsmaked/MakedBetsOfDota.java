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
    private LocalDateTime date;

    @OneToOne
    private DotaTeam bettingTeam;

    @ManyToOne(optional = false)
    private ClientImpl client;

    @ManyToOne(optional = false)
    private DotaEvent event;

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

    private MakedBetsOfDota() {}

    public static Builder Builder() {
        return new MakedBetsOfDota().new Builder();
    }

    public static Mutator Mutate(MakedBetsOfDota makedBet) {
        return makedBet.new Mutator();
    }

    public class Builder {

        private Builder() {}

        public Builder date(LocalDateTime date) {
            MakedBetsOfDota.this.date = date;
            return this;
        }

        public Builder bettingTeam(DotaTeam bettingTeam) {
            MakedBetsOfDota.this.bettingTeam = bettingTeam;
            return this;
        }

        public Builder client(ClientImpl clientImpl) {
            MakedBetsOfDota.this.client = clientImpl;
            return this;
        }

        public Builder event(DotaEvent event) {
            MakedBetsOfDota.this.event = event;
            return this;
        }

        public MakedBetsOfDota build() {
            return MakedBetsOfDota.this;
        }
    }

    public class Mutator {

        private Mutator() {}

        public Mutator date(LocalDateTime date) {
            MakedBetsOfDota.this.date = date;
            return this;
        }

        public Mutator bettingTeam(DotaTeam bettingTeam) {
            MakedBetsOfDota.this.bettingTeam = bettingTeam;
            return this;
        }

        public Mutator client(ClientImpl client) {
            MakedBetsOfDota.this.client = client;
            return this;
        }

        public Mutator event(DotaEvent event) {
            MakedBetsOfDota.this.event = event;
            return this;
        }

        public MakedBetsOfDota mutate() {
            return MakedBetsOfDota.this;
        }
    }

}
