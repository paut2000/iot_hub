package iot.hub.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class TableCreator implements CommandLineRunner {

    @Autowired
    private Connection connection;

    private PreparedStatement statement = null;

    private static final Logger logger = LoggerFactory.getLogger(TableCreator.class);

    @Override
    public void run(String... args) {
        // Основные таблицы
        createDeviceTable();

        // Таблицы актуаторов
        createRelayTable();
        createRGBATable();

        // Таблицы сенсоров
        createDHTTable();
    }

    private void createDeviceTable() {
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS devices (\n" +
                            "    id SERIAL,\n" +
                            "    serial_number TEXT,\n" +
                            "    device_type TEXT,\n" +
                            "    to_device_topic TEXT,\n" +
                            "    from_device_topic TEXT,\n" +
                            "    CONSTRAINT pk_device PRIMARY KEY (id),\n" +
                            "    CONSTRAINT u_device UNIQUE (serial_number)\n" +
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при создании таблицы Devices: " + e.getMessage());
        }
    }

    private void createRelayTable() {
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS relay (\n" +
                            "    id SERIAL,\n" +
                            "    datetime TIMESTAMP,\n" +
                            "    status BOOLEAN,\n" +
                            "    device_id INTEGER,\n" +
                            "    CONSTRAINT pk_relay PRIMARY KEY (id),\n" +
                            "    CONSTRAINT fk_device FOREIGN KEY (device_id) REFERENCES devices (id)\n" +
                            "    ON DELETE CASCADE\n" +
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при создании таблицы Relay: " + e.getMessage());
        }
    }

    private void createRGBATable() {
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS rgba (\n" +
                            "    id SERIAL,\n" +
                            "    datetime TIMESTAMP,\n" +
                            "    red INTEGER,\n" +
                            "    green INTEGER,\n" +
                            "    blue INTEGER,\n" +
                            "    alfa INTEGER,\n" +
                            "    device_id INTEGER,\n" +
                            "    CONSTRAINT pk_rgba PRIMARY KEY (id),\n" +
                            "    CONSTRAINT fk_device FOREIGN KEY (device_id) REFERENCES devices (id)\n" +
                            "    ON DELETE CASCADE\n" +
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при создании таблицы RGBA: " + e.getMessage());
        }
    }

    private void createDHTTable() {
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS dht (\n" +
                            "    id SERIAL,\n" +
                            "    datetime TIMESTAMP,\n" +
                            "    temperature DOUBLE PRECISION,\n" +
                            "    humidity DOUBLE PRECISION,\n" +
                            "    device_id INTEGER,\n" +
                            "    CONSTRAINT pk_dht PRIMARY KEY (id),\n" +
                            "    CONSTRAINT fk_device FOREIGN KEY (device_id) REFERENCES devices (id)\n" +
                            "    ON DELETE CASCADE\n" +
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при создании таблицы DHT: " + e.getMessage());
        }
    }

}
