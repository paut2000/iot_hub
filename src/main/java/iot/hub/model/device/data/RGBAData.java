package iot.hub.model.device.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RGBAData extends AbstractData {

    private Integer red = 0;
    private Integer green = 0;
    private Integer blue = 0;
    private Integer alfa = 0;

    public RGBAData(Timestamp datetime, Integer red, Integer green, Integer blue, Integer alfa) {
        super(datetime);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alfa = alfa;
    }

    @Override
    public void changeData(LinkedHashMap<String, Object> payload) {
        this.red = Integer.parseInt(payload.get("red").toString());
        this.green = Integer.parseInt(payload.get("green").toString());
        this.blue = Integer.parseInt(payload.get("blue").toString());
        this.alfa = Integer.parseInt(payload.get("alfa").toString());
    }
}
