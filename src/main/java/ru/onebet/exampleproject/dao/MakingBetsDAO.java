package ru.onebet.exampleproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.Bets;
import ru.onebet.exampleproject.model.MakingBets;
import ru.onebet.exampleproject.model.User;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class MakingBetsDAO {

    private final EntityManager em;
    private UserDAO daoU;
    private BetsDAO daoB;
    private TransactionDAO daoT;
    private ComandDAO daoC;

    @Autowired
    public MakingBetsDAO(EntityManager em,
                         UserDAO daoU,
                         BetsDAO daoB,
                         TransactionDAO daoT,
                         ComandDAO daoC) {
        this.em = em;
        this.daoU = daoU;
        this.daoB = daoB;
        this.daoT = daoT;
        this.daoC = daoC;
    }

    public void makeBet(String login,
                        String password,
                        String timeOfTheGame,
                        String comandOne,
                        String comandTwo,
                        String plasedComand,
                        String amount) {

        if(comandOne != plasedComand && comandTwo != plasedComand) throw new IllegalArgumentException("Illegal comand for make bet");

        User user = daoU.findUser(login);

        BigDecimal balance = daoU.checkBalanceForBet(login,password,amount);

        Bets bet = daoB.findBet(comandOne+comandTwo+timeOfTheGame);

        daoT.sendMoney(user,amount);

        em.getTransaction().begin();

        MakingBets mBet = MakingBets.newBuilder()
                .date(new Date())
                .user(user)
                .takeOnComand(daoC.findComandByComandName(plasedComand))
                .bet(bet)
                .build();

        em.persist(mBet);

        em.getTransaction().commit();


    }

    public List<Bets> allMakedBets() {
        try {
            List<Bets> makedBets = em.createQuery("from MakingBets ").getResultList();
            return makedBets;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
