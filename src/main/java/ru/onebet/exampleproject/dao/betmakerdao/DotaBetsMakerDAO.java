package ru.onebet.exampleproject.dao.betmakerdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.onebet.exampleproject.dao.TransactionDAO;
import ru.onebet.exampleproject.dao.eventsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;
import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DotaBetsMakerDAO implements BetsMakerDAO<DotaTeam, DotaEvent, MakedBetsOfDota>{

    private final EntityManager em;



    @Autowired
    public DotaBetsMakerDAO(EntityManager em) {
        this.em = em;
    }

    public void makeBet(ClientImpl client,
                        Admin root,
                        DotaEvent event,
                        DotaTeam bettingTeam,
                        BigDecimal amount) {

        MakedBetsOfDota mBet = MakedBetsOfDota.Builder()
                .withDate(LocalDateTime.now())
                .withClient(client)
                .withBettingTeam(bettingTeam)
                .withEvent(event)
                .build();

        em.persist(mBet);
        em.refresh(client);

    }

    public List<MakedBetsOfDota> allMakedBets() {
            return em.createQuery("from MakedBetsOfDota", MakedBetsOfDota.class).getResultList();
    }
}
