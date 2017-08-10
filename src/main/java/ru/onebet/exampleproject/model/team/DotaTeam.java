package ru.onebet.exampleproject.model.team;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@NamedQueries(
        @NamedQuery(
                name = DotaTeam.FindByTeamName,
                query = "select u from DotaTeam u where u.teamName = :teamName"
        )
)
@Table(name = "COMADS")
public class DotaTeam implements Team {
    public static final String FindByTeamName = "DotaTeam.FindByTeamName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "COMAND_NAME")
    @NotNull
    private String teamName;
    @Column(name = "ROLE_MID")
    @NotNull
    private String roleMid = "unknown";
    @Column(name = "ROLE_CARRY")
    @NotNull
    private String roleCarry = "unknown";
    @Column(name = "ROLE_HARD")
    @NotNull
    private String roleHard = "unknown";
    @Column(name = "ROLE_SUP_FOUR")
    @NotNull
    private String roleSupFour = "unknown";
    @Column(name = "ROLE_SUP_FIVE")
    @NotNull
    private String roleSupFive = "unknown";

    public DotaTeam() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    public String getRoleMid() {
        return roleMid;
    }

    public String getRoleCarry() {
        return roleCarry;
    }

    public String getRoleHard() {
        return roleHard;
    }

    public String getRoleSupFour() {
        return roleSupFour;
    }

    public String getRoleSupFive() {
        return roleSupFive;
    }

    private DotaTeam(Builder builder) {
        this.teamName = builder.teamName;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static Mutator mutator(DotaTeam team) {
        return new Mutator(team);
    }

    public static class Builder {
        private String teamName;


        private Builder() {
        }

        public Builder withTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public DotaTeam build() {
            return new DotaTeam(this);
        }
    }

    public static class Mutator {
        DotaTeam team;


        private Mutator(DotaTeam team) {
            this.team = team;
        }

        public Mutator withTeamName(String teamName) {
            this.team.teamName = teamName;
            return this;
        }

        public Mutator withRoleMid(String roleMid) {
            this.team.roleMid = roleMid;
            return this;
        }

        public Mutator withRoleCarry(String roleCarry) {
            this.team.roleCarry = roleCarry;
            return this;
        }

        public Mutator withRoleHard(String roleHard) {
            this.team.roleHard = roleHard;
            return this;
        }

        public Mutator withRoleSupFour(String roleSupFour) {
            this.team.roleSupFour = roleSupFour;
            return this;
        }

        public Mutator withRoleSupFive(String roleSupFive) {
            this.team.roleSupFive = roleSupFive;
            return this;
        }

        public DotaTeam mutate() {
            return team;
        }
    }

    @Override
    public String toString() {
        return "DotaTeam{" +
                "teamName='" + teamName + '\'' +
                ", roleMid='" + roleMid + '\'' +
                ", roleCarry='" + roleCarry + '\'' +
                ", roleHard='" + roleHard + '\'' +
                ", roleSupFour='" + roleSupFour + '\'' +
                ", roleSupFive='" + roleSupFive + '\'' +
                '}';
    }
}
