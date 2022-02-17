package iot.hub.model.device.actuator.dataPojo;

import iot.hub.model.device.AbstractDevice;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Calendar;
import java.util.LinkedHashMap;

@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar datetime;

    @ManyToOne
    @JoinColumn(name = "device_id", foreignKey = @ForeignKey(name = "fk_device"))
    private AbstractDevice abstractDevice;

    public AbstractData(AbstractDevice abstractDevice) {
        this.abstractDevice = abstractDevice;
    }

    public abstract void changeData(LinkedHashMap<String, Object> payload);

}
