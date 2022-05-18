package com.turing.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Turing
 * @date 2022/5/9 1:00
 **/
public class C3P0Utils {
    private static final ComboPooledDataSource dataSource = new ComboPooledDataSource("house-rent");
    private static final QueryRunner runner = new QueryRunner(dataSource);

    public static QueryRunner getQueryRunner() {
        return runner;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
