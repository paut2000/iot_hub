package iot.hub.mqtt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import iot.hub.model.device.AbstractDevice;
import iot.hub.parser.AbstractDeviceJsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewDeviceInfo {

    private String roomName;

    @JsonDeserialize(using = AbstractDeviceJsonParser.class)
    private AbstractDevice device;

}
