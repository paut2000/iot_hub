package iot.hub.model.device;

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
    protected String toDeviceTopic;

    @Getter
    @Setter
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

    public ArrayList<? extends AbstractData> getSample() {
        return dataDao.getByDevice(this);
    }

    public String getStatistic() {
        ArrayList<AbstractData> sample = (ArrayList<AbstractData>) getSample();

        StringBuilder str = new StringBuilder();

        str.append("{statistic:[");

        for (AbstractData record : sample) {
            str.append(record.toString()).append(",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("]}");

        return str.toString();
    }

    @Override
    public String toString() {
        return "\"" + serialNumber + "\":" + "{" +
                "\"type\":\"" + type + "\""
                + "}";
    }
}
