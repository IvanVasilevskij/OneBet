package ru.onebet.exampleproject.Model;


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

    public void setId(int id) {
        this.id = id;
    }

    public String getComandName() {
        return comandName;
    }

    public void setComandName(String comandName) {
        this.comandName = comandName;
    }

    public String getRoleMid() {
        return roleMid;
    }

    public void setRoleMid(String roleMid) {
        this.roleMid = roleMid;
    }

    public String getRoleCarry() {
        return roleCarry;
    }

    public void setRoleCarry(String roleCarry) {
        this.roleCarry = roleCarry;
    }

    public String getRoleHard() {
        return roleHard;
    }

    public void setRoleHard(String roleHard) {
        this.roleHard = roleHard;
    }

    public String getRoleSupFour() {
        return roleSupFour;
    }

    public void setRoleSupFour(String roleSupFour) {
        this.roleSupFour = roleSupFour;
    }

    public String getRoleSupFive() {
        return roleSupFive;
    }

    public void setRoleSupFive(String roleSupFive) {
        this.roleSupFive = roleSupFive;
    }
}
