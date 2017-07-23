package ru.onebet.exampleproject.model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@NamedQueries(
        @NamedQuery(
                name = ComandOfDota.FindByLogin,
                query = "select u from ComandOfDota u where u.comandName = :comandName"
        )
)
@Table(name = "COMADS")
public class ComandOfDota {
    public static final String FindByLogin = "ComandOfDota.findByLogin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private int id;

    @Column(name = "COMAND_NAME")
    @NotNull
    private String comandName;

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

    public String getComandName() {
        return comandName;
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

    private ComandOfDota() {}

    public static Builder newBuilder() {
        return new ComandOfDota().new Builder();
    }

    public static Builder newBuilder(ComandOfDota comandOfDota) {
        return comandOfDota.new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder comandName(String comandName) {
            ComandOfDota.this.comandName = comandName;
            return this;
        }

        public Builder roleMid(String roleMid) {
            ComandOfDota.this.roleMid = roleMid;
            return this;
        }

        public Builder roleCarry(String roleCarry) {
            ComandOfDota.this.roleCarry = roleCarry;
            return this;
        }

        public Builder roleHard(String roleHard){
            ComandOfDota.this.roleHard = roleHard;
            return this;
        }

        public Builder roleSupFour(String roleSupFour) {
            ComandOfDota.this.roleSupFour = roleSupFour;
            return this;
        }

        public Builder roleSupFive(String roleSupFive){
            ComandOfDota.this.roleSupFive = roleSupFive;
            return this;
        }

        public ComandOfDota build() {
            return ComandOfDota.this;
        }
    }
}
