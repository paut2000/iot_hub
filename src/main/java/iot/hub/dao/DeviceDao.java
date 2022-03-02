package iot.hub.dao;

import iot.hub.model.Room;
import iot.hub.model.device.AbstractDevice;
import iot.hub.factory.DeviceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@DependsOn({"tableCreator"})
@Component
public class DeviceDao extends AbstractDao {

    @Autowired
    private DeviceFactory deviceFactory;

    public void save(Room room, AbstractDevice device) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO devices (serial_number, \n" +
                            "                     device_type, \n" +
                            "                     to_device_topic, \n" +
                            "                     from_device_topic, \n" +
                            "                     room_id)\n" +
                            "VALUES (?, ?, ?, ?, (SELECT id FROM rooms WHERE name = ?))"
            );
            statement.setString(1, device.getSerialNumber());
            statement.setString(2, device.getType());
            statement.setString(3, device.getToDeviceTopic());
            statement.setString(4, device.getFromDeviceTopic());
            statement.setString(5, room.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении в таблицу Devices: " + e.getMessage());
        }
    }

    public Map<String, AbstractDevice> findAll() {
        Map<String, AbstractDevice> map = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT (serial_number,\n" +
                            "        device_type,\n" +
                            "        to_device_topic,\n" +
                            "        from_device_topic,\n" +
                            "        room_id)\n" +
                            "    FROM devices"
            );
            ResultSet result = statement.executeQuery();

            map = abstractDeviceResultSetToMap(result);

        } catch (SQLException e) {
            System.out.println("Ошибка при чтении из таблицы Devices: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return map;
    }

    public Map<String, AbstractDevice> findByRoom(Room room) {
        Map<String, AbstractDevice> map = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT serial_number,\n" +
                            "        device_type,\n" +
                            "        to_device_topic,\n" +
                            "        from_device_topic,\n" +
                            "        room_id\n" +
                            "FROM devices WHERE room_id = (SELECT id FROM rooms WHERE rooms.name = ?)"
            );
            statement.setString(1, room.getName());

            ResultSet result = statement.executeQuery();

            map = abstractDeviceResultSetToMap(result);

        } catch (SQLException e) {
            System.out.println("Ошибка при чтении из таблицы Devices: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return map;
    }

    private Map<String, AbstractDevice> abstractDeviceResultSetToMap(ResultSet result) throws Exception {
        Map<String, AbstractDevice> map = new HashMap<>();
        while (result.next()) {
            AbstractDevice device = deviceFactory.createDevice(result.getString("device_type"));
            device.setSerialNumber(result.getString("serial_number"));
            device.setType(result.getString("device_type"));
            device.setToDeviceTopic(result.getString("to_device_topic"));
            device.setFromDeviceTopic(result.getString("from_device_topic"));
            map.put(device.getSerialNumber(), device);
        }
        return map;
    }

    public void delete(String serialNumber) {
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM devices WHERE serial_number = ?"
            );
            statement.setString(1, serialNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении из таблицы Devices: " + e.getMessage());
        }
    }

}
