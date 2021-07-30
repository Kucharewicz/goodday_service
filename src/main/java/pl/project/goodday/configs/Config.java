package pl.project.goodday.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }

}
