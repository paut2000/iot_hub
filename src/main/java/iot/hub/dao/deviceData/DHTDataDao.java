package iot.hub.dao.deviceData;

import iot.hub.dao.AbstractDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;

@DependsOn({"tableCreator"})
@Component
public class DHTDataDao extends AbstractDao implements IDeviceDataDao {

    @Override
    public ArrayList<AbstractData> getByDevice(AbstractDevice device) {
        return null;
    }

    @Override
    public ArrayList<AbstractData> getByDeviceForPeriod(AbstractDevice device, Timestamp datetime) {
        return null;
    }

    @Override
    public void save(AbstractDevice device) {

    }
}
