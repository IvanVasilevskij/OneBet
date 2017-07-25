package ru.onebet.exampleproject.dao.betmakerdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.dao.TransactionDAO;
import ru.onebet.exampleproject.dao.betsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;
import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DotaBetsMakerDAO implements BetsMakerDAO<DotaTeam, DotaEvent>{

    private final EntityManager em;
    private UserDAOImpl daoUser;
    private TransactionDAO daoTransaction;
    private DotaEventsDAO daoEventsDota;


    @Autowired
    public DotaBetsMakerDAO(EntityManager em,
                            UserDAOImpl daoUser,
                            TransactionDAO daoTransaction,
                            DotaEventsDAO daoEventsDota) {
        this.em = em;
        this.daoUser = daoUser;
        this.daoTransaction = daoTransaction;
        this.daoEventsDota = daoEventsDota;
    }

    public void makeBet(ClientImpl client,
                        DotaEvent event,
                        DotaTeam bettingTeam,
                        BigDecimal amount) {

        daoEventsDota.checkThatThisEventHaveTheTeam(event, bettingTeam);

        daoUser.checkBalanceForBet(client, amount);

        daoTransaction.sendMoney(client, amount);

        em.getTransaction().begin();

        MakedBetsOfDota mBet = MakedBetsOfDota.Builder()
                .date(LocalDateTime.now())
                .client(client)
                .bettingTeam(bettingTeam)
                .event(event)
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
