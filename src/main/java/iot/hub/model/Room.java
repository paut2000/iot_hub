package iot.hub.model;

import iot.hub.model.device.AbstractDevice;
import iot.hub.repository.RoomRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "rooms",
        uniqueConstraints = @UniqueConstraint(
                name = "uc_room",
                columnNames = {"name"}
                )
)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "fk_room"))
    private final Map<String, AbstractDevice> abstractDevices = new LinkedHashMap<>();

    public Room(String name) {
        this.name = name;
    }

    public void addDevice(AbstractDevice abstractDevice) {
        abstractDevices.put(abstractDevice.getDeviceId(), abstractDevice);
    }

    public void removeDevice(AbstractDevice abstractDevice) {
        abstractDevices.remove(abstractDevice.getDeviceId());
    }

    @Override
    public String toString() {

        StringBuilder ads = new StringBuilder();

        for (AbstractDevice abstractDevice : abstractDevices.values()) {
            ads.append(abstractDevice.toString()).append(",");
        }

        if (ads.length() != 0) ads.deleteCharAt(ads.length() - 1);

        return "\"" + name + "\": {" + ads + "}";
    }
}
