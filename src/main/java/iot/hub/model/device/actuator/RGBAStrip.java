package iot.hub.model.device.actuator;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.RGBAData;
import iot.hub.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class RGBAStrip extends AbstractDevice implements IActuator {

    public RGBAStrip(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
        this.data = new RGBAData();
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
            this.messagingService.publish(this.toDeviceTopic, new RGBAData(255,255,255, 255).toString(), 2, false);
            ((RGBAData) this.data).setRed(255);
            ((RGBAData) this.data).setGreen(255);
            ((RGBAData) this.data).setBlue(255);
            ((RGBAData) this.data).setAlfa(255);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
        try {
            this.messagingService.publish(this.toDeviceTopic, new RGBAData(0,0,0, 0).toString(), 2, false);
            ((RGBAData) this.data).setRed(0);
            ((RGBAData) this.data).setGreen(0);
            ((RGBAData) this.data).setBlue(0);
            ((RGBAData) this.data).setAlfa(0);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setRGBA(RGBAData rgbaData) {
        try {
            this.messagingService.publish(this.toDeviceTopic, rgbaData.toString(), 2, false);
            ((RGBAData) this.data).setRed(rgbaData.getRed());
            ((RGBAData) this.data).setGreen(rgbaData.getGreen());
            ((RGBAData) this.data).setBlue(rgbaData.getBlue());
            ((RGBAData) this.data).setAlfa(rgbaData.getAlfa());
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
