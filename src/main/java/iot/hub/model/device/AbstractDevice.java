package iot.hub.model.device;

import iot.hub.model.device.actuator.dataPojo.AbstractData;
import iot.hub.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractDevice {

    protected MessagingService messagingService;

    protected String deviceId;

    protected String toDeviceTopic;

    protected String fromDeviceTopic;

    protected String type;

    protected AbstractData data;

    public AbstractDevice(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Override
    public String toString() {
        return "\"" + deviceId + "\":" + "{" +
                "\"type\":\"" + type + "\""
                + "}";
    }
}
