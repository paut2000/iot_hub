package iot.hub.dao;

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

    @Override
    public void run(String... args) {
        createRoomTable();
        createDeviceTable();

        createRelayTable();
        createRGBATable();
    }

    private void createRoomTable() {
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS rooms (\n" +
                            "    id SERIAL,\n" +
                            "    name TEXT NOT NULL,\n" +
                            "    CONSTRAINT pk_room PRIMARY KEY (id),\n" +
                            "    CONSTRAINT u_name UNIQUE (name)\n" +
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы Rooms: " + e.getMessage());
        }
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
                            "    room_id INTEGER,\n" +
                            "    CONSTRAINT pk_device PRIMARY KEY (id),\n" +
                            "    CONSTRAINT u_device UNIQUE (serial_number),\n" +
                            "    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES rooms (id)\n" +
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы Devices: " + e.getMessage());
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
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы Relay: " + e.getMessage());
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
                            ")"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы RGBA: " + e.getMessage());
        }
    }

}
