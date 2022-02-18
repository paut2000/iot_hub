package iot.hub.dao;

import iot.hub.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RoomDao extends AbstractDao {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public void createTable() {
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

    public void save(Room room) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO rooms (name) VALUES (?)"
            );
            statement.setString(1, room.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении в таблицу Rooms: " + e.getMessage());
        }
    }

    public void delete(String roomName) {
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM rooms WHERE name = ?"
            );
            statement.setString(1, roomName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении из таблицы Rooms: " + e.getMessage());
        }
    }

    public Map<String, Room> findAll() {
        Map<String, Room> map = new HashMap<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT name FROM rooms"
            );
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Room room = new Room(result.getString("name"), deviceDao
                );
                map.put(room.getName(), room);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении из таблицы Rooms: " + e.getMessage());
        }

        for (Room room : map.values()) {
            room.setAbstractDevices(deviceDao.findByRoom(room));
        }

        return map;
    }

}
