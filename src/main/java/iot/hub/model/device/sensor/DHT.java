package iot.hub.model.device.sensor;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.DHTData;
import iot.hub.service.MessagingService;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@Getter
@Setter
public class DHT extends AbstractDevice implements ISensor {

    public DHT(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
        this.data = new DHTData();
    }

    @Override
    public String getInfo() {
        return ((DHTData) this.data).toString();
    }

    @Override
    public void changeInfo(LinkedHashMap<String, Object> info) {
        this.data.changeData(info);
        this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
        this.dataDao.save(this);
    }

    @Override
    public void changeUpdateFrequency(Integer milliSeconds) {

    }

}
