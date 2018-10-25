package com.hhg.jerry.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by lining on 2018/10/25.
 */
public class CityService {
    private static Logger logger = LoggerFactory.getLogger(CityService.class);
    private static final String PROPERTIES_FILE_NAME = "db.properties";
    private static String url;
    private static String driver;
    private static String username;
    private static String password;
    public static void main(String[] args) throws Exception{
        init();
        simpleUserJBDC();
        transactionLevelTest();
    }

    static void simpleUserJBDC() throws Exception{
        Connection connection = getConnection(Connection.TRANSACTION_REPEATABLE_READ);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from city where id < ?");
        preparedStatement.setLong(1, 2);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<City> cities = new ArrayList<>();
        while (resultSet.next()){
            City city = new City();
            city.setId(resultSet.getLong("id"));
            city.setName(resultSet.getString("name"));
            city.setPopulation(resultSet.getLong("population"));
            cities.add(city);
        }
        logger.info("cities : {}", cities);
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    static void transactionLevelTest() throws Exception{
        Connection connection1 = getConnection(Connection.TRANSACTION_REPEATABLE_READ);
        logger.info("origin data:{}", getCityById(connection1, 1L));
        logger.info("origin data:{}", getCityById(connection1, 2L));
        int affectRowCount = addOnePopulation(connection1, 1L);
        if(affectRowCount > 0){
            Connection connection2 = getConnection(Connection.TRANSACTION_READ_UNCOMMITTED);//read uncommitted
            Connection connection3 = getConnection(Connection.TRANSACTION_READ_COMMITTED);//read committed
            City city1ByConnection2 = getCityById(connection2, 1L);
            City city1ByConnection3 = getCityById(connection3, 1L);
            addOnePopulation(connection3, 2L);
            connection3.commit();
            logger.info("after connection3 modify data:{}", getCityById(connection1, 2L));
            logger.info("another transaction with read uncommitted:{}", city1ByConnection2.toString());
            logger.info("another transaction with read committed:{}", city1ByConnection3.toString());
            connection1.rollback();
            logger.info("after connection1 rollback:{}", getCityById(connection1, 2L));
            logger.info("after rollback:{}", getCityById(connection1, 1L).toString());
            connection1.close();
            connection2.close();
            connection3.close();
        }
    }

    static int addOnePopulation(Connection connection, Long id) throws Exception{
        PreparedStatement preparedStatement = connection.prepareStatement("update city set population = population+1 where id = ?");
        preparedStatement.setLong(1, id);
        int affectRowCount = preparedStatement.executeUpdate();
        preparedStatement.close();
        return affectRowCount;
    }

    static City getCityById(Connection connection, Long id) throws Exception{
        PreparedStatement preparedStatement = connection.prepareStatement("select * from city where id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        City city = new City();
        if(resultSet.next()){
            city.setId(resultSet.getLong("id"));
            city.setName(resultSet.getString("name"));
            city.setDistrict(resultSet.getString("district"));
            city.setCountryCode(resultSet.getString("countryCode"));
            city.setPopulation(resultSet.getLong("population"));
        }
        resultSet.close();
        preparedStatement.close();
        return city;
    }

    static void init() throws Exception{
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getSystemResourceAsStream(PROPERTIES_FILE_NAME);
        properties.load(in);
        in.close();
        url = properties.get("url").toString();
        driver = properties.get("driver").toString();
        username = properties.get("username").toString();
        password = properties.get("password").toString();
        Class.forName(driver);
    }

    static Connection getConnection(int level) throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);
        connection.setTransactionIsolation(level);
        connection.setAutoCommit(false);
        return connection;
    }
}
