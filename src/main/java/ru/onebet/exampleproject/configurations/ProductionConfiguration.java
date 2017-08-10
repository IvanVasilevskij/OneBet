package ru.onebet.exampleproject.configurations;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "ru.onebet.exampleproject")
@Import({SecurityConfiguration.class})
@EnableWebMvc
@EnableTransactionManagement
public class ProductionConfiguration extends WebMvcConfigurerAdapter{

    @Autowired
    private UserDAOImpl daoUser;

    @Autowired
    private EntityManager em;

    @Bean
    public EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("postgres");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/*.css").addResourceLocations("/css/");
        registry.addResourceHandler("/img/*.jpg").addResourceLocations("/img/");
        registry.addResourceHandler("/fonts/*.*").addResourceLocations("/fonts/");
    }

    @Bean
    public EntityManager getEntityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    @Bean
    public ViewResolver getResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PlatformTransactionManager tx(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @PostConstruct
    public void ensureRootAndClientForMoneyOperation() {
        em.getTransaction().begin();
        daoUser.ensureRootUser();
        daoUser.ensureClientForEmitMoneyOperation();
        em.getTransaction().commit();
    }
}
