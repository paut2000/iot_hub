package iot.hub.model.device.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractData {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp datetime;

    public abstract void changeData(AbstractData data);

}
