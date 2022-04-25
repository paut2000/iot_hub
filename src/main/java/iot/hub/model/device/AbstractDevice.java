package iot.hub.model.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.model.device.data.AbstractData;
import iot.hub.mqtt.MessagingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDevice {

    @Setter
    protected MessagingService messagingService = null;

    @Setter
    protected IDeviceDataDao dataDao = null;

    @Getter
    @Setter
    protected String serialNumber;

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected boolean alive = false;

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String toDeviceTopic;

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String fromDeviceTopic;

    @Getter
    @Setter
    protected String type;

    @Getter
    @Setter
    protected AbstractData data;

    public AbstractDevice(MessagingService messagingService, IDeviceDataDao dataDao) {
        this.messagingService = messagingService;
        this.dataDao = dataDao;
    }

    public void fillLastData() {
        this.data = this.dataDao.getLastByDevice(this);
    }

    @JsonIgnore
    public ArrayList<? extends AbstractData> getSample() {
        return dataDao.getByDevice(this);
    }

    // Вызывается со стороны МК
    public void changeData(AbstractData data) {
        this.data.changeData(data);
        this.data.setDatetime(new Timestamp(System.currentTimeMillis()));
        this.dataDao.save(this);
    }

}
