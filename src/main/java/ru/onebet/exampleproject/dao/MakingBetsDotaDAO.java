package ru.onebet.exampleproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.model.BetsDota;
import ru.onebet.exampleproject.model.ComandOfDota;
import ru.onebet.exampleproject.model.MakingBets;
import ru.onebet.exampleproject.model.User;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class MakingBetsDotaDAO {

    private final EntityManager em;
    private UserDAO daoU;
    private BetsDotaDAO daoB;
    private TransactionDAO daoT;
    private ComandDotaDAO daoC;
    private CheckOperations sCheck;


    @Autowired
    public MakingBetsDotaDAO(EntityManager em,
                             UserDAO daoU,
                             BetsDotaDAO daoB,
                             TransactionDAO daoT,
                             ComandDotaDAO daoC,
                             CheckOperations sCheck) {
        this.em = em;
        this.daoU = daoU;
        this.daoB = daoB;
        this.daoT = daoT;
        this.daoC = daoC;
        this.sCheck = sCheck;
    }

    public void makeBet(User User,
                        Date timeOfTheGame,
                        ComandOfDota comandOne,
                        ComandOfDota comandTwo,
                        ComandOfDota plasedComand,
                        BigDecimal amount) {

        if(comandOne != plasedComand && comandTwo != plasedComand) throw new IllegalArgumentException("Illegal comand for make bet");

        User user = daoU.findUser(User.getLogin());

        BigDecimal balance = daoU.checkBalanceForBet(User, amount);

        BetsDota bet = daoB.findBet(daoB.makeSearchingMarkOfDotaBet(comandOne, comandTwo, timeOfTheGame));

        daoT.sendMoney(user,amount);

        em.getTransaction().begin();

        MakingBets mBet = MakingBets.newBuilder()
                .date(new Date())
                .user(user)
                .takeOnComand(plasedComand)
                .bet(bet)
                .build();

        em.persist(mBet);

        em.getTransaction().commit();


    }

    public List<BetsDota> allMakedBets() {
        try {
            List<BetsDota> makedBets = em.createQuery("from MakingBets ").getResultList();
            return makedBets;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
