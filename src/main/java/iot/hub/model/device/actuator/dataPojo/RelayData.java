package iot.hub.model.device.actuator.dataPojo;

import iot.hub.model.device.AbstractDevice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.LinkedHashMap;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Relay")
public class RelayData extends AbstractData {

    private Boolean status = false;

    public RelayData(AbstractDevice abstractDevice) {
        super(abstractDevice);
    }

    @Override
    public void changeData(LinkedHashMap<String, Object> payload) {
        this.status = Boolean.parseBoolean(payload.get("isOn").toString());
    }

    @Override
    public String toString() {
        return "{" + "\"isOn\":" + status + "}";
    }
}
