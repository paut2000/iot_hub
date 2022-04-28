package iot.hub.dao.deviceData;

import iot.hub.dao.AbstractDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;
import iot.hub.model.device.data.DHTData;
import iot.hub.model.device.data.RGBAStripData;
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
public class RGBADataDao extends AbstractDao implements IDeviceDataDao {

    private static final Logger logger = LoggerFactory.getLogger(RGBADataDao.class);

    @Override
    public AbstractData getLastByDevice(AbstractDevice device) {
        RGBAStripData rgbaStripData = new RGBAStripData();

        try {
            statement = connection.prepareStatement(
                    "select datetime, red, green, blue, alfa from rgba where datetime = (\n" +
                            "    select max(datetime) from rgba inner join devices d on d.id = rgba.device_id\n" +
                            "    where serial_number = ?\n" +
                            ");"
            );
            statement.setString(1, device.getSerialNumber());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                rgbaStripData.setDatetime(result.getTimestamp("datetime"));
                rgbaStripData.setRed(result.getInt("red"));
                rgbaStripData.setGreen(result.getInt("green"));
                rgbaStripData.setBlue(result.getInt("blue"));
                rgbaStripData.setAlfa(result.getInt("alfa"));
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении из таблицы rgba: " + e.getMessage());
        }

        return rgbaStripData;
    }

    @Override
    public ArrayList<RGBAStripData> getByDevice(AbstractDevice device) {
        ArrayList<RGBAStripData> resultArrayList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT datetime, red, green, blue, alfa FROM rgba\n" +
                            "INNER JOIN devices d on d.id = rgba.device_id\n" +
                            "WHERE serial_number = ? ORDER BY datetime"
            );
            statement.setString(1, device.getSerialNumber());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                RGBAStripData rgbaStripData = new RGBAStripData(
                        result.getTimestamp("datetime"),
                        result.getInt("red"),
                        result.getInt("green"),
                        result.getInt("blue"),
                        result.getInt("alfa")
                );
                resultArrayList.add(rgbaStripData);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении из таблицы RGBA: " + e.getMessage());
        }

        return resultArrayList;
    }

    @Override
    public ArrayList<RGBAStripData> getByDeviceForPeriod(AbstractDevice device, Timestamp timestampStart, Timestamp timestampEnd) {
        ArrayList<RGBAStripData> resultArrayList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(
                    "SELECT datetime, red, green, blue, alfa FROM rgba\n" +
                            "INNER JOIN devices d on d.id = rgba.device_id\n" +
                            "WHERE serial_number = ? AND datetime > ? AND datetime < ?\n" +
                            "ORDER BY datetime;"
            );
            statement.setString(1, device.getSerialNumber());
            statement.setTimestamp(2, timestampStart);
            statement.setTimestamp(3, timestampEnd);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                RGBAStripData rgbaStripData = new RGBAStripData(
                        result.getTimestamp("datetime"),
                        result.getInt("red"),
                        result.getInt("green"),
                        result.getInt("blue"),
                        result.getInt("alfa")
                );
                resultArrayList.add(rgbaStripData);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при чтении из таблицы RGBA: " + e.getMessage());
        }

        return resultArrayList;
    }

    @Override
    public void save(AbstractDevice device) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO rgba (datetime, red, green, blue, alfa, device_id)\n" +
                            "VALUES (?, ?, ?, ?, ?, (SELECT id FROM devices WHERE serial_number = ? AND device_type = 'RGBAStrip'))"
            );
            statement.setTimestamp(1, device.getData().getDatetime());
            statement.setInt(2, ((RGBAStripData) device.getData()).getRed());
            statement.setInt(3, ((RGBAStripData) device.getData()).getGreen());
            statement.setInt(4, ((RGBAStripData) device.getData()).getBlue());
            statement.setInt(5, ((RGBAStripData) device.getData()).getAlfa());
            statement.setString(6, device.getSerialNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при сохранении в таблицу RGBA: " + e.getMessage());
        }
    }
}
