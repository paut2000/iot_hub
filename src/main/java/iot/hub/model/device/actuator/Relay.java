package iot.hub.model.device.actuator;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.RelayData;
import iot.hub.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class Relay extends AbstractDevice implements IActuator {

    public Relay(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
        this.data = new RelayData();
    }

    @Override
    public void changeStatus(LinkedHashMap<String, Object> payload) {
        this.data.changeData(payload);
        this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
        this.dataDao.save(this);
    }

    @Override
    public void enable() {
        try {
            this.messagingService.publish(this.toDeviceTopic, "{\"action\":\"enable\"}", 2, false);
            ((RelayData) this.data).setStatus(true);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {

        }
    }

    @Override
    public void disable() {
        try {
            this.messagingService.publish(this.toDeviceTopic, "{\"action\":\"disable\"}", 2, false);
            ((RelayData) this.data).setStatus(false);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {

        }
    }

}
