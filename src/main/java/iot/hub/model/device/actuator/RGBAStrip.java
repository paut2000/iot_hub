package iot.hub.model.device.actuator;

import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.exception.DiedDeviceException;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.RGBAStripData;
import iot.hub.mqtt.MessagingService;
import lombok.NoArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

@NoArgsConstructor
public class RGBAStrip extends AbstractActuator {

    private static final Logger logger = LoggerFactory.getLogger(RGBAStrip.class);

    public RGBAStrip(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
        this.data = new RGBAStripData();
    }

    @Override
    public void enable() throws DiedDeviceException {
        if (alive == false) {
            throw new DiedDeviceException(serialNumber);
        }
        try {
            this.messagingService.publish(this.toDeviceTopic, new RGBAStripData(255,255,255, 255).toString(), 2, false);
            ((RGBAStripData) this.data).setRed(255);
            ((RGBAStripData) this.data).setGreen(255);
            ((RGBAStripData) this.data).setBlue(255);
            ((RGBAStripData) this.data).setAlfa(255);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {
            logger.error("Метод enable(): " + e.getMessage());
        }
    }

    @Override
    public void disable() throws DiedDeviceException {
        if (alive == false) {
            throw new DiedDeviceException(serialNumber);
        }
        try {
            this.messagingService.publish(this.toDeviceTopic, new RGBAStripData(0,0,0, 0).toString(), 2, false);
            ((RGBAStripData) this.data).setRed(0);
            ((RGBAStripData) this.data).setGreen(0);
            ((RGBAStripData) this.data).setBlue(0);
            ((RGBAStripData) this.data).setAlfa(0);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {
            logger.error("Метод enable(): " + e.getMessage());
        }
    }

    public void changeRGBA(RGBAStripData rgbaStripData) throws DiedDeviceException {
        if (alive == false) {
            throw new DiedDeviceException(serialNumber);
        }
        try {
            this.messagingService.publish(this.toDeviceTopic, rgbaStripData.toString(), 2, false);
            ((RGBAStripData) this.data).setRed(rgbaStripData.getRed());
            ((RGBAStripData) this.data).setGreen(rgbaStripData.getGreen());
            ((RGBAStripData) this.data).setBlue(rgbaStripData.getBlue());
            ((RGBAStripData) this.data).setAlfa(rgbaStripData.getAlfa());
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException e) {
            logger.error("Метод changeRGBA(): " + e.getMessage());
        }
    }

}
