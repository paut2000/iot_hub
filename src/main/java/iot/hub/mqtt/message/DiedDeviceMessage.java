package iot.hub.mqtt.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiedDeviceMessage {

    private String roomName;
    private String serialNumber;

}
