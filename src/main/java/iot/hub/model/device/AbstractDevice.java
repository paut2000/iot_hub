package iot.hub.model.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iot.hub.dao.deviceData.IDeviceDataDao;
import iot.hub.model.device.data.AbstractData;
import iot.hub.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDevice {

    protected MessagingService messagingService = null;

    protected IDeviceDataDao dataDao = null;

    @Getter
    @Setter
    protected String serialNumber;

    @Getter
    @Setter
    @JsonIgnore
    protected String toDeviceTopic;

    @Getter
    @Setter
    @JsonIgnore
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

    @JsonIgnore
    public ArrayList<? extends AbstractData> getSample() {
        return dataDao.getByDevice(this);
    }

}
