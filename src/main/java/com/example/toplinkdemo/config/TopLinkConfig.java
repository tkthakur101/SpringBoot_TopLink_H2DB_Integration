package com.example.toplinkdemo.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.toplinkdemo.repo")
@EntityScan("com.example.toplinkdemo.entity")
public class TopLinkConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example.toplinkdemo.entity");
        // emf.setPersistenceUnitName("toplinkPU"); // Removed to avoid persistence.xml interference

        // Set EclipseLink provider class (must be on classpath)
        try {
            Class<?> provider = Class.forName("org.eclipse.persistence.jpa.PersistenceProvider");
            emf.setPersistenceProviderClass((Class<? extends PersistenceProvider>) provider);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("EclipseLink provider not found on classpath. Ensure the EclipseLink dependency is present.", e);
        }

        Properties props = new Properties();
        props.put("eclipselink.logging.level", "FINE");
        props.put("eclipselink.ddl-generation", "none");
        props.put("eclipselink.target-database", "org.eclipse.persistence.platform.database.H2Platform");
        props.put("eclipselink.weaving", "false");
        emf.setJpaProperties(props);

        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
