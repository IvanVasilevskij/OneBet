package ru.onebet.exampleproject.model.coupleteambets;


import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BETS")
public class DotaEvent implements EventBetweenCoupleTeam<DotaTeam> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "DATE_OF_BET")
    @NotNull
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @NotNull
    private DotaTeam teamFirst;

    @ManyToOne(optional = false)
    @NotNull
    private DotaTeam teamSecond;

    @Column(name = "PERSENT_TO_COMAND_ONE")
    @NotNull
    private double percentForTeamFirst;

    @Column(name = "PERSENT_TO_DROW")
    @NotNull
    private double persentForDrow;

    @Column(name = "PERSENT_TO_COMAND_TWO")
    @NotNull
    private double percentForTeamSecond;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public DotaTeam getTeamFirst() {
        return teamFirst;
    }

    @Override
    public DotaTeam getTeamSecond() {
        return teamSecond;
    }

    @Override
    public double getPercentForTeamFirst() {
        return percentForTeamFirst;
    }

    @Override
    public double getPersentForDrow() {
        return persentForDrow;
    }

    @Override
    public double getPercentForTeamSecond() {
        return percentForTeamSecond;
    }

    private DotaEvent() {}

    public static Builder Builder() {
        return new DotaEvent().new Builder();
    }

    public static Mutator Mutate(DotaEvent event) {
        return event.new Mutator();
    }

    public class Builder {

        private Builder() {}

        public Builder date(LocalDateTime date) {
            DotaEvent.this.date = date;
            return this;
        }

        public Builder teamFirst(DotaTeam teamFirst) {
            DotaEvent.this.teamFirst = teamFirst;
            return this;
        }

        public Builder teamSecond(DotaTeam teamSecond) {
            DotaEvent.this.teamSecond = teamSecond;
            return this;
        }

        public Builder percentForTeamFirst(double percentForTeamFirst) {
            DotaEvent.this.percentForTeamFirst = percentForTeamFirst;
            return this;
        }

        public Builder persentToDrow(double persentForDrow) {
            DotaEvent.this.persentForDrow = persentForDrow;
            return this;
        }

        public Builder persentForTeamSecond(double percentForTeamSecond) {
            DotaEvent.this.percentForTeamSecond = percentForTeamSecond;
            return this;
        }

        public DotaEvent build() {
            return DotaEvent.this;
        }
    }

    public class Mutator {

        private Mutator() {}

        public Mutator date(LocalDateTime date) {
            DotaEvent.this.date = date;
            return this;
        }

        public Mutator teamFirst(DotaTeam teamFirst) {
            DotaEvent.this.teamFirst = teamFirst;
            return this;
        }

        public Mutator teamSecond(DotaTeam teamSecond) {
            DotaEvent.this.teamSecond = teamSecond;
            return this;
        }

        public Mutator percentForTeamFirst(double percentForTeamFirst) {
            DotaEvent.this.percentForTeamFirst = percentForTeamFirst;
            return this;
        }

        public Mutator persentToDrow(double persentForDrow) {
            DotaEvent.this.persentForDrow = persentForDrow;
            return this;
        }

        public Mutator persentForTeamSecond(double percentForTeamSecond) {
            DotaEvent.this.percentForTeamSecond = percentForTeamSecond;
            return this;
        }

        public DotaEvent mutate() {
            return DotaEvent.this;
        }
    }
}
