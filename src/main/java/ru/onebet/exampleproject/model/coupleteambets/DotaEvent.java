package ru.onebet.exampleproject.model.coupleteambets;


import com.sun.istack.internal.NotNull;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BETS")
@NamedQueries({
        @NamedQuery(
                name = DotaEvent.FindEventsByTeamName,
                query = "select u from DotaEvent u where u.teamFirst.teamName = :teamName or u.teamSecond.teamName = :teamName"
        ),
        @NamedQuery(
                name = DotaEvent.FindEventsByEnteredDate,
                query = "select u from DotaEvent u where u.date > :dateS and u.date < :dateE"
        )
})
public class DotaEvent implements EventBetweenCoupleTeam<DotaTeam> {
    public static final String FindEventsByTeamName = "DotaTeam.FindEventsByTeamName";
    public static final String FindEventsByEnteredDate = "DotaTeam.FindEventsByEnteredDate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;
    @Column(name = "DATE_OF_BET")
    @NotNull
    private  LocalDateTime date;
    @ManyToOne(optional = false)
    @NotNull
    private DotaTeam teamFirst;
    @ManyToOne(optional = false)
    @NotNull
    private  DotaTeam teamSecond;
    @Column(name = "PERSENT_TO_COMAND_ONE")
    @NotNull
    private double percentForTeamFirst;
    @Column(name = "PERSENT_TO_DROW")
    @NotNull
    private double persentForDrow;
    @Column(name = "PERSENT_TO_COMAND_TWO")
    @NotNull
    private double percentForTeamSecond;

    public DotaEvent() {
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

    private DotaEvent(final Builder builder) {
        this.teamFirst = builder.teamFirst;
        this.teamSecond = builder.teamSecond;
        this.date = builder.date;
        this.percentForTeamFirst = builder.percentForTeamFirst;
        this.persentForDrow = builder.persentForDrow;
        this.percentForTeamSecond = builder.percentForTeamSecond;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime date;
        private DotaTeam teamFirst;
        private DotaTeam teamSecond;
        private double percentForTeamFirst;
        private double persentForDrow;
        private double percentForTeamSecond;

        public Builder() {}

        public Builder withDate(final LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withTeamFirst(final DotaTeam teamFirst) {
            this.teamFirst = teamFirst;
            return this;
        }

        public Builder withTeamSecond(final DotaTeam teamSecond) {
            this.teamSecond = teamSecond;
            return this;
        }

        public Builder withPercentForTeamFirst(final double percentForTeamFirst) {
            this.percentForTeamFirst = percentForTeamFirst;
            return this;
        }

        public Builder withPersentToDrow(final double persentForDrow) {
            this.persentForDrow = persentForDrow;
            return this;
        }

        public Builder withPersentForTeamSecond(final double percentForTeamSecond) {
            this.percentForTeamSecond = percentForTeamSecond;
            return this;
        }

        public DotaEvent build() {
            return new DotaEvent(this);
        }
    }

    @Override
    public String toString() {
        return teamFirst.getTeamName() +
                "(" + getPercentForTeamFirst() + ")" +
                " vs " +
                teamSecond.getTeamName() +
                "(" + getPercentForTeamSecond() + ")" +
                date.getDayOfMonth() + "." +
                (date.getMonthValue() < 10 ? ("0" + date.getMonthValue()) : (date.getMonthValue())) + "." +
                date.getYear() + " " +
                date.getHour() + ":" +
                date.getMinute();
    }
}
