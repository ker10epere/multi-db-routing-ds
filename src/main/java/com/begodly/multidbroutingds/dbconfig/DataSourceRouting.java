package com.begodly.multidbroutingds.dbconfig;

import com.begodly.multidbroutingds.dbconfig.MultiDatabaseConfig.DataSourceOneProperties;
import com.begodly.multidbroutingds.dbconfig.MultiDatabaseConfig.DataSourceTwoProperties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRouting extends AbstractRoutingDataSource {
    private final DataSourceOneProperties dataSourceOneProperties;
    private final DataSourceTwoProperties dataSourceTwoProperties;
    private final DataSourceContextHolder dataSourceContextHolder;

    public DataSourceRouting(
            DataSourceContextHolder dataSourceContextHolder,
            DataSourceOneProperties dataSourceOneProperties,
            DataSourceTwoProperties dataSourceTwoProperties
    ) {
        this.dataSourceOneProperties = dataSourceOneProperties;
        this.dataSourceTwoProperties = dataSourceTwoProperties;
        this.dataSourceContextHolder = dataSourceContextHolder;

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceEnum.DATASOURCE_ONE, dataSourceOneDataSource());
        dataSourceMap.put(DataSourceEnum.DATASOURCE_TWO, dataSourceTwoDataSource());
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(dataSourceOneDataSource());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getBranchContext();
    }

    public DataSource dataSourceOneDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceOneProperties.getUrl());
        dataSource.setUsername(dataSourceOneProperties.getUsername());
        dataSource.setPassword(dataSourceOneProperties.getPassword());
        return dataSource;
    }

    public DataSource dataSourceTwoDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceTwoProperties.getUrl());
        dataSource.setUsername(dataSourceTwoProperties.getUsername());
        dataSource.setPassword(dataSourceTwoProperties.getPassword());
        return dataSource;
    }
}