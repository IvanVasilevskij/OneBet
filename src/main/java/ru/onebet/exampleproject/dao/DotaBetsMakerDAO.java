package ru.onebet.exampleproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.model.DotaBets;
import ru.onebet.exampleproject.model.DotaTeam;
import ru.onebet.exampleproject.model.DotaBetsMaked;
import ru.onebet.exampleproject.model.User;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DotaBetsMakerDAO {

    private final EntityManager em;
    private UserDAO daoU;
    private DotaBetsDAO daoB;
    private TransactionDAO daoT;
    private DotaTeamDAO daoC;
    private CheckOperations sCheck;


    @Autowired
    public DotaBetsMakerDAO(EntityManager em,
                            UserDAO daoU,
                            DotaBetsDAO daoB,
                            TransactionDAO daoT,
                            DotaTeamDAO daoC,
                            CheckOperations sCheck) {
        this.em = em;
        this.daoU = daoU;
        this.daoB = daoB;
        this.daoT = daoT;
        this.daoC = daoC;
        this.sCheck = sCheck;
    }

    public void makeBet(User User,
                        DotaBets betsDota,
                        DotaTeam plasedComand,
                        BigDecimal amount) {

        if(betsDota.getTeamOne() != plasedComand && betsDota.getTemaTwo() != plasedComand) throw new IllegalArgumentException("Illegal comand for make bet");

        User user = daoU.findUser(User.getLogin());

        BigDecimal balance = daoU.checkBalanceForBet(User, amount);

        DotaBets bet = daoB.findBet(daoB.makeSearchingMarkOfDotaBet(betsDota.getTeamOne(), betsDota.getTemaTwo(), betsDota.getDate()));

        daoT.sendMoney(user,amount);

        em.getTransaction().begin();

        DotaBetsMaked mBet = DotaBetsMaked.newBuilder()
                .date(new Date())
                .user(user)
                .takeOnComand(plasedComand)
                .bet(bet)
                .build();

        em.persist(mBet);

        em.getTransaction().commit();


    }

    public List<DotaBets> allMakedBets() {
        try {
            List<DotaBets> makedBets = em.createQuery("from DotaBetsMaked ").getResultList();
            return makedBets;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
