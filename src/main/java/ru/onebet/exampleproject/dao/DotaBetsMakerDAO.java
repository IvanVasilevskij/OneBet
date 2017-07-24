package ru.onebet.exampleproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.dao.betsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;
import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;
import ru.onebet.exampleproject.model.User;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DotaBetsMakerDAO {

    private final EntityManager em;
    private UserDAO daoU;
    private DotaEventsDAO daoB;
    private TransactionDAO daoT;
    private DotaTeamDAO daoC;
    private CheckOperations sCheck;


    @Autowired
    public DotaBetsMakerDAO(EntityManager em,
                            UserDAO daoU,
                            DotaEventsDAO daoB,
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
                        DotaEvent dotaEvent,
                        DotaTeam plasedComand,
                        BigDecimal amount) {

        if(dotaEvent.getTeamOne() != plasedComand && dotaEvent.getTeamTwo() != plasedComand) throw new IllegalArgumentException("Illegal comand for make bet");

        User user = daoU.findUser(User.getLogin());

        BigDecimal balance = daoU.checkBalanceForBet(User, amount);

        daoT.sendMoney(user,amount);

        em.getTransaction().begin();

        MakedBetsOfDota mBet = MakedBetsOfDota.newBuilder()
                .date(new Date())
                .user(user)
                .takeOnComand(plasedComand)
                .bet(dotaEvent)
                .build();

        em.persist(mBet);

        em.getTransaction().commit();
    }

    public List<DotaEvent> allMakedBets() {
        try {
            List<DotaEvent> makedBets = em.createQuery("from MakedBetsOfDota ").getResultList();
            return makedBets;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
