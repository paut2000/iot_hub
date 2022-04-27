package iot.hub.dao.deviceData;

import iot.hub.dao.AbstractDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;
import iot.hub.model.device.data.DHTData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

@DependsOn({"tableCreator"})
@Component
public class DHTDataDao extends AbstractDao implements IDeviceDataDao {

    private static final Logger logger = LoggerFactory.getLogger(DHTDataDao.class);

    @Override
    public AbstractData getLastByDevice(AbstractDevice device) {
        DHTData dhtData = new DHTData();

        try {
            statement = connection.prepareStatement(
                    "select datetime, humidity, temperature from dht where datetime = (\n" +
                            "    select max(datetime) from dht inner join devices d on d.id = dht.device_id\n" +
                            "    where serial_number = ?\n" +
                            ")"
            );
            statement.setString(1, device.getSerialNumber());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                dhtData.setDatetime(result.getTimestamp("datetime"));
                dhtData.setHumidity(result.getDouble("humidity"));
                dhtData.setTemperature(result.getDouble("temperature"));
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении из таблицы DHT: " + e.getMessage());
        }

        return dhtData;
    }

    @Override
    public ArrayList<DHTData> getByDevice(AbstractDevice device) {
        ArrayList<DHTData> resultArrayList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT datetime, temperature, humidity FROM dht\n" +
                            "INNER JOIN devices d on d.id = dht.device_id\n" +
                            "WHERE serial_number = ? ORDER BY datetime"
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
            logger.error("Ошибка при чтении из таблицы DHT: " + e.getMessage());
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
            logger.error("Ошибка при сохранении в таблицу DHT: " + e.getMessage());
        }
    }
}
