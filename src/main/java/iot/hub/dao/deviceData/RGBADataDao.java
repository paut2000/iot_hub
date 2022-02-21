package iot.hub.dao.deviceData;

import iot.hub.dao.AbstractDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.RGBAData;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

@DependsOn({"tableCreator"})
@Component
public class RGBADataDao extends AbstractDao implements IDeviceDataDao {

    @Override
    public ArrayList<RGBAData> getByDevice(AbstractDevice device) {
        ArrayList<RGBAData> resultArrayList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT datetime, red, green, blue, alfa FROM rgba\n" +
                            "INNER JOIN devices d on d.id = rgba.device_id\n" +
                            "WHERE serial_number = ?"
            );
            statement.setString(1, device.getSerialNumber());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                RGBAData rgbaData = new RGBAData(
                        result.getTimestamp("datetime"),
                        result.getInt("red"),
                        result.getInt("green"),
                        result.getInt("blue"),
                        result.getInt("alfa")
                );
                resultArrayList.add(rgbaData);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении из таблицы RGBA: " + e.getMessage());
        }

        return resultArrayList;
    }

    @Override
    public ArrayList<RGBAData> getByDeviceForPeriod(AbstractDevice device, Timestamp datetime) {
        return null;
    }

    @Override
    public void save(AbstractDevice device) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO rgba (datetime, red, green, blue, alfa, device_id)\n" +
                            "VALUES (?, ?, ?, ?, ?, (SELECT id FROM devices WHERE serial_number = ? AND device_type = 'RGBAStrip'))"
            );
            statement.setTimestamp(1, device.getData().getDatetime());
            statement.setInt(2, ((RGBAData) device.getData()).getRed());
            statement.setInt(3, ((RGBAData) device.getData()).getGreen());
            statement.setInt(4, ((RGBAData) device.getData()).getBlue());
            statement.setInt(5, ((RGBAData) device.getData()).getAlfa());
            statement.setString(6, device.getSerialNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении в таблицу RGBA: " + e.getMessage());
        }
    }
}
