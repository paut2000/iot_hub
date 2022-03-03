package iot.hub.dao;

import iot.hub.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@DependsOn({"tableCreator"})
@Component
public class RoomDao extends AbstractDao {

    @Autowired
    private DeviceDao deviceDao;

    private static final Logger logger = LoggerFactory.getLogger(RoomDao.class);

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
            logger.error("Ошибка при удалении из таблицы Rooms: " + e.getMessage());
        }
    }

    public void update(String oldRoomName, String newRoomName) {
        try {
            statement = connection.prepareStatement(
                    "UPDATE rooms SET name = ? where name = ?"
            );
            statement.setString(1, newRoomName);
            statement.setString(2, oldRoomName);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при обновлении данных из таблицы Rooms: " + e.getMessage());
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
                Room room = new Room(result.getString("name"), deviceDao);
                map.put(room.getName(), room);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении из таблицы Rooms: " + e.getMessage());
        }

        for (Room room : map.values()) {
            room.setAbstractDevices(deviceDao.findByRoom(room));
        }

        return map;
    }

}
