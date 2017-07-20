package ru.onebet.exampleproject.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.Model.ComandOfDota;

import javax.persistence.EntityManager;
import java.util.Date;

@Service
public class MakingBetsDAO {
    private final EntityManager em;

    @Autowired
    public MakingBetsDAO(EntityManager em) {
        this.em = em;
    }

    @Autowired
    private UserDAO daoU;

    @Autowired
    private BetsDAO daoB;

    public void makeBet(String login,
                        String password,
                        Date timeOfTheGame,
                        ComandOfDota comandOne,
                        ComandOfDota comandTwo,
                        ComandOfDota plasedComand,
                        double amount) {
        if(daoU.checkPassword(login,password) == false) throw new IllegalArgumentException("User didn't exist");

    }
}
