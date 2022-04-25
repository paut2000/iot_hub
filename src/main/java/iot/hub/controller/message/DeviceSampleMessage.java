package iot.hub.controller.message;

import iot.hub.model.device.data.AbstractData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeviceSampleMessage {

    private String type;
    private List<? extends AbstractData> dataList;

}
