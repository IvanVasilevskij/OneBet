package ru.onebet.exampleproject.Model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COMADS")
public class AllComand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( insertable = false, updatable = false)
    private int id;

    @Column(name = "COMAND_NAME")
    @NotNull
    private String comandName;

    @Column(name = "ROLE_ONE")
    @NotNull
    private String roleOne;

    @Column(name = "ROLE_TWO")
    @NotNull
    private String roleTwo;

    @Column(name = "ROLE_THREE")
    @NotNull
    private String roleThree;

    @Column(name = "ROLE_FOUR")
    @NotNull
    private String roleFour;

    @Column(name = "ROLE_FIVE")
    @NotNull
    private String roleFive;

    public String getRoleOne() {
        return roleOne;
    }

    public void setRoleOne(String roleOne) {
        this.roleOne = roleOne;
    }

    public String getRoleTwo() {
        return roleTwo;
    }

    public void setRoleTwo(String roleTwo) {
        this.roleTwo = roleTwo;
    }

    public String getRoleThree() {
        return roleThree;
    }

    public void setRoleThree(String roleThree) {
        this.roleThree = roleThree;
    }

    public String getRoleFour() {
        return roleFour;
    }

    public void setRoleFour(String roleFour) {
        this.roleFour = roleFour;
    }

    public String getRoleFive() {
        return roleFive;
    }

    public void setRoleFive(String roleFive) {
        this.roleFive = roleFive;
    }

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

}
