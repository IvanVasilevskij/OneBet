package ru.onebet.exampleproject.dao.userdao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.users.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO{
    @PersistenceContext
    private final EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserDAOImpl(EntityManager em, BCryptPasswordEncoder passwordEncoder) {
        this.em = em;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClientImpl createClient(String login, String password) throws EntityExistsException {
                ClientImpl client = ClientImpl.builder()
                        .withLogin(login)
                        .withPassword(password)
                        .withBalance(BigDecimal.ZERO.setScale(2))
                        .build();
                em.persist(client);
                return client;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientImpl findClient(String login) {
        try {
            return em.createNamedQuery(ClientImpl.FindByLogin, ClientImpl.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public Admin createAdmin(String login, String password) throws EntityExistsException {
                Admin admin = Admin.builder()
                        .withLogin(login)
                        .withPassword(password)
                        .withBalance(BigDecimal.ZERO.setScale(2))
                        .build();
                em.persist(admin);
                return admin;
    }

    @Override
    public Admin findAdmin(String login) {
        try {
            return em.createNamedQuery(Admin.FindByLogin, Admin.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public void updateInformationForAdmin(Admin admin, String firstName, String lastName, String email) {
        String trimmedFirstName = firstName.trim();
        String trimmedLastName = lastName.trim();
        String trimmedEmail = email.trim();

        admin = Admin.mutator(admin)
                .withFirstName(trimmedFirstName.equals("") ? admin.getFirstName() : trimmedFirstName)
                .withLastName(trimmedLastName.equals("") ? admin.getLastName() : trimmedLastName)
                .withEmail(trimmedEmail.equals("") ? admin.getEmail() : trimmedEmail)
                .mutate();
        em.persist(admin);
    }

    @Override
    public void updateInformationForClient(ClientImpl client, String firstName, String lastName, String email) {
        String trimmedFirstName = firstName.trim();
        String trimmedLastName = lastName.trim();
        String trimmedEmail = email.trim();

        client = ClientImpl.mutator(client)
                .withFirstName(trimmedFirstName.equals("") ? client.getFirstName() : trimmedFirstName)
                .withLastName(trimmedLastName.equals("") ? client.getLastName() : trimmedLastName)
                .withEmail(trimmedEmail.equals("") ? client.getEmail() : trimmedEmail)
                .mutate();
        em.persist(client);
    }

    @Override
    public void deleteUserByLogin(String login) throws EntityExistsException {
            if (findClient(login) != null) {
                em.remove(findClient(login));
            }
            else if(findAdmin((login)) != null) {
                em.remove(findAdmin(login));
            }
    }

    @Override
    public ClientImpl ensureClientForEmitMoneyOperation() {
            ClientImpl clientForEmitMoneyOperations = findClient(ClientImpl.ClientForEmitMoneyOperations);
            if (clientForEmitMoneyOperations == null) {
                clientForEmitMoneyOperations = createClient(ClientImpl.ClientForEmitMoneyOperations,
                        passwordEncoder.encode("4012659172"));
            }
            return clientForEmitMoneyOperations;
    }

    @Override
    public Admin ensureRootUser() {
            Admin root = findAdmin(Admin.RootAdminName);
            if (root == null) {
                root =  createAdmin(Admin.RootAdminName,
                        passwordEncoder.encode("4012659172"));
            }
            return root;
    }

    @Override
    public User checkPassword(User user, String password) {
        if (!user.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password");
        return user;
    }

    @Override
    public boolean checkBalanceForBet(ClientImpl client, BigDecimal amount) {
        return client.getBalance().compareTo(amount) >= 0;
    }

    @Override
    public List<ClientImpl> getAllClients() {
            return em.createQuery("from ClientImpl", ClientImpl.class).getResultList();
    }

    @Override
    public List<Admin> getAllAdmins() {
            return em.createQuery("from Admin" , Admin.class).getResultList();
    }

    @Override
    public ClientImpl upBalance(ClientImpl client, BigDecimal amount) {
        client = ClientImpl.mutator(client)
                .withBalance(amount)
                .mutate();
        em.persist(client);
        return client;
    }

}
