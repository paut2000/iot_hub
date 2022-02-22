package iot.hub.dao.deviceData;

import iot.hub.dao.AbstractDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;
import iot.hub.model.device.data.RelayData;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

@DependsOn({"tableCreator"})
@Component
public class RelayDataDao extends AbstractDao implements IDeviceDataDao {

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
            System.out.println("Ошибка при чтении из таблицы Relay: " + e.getMessage());
        }

        return resultArrayList;
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
            System.out.println("Ошибка при сохранении в таблицу Relay: " + e.getMessage());
        }
    }
}