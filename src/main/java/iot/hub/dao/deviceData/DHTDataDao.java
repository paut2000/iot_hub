package iot.hub.dao.deviceData;

import iot.hub.dao.AbstractDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.DHTData;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

@DependsOn({"tableCreator"})
@Component
public class DHTDataDao extends AbstractDao implements IDeviceDataDao {

    @Override
    public ArrayList<DHTData> getByDevice(AbstractDevice device) {
        ArrayList<DHTData> resultArrayList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT datetime, temperature, humidity FROM dht\n" +
                            "INNER JOIN devices d on d.id = dht.device_id\n" +
                            "WHERE serial_number = ?"
            );
            statement.setString(1, device.getSerialNumber());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                DHTData dhtData = new DHTData(
                        result.getTimestamp("datetime"),
                        result.getDouble("humidity"),
                        result.getDouble("temperature")
                );
                resultArrayList.add(dhtData);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении из таблицы DHT: " + e.getMessage());
        }

        return resultArrayList;
    }

    @Override
    public ArrayList<DHTData> getByDeviceForPeriod(AbstractDevice device, Timestamp datetime) {
        return null;
    }

    @Override
    public void save(AbstractDevice device) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO dht (datetime, temperature, humidity, device_id)\n" +
                            "VALUES (?, ?, ?, (SELECT id FROM devices WHERE serial_number = ? AND device_type = 'DHT'))"
            );
            statement.setTimestamp(1, device.getData().getDatetime());
            statement.setDouble(2, ((DHTData) device.getData()).getTemperature());
            statement.setDouble(3, ((DHTData) device.getData()).getHumidity());
            statement.setString(4, device.getSerialNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении в таблицу DHT: " + e.getMessage());
        }
    }
}
