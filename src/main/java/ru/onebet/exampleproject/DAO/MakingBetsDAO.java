package ru.onebet.exampleproject.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.Model.Bets;
import ru.onebet.exampleproject.Model.ComandOfDota;
import ru.onebet.exampleproject.Model.MakingBets;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private TransactionDAO daoT;

    @Autowired
    private ComandDAO daoC;

    public void makeBet(String login,
                        String password,
                        String timeOfTheGame,
                        String comandOne,
                        String comandTwo,
                        String plasedComand,
                        double amount) {

        if(comandOne != plasedComand && comandTwo != plasedComand) throw new IllegalArgumentException("Illegal comand for make bet");

        User user = daoU.checkPassword(login,password);
        double balance = daoU.checkBalanceForBet(user.getLogin(),user.getPassword(),amount);

        Bets bet = daoB.findBet(comandOne+comandTwo+timeOfTheGame);

        daoT.sendMoney(user,amount);

        em.getTransaction().begin();

        MakingBets mBet = new MakingBets();

        mBet.setDate(new Date());
        mBet.setUser(user);
        mBet.setTakeOnComand(daoC.findComandByComandName(plasedComand));
        mBet.setBet(bet);

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
