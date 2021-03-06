package iot.hub.dao.deviceData;

import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface IDeviceDataDao {

    ArrayList<? extends AbstractData> getByDevice(AbstractDevice device);
    AbstractData getLastByDevice(AbstractDevice device);
    ArrayList<? extends AbstractData> getByDeviceForPeriod(AbstractDevice device, Timestamp timestampStart, Timestamp timestampEnd);

    void save(AbstractDevice device);

}
