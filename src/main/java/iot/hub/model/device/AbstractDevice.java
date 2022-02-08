package iot.hub.model.device;

import iot.hub.model.device.actuator.Relay;
import iot.hub.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDevice {

    @Getter
    @Setter
    protected MessagingService messagingService;

    @Getter
    @Setter
    protected String id;

    @Getter
    @Setter
    protected String topic;

}
