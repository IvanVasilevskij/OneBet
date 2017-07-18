package ru.onebet.exampleproject.Model;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Comand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMAND_ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "COMAND_NAME")
    @NotNull
    private String comandName;

    @Column(name = "COMPOSITION")
    @NotNull
    private ArrayList<String> compositionsOfComand;

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

    public ArrayList<String> getCompositionsOfComand() {
        return compositionsOfComand;
    }

    public void setCompositionsOfComand(ArrayList<String> compositionsOfComand) {
        this.compositionsOfComand = compositionsOfComand;
    }
}
