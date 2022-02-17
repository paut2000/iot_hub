package iot.hub.model.device.actuator.dataPojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@NoArgsConstructor
@Getter
@Setter
public class RelayData extends AbstractData {

    private Boolean status = false;

    @Override
    public void changeData(LinkedHashMap<String, Object> payload) {
        this.status = Boolean.parseBoolean(payload.get("isOn").toString());
    }

    @Override
    public String toString() {
        return "{" + "\"isOn\":" + status + "}";
    }
}
