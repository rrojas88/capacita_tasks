
package com.simple_apiX.tasks.api.v1.local.api_tasks.adapters;


import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "postgresEntityManagerFactory", 
    transactionManagerRef = "postgresTransactionManager"
    , basePackages =  "com.simple_apiX.tasks.api.v1.local.api_tasks"
    , includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.simple_apiX.tasks.api.v1.local.api_tasks.*.adapters.bd1.*")
    }
    , excludeFilters = {
        //@ComponentScan.Filter(type = FilterType.REGEX, pattern = {"com.simple_apiX.tasks.api.v1.local.api_tasks.*.adapters.bd2.*"})
    }
)
public class Bd1PostgresConfig {

        public String packages_models = "com.simple_apiX.tasks.api.v1.local.api_tasks.*.adapters.bd1";
    
        @Autowired
        public Environment env;
	
        @Bean(name = "postgresDataSource")
        public DataSource postgresDataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(env.getProperty("bd1postgres.datasource.url"));
            dataSource.setUsername(env.getProperty("bd1postgres.datasource.username"));
            dataSource.setPassword(env.getProperty("bd1postgres.datasource.password"));
            dataSource.setDriverClassName(env.getProperty("bd1postgres.datasource.driver-class-name"));

            return dataSource;
        }
        
        
        @Primary
        @Bean(name = "postgresEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(postgresDataSource());
            em.setPackagesToScan( this.packages_models );
            em.setPersistenceUnitName("Bd1PostgresConfig");

            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);

            Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.hbm2ddl.auto", env.getProperty("bd1postgres.jpa.hibernate.ddl-auto"));
            properties.put("hibernate.show-sql", env.getProperty("bd1postgres.jpa.show-sql"));
            properties.put("hibernate.dialect", env.getProperty("bd1postgres.jpa.properties.hibernate.dialect"));

            em.setJpaPropertyMap(properties);
            return em;
        }
    
    
        @Primary
        @Bean(name = "postgresTransactionManager")
        public PlatformTransactionManager transactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
            return transactionManager;
        }

        public void setEnv(Environment env) {
            this.env = env;
        }
        
}
