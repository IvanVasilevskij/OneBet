package ru.onebet.exampleproject.configurations;

import org.springframework.context.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "ru.onebet.exampleproject",
excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ProductionConfiguration.class})})
public class TestConfiguration {

    @Bean
    public EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("postgresTest");
    }

    @Bean
    public EntityManager getEntityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }
}
