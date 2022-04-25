package iot.hub.dao.deviceData;

import iot.hub.dao.AbstractDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;
import iot.hub.model.device.data.RelayData;
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
public class RelayDataDao extends AbstractDao implements IDeviceDataDao {

    private static final Logger logger = LoggerFactory.getLogger(RelayDataDao.class);

    @Override
    public ArrayList<RelayData> getByDevice(AbstractDevice device) {
        ArrayList<RelayData> resultArrayList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT datetime, status FROM relay\n" +
                            "INNER JOIN devices ON relay.device_id = devices.id\n" +
                            "WHERE serial_number = ?"
            );
            statement.setString(1, device.getSerialNumber());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                RelayData relayData = new RelayData(
                        result.getTimestamp("datetime"),
                        result.getBoolean("status")
                );
                resultArrayList.add(relayData);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении из таблицы Relay: " + e.getMessage());
        }

        return resultArrayList;
    }

    @Override
    public AbstractData getLastByDevice(AbstractDevice device) {
        RelayData relayData = new RelayData();

        try {
            statement = connection.prepareStatement(
                    "select datetime, status from relay where datetime = (\n" +
                            "    select max(datetime) from relay inner join devices d on d.id = relay.device_id\n" +
                            "    where serial_number = ?\n" +
                            ");"
            );
            statement.setString(1, device.getSerialNumber());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                relayData.setDatetime(result.getTimestamp("datetime"));
                relayData.setStatus(result.getBoolean("status"));
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении из таблицы relay: " + e.getMessage());
        }

        return relayData;
    }

    @Override
    public ArrayList<RelayData> getByDeviceForPeriod(AbstractDevice device, Timestamp datetime) {

        return null;
    }

    @Override
    public void save(AbstractDevice device) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO relay (datetime, status, device_id)\n" +
                            "VALUES (?, ?, (SELECT id FROM devices WHERE serial_number = ? AND device_type = 'Relay'))"
            );
            statement.setTimestamp(1, device.getData().getDatetime());
            statement.setBoolean(2, ((RelayData) device.getData()).getStatus());
            statement.setString(3, device.getSerialNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при сохранении в таблицу Relay: " + e.getMessage());
        }
    }
}
