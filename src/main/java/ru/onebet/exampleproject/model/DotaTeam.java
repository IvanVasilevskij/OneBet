package ru.onebet.exampleproject.model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@NamedQueries(
        @NamedQuery(
                name = DotaTeam.FindByLogin,
                query = "select u from DotaTeam u where u.teamName = :teamName"
        )
)
@Table(name = "COMADS")
public class DotaTeam implements Team {
    public static final String FindByLogin = "DotaTeam.FindByLogin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private int id;

    @Column(name = "COMAND_NAME")
    @NotNull
    private String teamName;

    @Column(name = "ROLE_MID")
    @NotNull
    private String roleMid;

    @Column(name = "ROLE_CARRY")
    @NotNull
    private String roleCarry;

    @Column(name = "ROLE_HARD")
    @NotNull
    private String roleHard;

    @Column(name = "ROLE_SUP_FOUR")
    @NotNull
    private String roleSupFour;

    @Column(name = "ROLE_SUP_FIVE")
    @NotNull
    private String roleSupFive;

    public int getId() {
        return id;
    }

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

    private DotaTeam() {}

    public static Builder newBuilder() {
        return new DotaTeam().new Builder();
    }

    public static Builder mutate(DotaTeam comandOfDotaImpl) {
        return comandOfDotaImpl.new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder comandName(String comandName) {
            DotaTeam.this.teamName = comandName;
            return this;
        }

        public Builder roleMid(String roleMid) {
            DotaTeam.this.roleMid = roleMid;
            return this;
        }

        public Builder roleCarry(String roleCarry) {
            DotaTeam.this.roleCarry = roleCarry;
            return this;
        }

        public Builder roleHard(String roleHard){
            DotaTeam.this.roleHard = roleHard;
            return this;
        }

        public Builder roleSupFour(String roleSupFour) {
            DotaTeam.this.roleSupFour = roleSupFour;
            return this;
        }

        public Builder roleSupFive(String roleSupFive){
            DotaTeam.this.roleSupFive = roleSupFive;
            return this;
        }

        public DotaTeam build() {
            return DotaTeam.this;
        }
    }
}
