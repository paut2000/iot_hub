package iot.hub.model.device.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelayData extends AbstractData {

    private Boolean status = false;

    public RelayData(Timestamp datetime, Boolean status) {
        super(datetime);
        this.status = status;
    }

    @Override
    public void changeData(AbstractData data) {
        this.status = ((RelayData) data).getStatus();
    }

}
