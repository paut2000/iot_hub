package iot.hub.model.device.actuator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.exception.DiedDeviceException;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.RelayData;
import iot.hub.mqtt.MessagingService;
import lombok.NoArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

@NoArgsConstructor
public class Relay extends AbstractActuator {

    private static final Logger logger = LoggerFactory.getLogger(Relay.class);

    public Relay(MessagingService messagingService, IDeviceDataDao dataDao) {
        super(messagingService, dataDao);
        this.data = new RelayData();
    }

    @Override
    public void enable() throws DiedDeviceException {
        if (alive == false) {
            throw new DiedDeviceException(serialNumber);
        }
        try {
            ((RelayData) this.data).setStatus(true);
            this.messagingService.publish(this.toDeviceTopic, new ObjectMapper().writeValueAsString(data), 2, false);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException | JsonProcessingException e) {
            logger.error("Метод enable(): " + e.getMessage());
        }
    }

    @Override
    public void disable() throws DiedDeviceException {
        if (alive == false) {
            throw new DiedDeviceException(serialNumber);
        }
        try {
            ((RelayData) this.data).setStatus(false);
            this.messagingService.publish(this.toDeviceTopic, new ObjectMapper().writeValueAsString(data), 2, false);
            this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
            this.dataDao.save(this);
        } catch (MqttException | JsonProcessingException e) {
            logger.error("Метод disable(): " + e.getMessage());
        }
    }

}
