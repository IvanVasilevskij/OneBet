package ru.onebet.exampleproject.dao.betmakerdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;
import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class DotaBetsMakerDAO implements BetsMakerDAO<DotaTeam, DotaEvent, MakedBetsOfDota>{

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public DotaBetsMakerDAO(EntityManager em) {
        this.em = em;
    }

    public MakedBetsOfDota makeBet(ClientImpl client,
                        Admin root,
                        DotaEvent event,
                        DotaTeam bettingTeam,
                        BigDecimal amount) {

        MakedBetsOfDota mBet = MakedBetsOfDota.Builder()
                .withDate(LocalDateTime.now())
                .withClient(client)
                .withBettingTeam(bettingTeam)
                .withEvent(event)
                .withAmount(amount)
                .build();

        em.persist(mBet);
        em.refresh(client);

        return mBet;
    }

    public List<MakedBetsOfDota> allMakedBets() {
            return em.createQuery("from MakedBetsOfDota", MakedBetsOfDota.class).getResultList();
    }

    public List<MakedBetsOfDota> allBetsOfOneClient(String login) {
        return em.createNamedQuery(MakedBetsOfDota.FindBetWithClient, MakedBetsOfDota.class)
                .setParameter("login", login)
                .getResultList();
    }
}
