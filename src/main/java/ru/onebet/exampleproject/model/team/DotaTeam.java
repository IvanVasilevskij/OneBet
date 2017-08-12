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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DotaTeam)) return false;

        DotaTeam team = (DotaTeam) o;

        if (getId() != team.getId()) return false;
        if (getTeamName() != null ? !getTeamName().equals(team.getTeamName()) : team.getTeamName() != null)
            return false;
        if (getRoleMid() != null ? !getRoleMid().equals(team.getRoleMid()) : team.getRoleMid() != null) return false;
        if (getRoleCarry() != null ? !getRoleCarry().equals(team.getRoleCarry()) : team.getRoleCarry() != null)
            return false;
        if (getRoleHard() != null ? !getRoleHard().equals(team.getRoleHard()) : team.getRoleHard() != null)
            return false;
        if (getRoleSupFour() != null ? !getRoleSupFour().equals(team.getRoleSupFour()) : team.getRoleSupFour() != null)
            return false;
        return getRoleSupFive() != null ? getRoleSupFive().equals(team.getRoleSupFive()) : team.getRoleSupFive() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getTeamName() != null ? getTeamName().hashCode() : 0);
        result = 31 * result + (getRoleMid() != null ? getRoleMid().hashCode() : 0);
        result = 31 * result + (getRoleCarry() != null ? getRoleCarry().hashCode() : 0);
        result = 31 * result + (getRoleHard() != null ? getRoleHard().hashCode() : 0);
        result = 31 * result + (getRoleSupFour() != null ? getRoleSupFour().hashCode() : 0);
        result = 31 * result + (getRoleSupFive() != null ? getRoleSupFive().hashCode() : 0);
        return result;
    }
}
