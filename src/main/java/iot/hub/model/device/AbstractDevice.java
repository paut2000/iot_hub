package iot.hub.model.device;

import iot.hub.model.device.actuator.dataPojo.AbstractData;
import iot.hub.service.MessagingService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "devices")
public abstract class AbstractDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Transient
    protected MessagingService messagingService;

    @Column(name = "serial_number")
    protected String deviceId;

    @Column(name = "to_device_topic")
    protected String toDeviceTopic;

    @Column(name = "from_device_topic")
    protected String fromDeviceTopic;

    protected String type;

    @Transient
    protected AbstractData data;

    public AbstractDevice(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Override
    public String toString() {
        return "\"" + deviceId + "\":" + "{" +
                "\"type\":\"" + type + "\""
                + "}";
    }
}
