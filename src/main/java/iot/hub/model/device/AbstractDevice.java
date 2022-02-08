package iot.hub.model.device;

import iot.hub.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDevice {

    public AbstractDevice(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Getter
    @Setter
    protected MessagingService messagingService;

    @Getter
    @Setter
    protected String id;

    @Getter
    @Setter
    protected String topic;

    @Getter
    @Setter
    protected String type;

    @Override
    public String toString() {
        return "\"" + id + "\":" + "{" +
                "\"topic\":\"" + topic + "\"," +
                "\"type\":\"" + type + "\""
                + "}";
    }
}
