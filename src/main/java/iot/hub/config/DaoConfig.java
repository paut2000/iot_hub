package iot.hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DaoConfig {

    @Bean
    public Connection connection(
            @Value("${database.driver}") String driver,
            @Value("${database.url}") String url,
            @Value("${database.user}") String user,
            @Value("${database.password}") String password
    ) {
        Connection connection = null;

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Соединение с базой данных установлено: " + url);

        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка загрузки драйвера базы данных: " + e.getMessage());
        } catch (SQLException throwables) {
            System.out.println("Ошибка подключения к базе данных: " + throwables.getMessage());
        }

        return connection;
    }

}
