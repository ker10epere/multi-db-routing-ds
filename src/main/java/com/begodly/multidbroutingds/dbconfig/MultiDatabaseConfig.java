package com.begodly.multidbroutingds.dbconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MultiDatabaseConfig {
    @Component
    @ConfigurationProperties(prefix = "datasource-one.datasource")
    @Getter
    @Setter
    public static class DataSourceOneProperties {
        private String url;
        private String password;
        private String username;
    }

    @Component
    @ConfigurationProperties(prefix = "datasource-two.datasource")
    @Getter
    @Setter
    public static class DataSourceTwoProperties {
        private String url;
        private String password;
        private String username;
    }
}
