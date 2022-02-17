package iot.hub.model.device.actuator.dataPojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Override
    public String toString() {
        return "{" + "\"alfa\":" + alfa + ", \"red\":" + red + ", \"green\":" + green + ", \"blue\":" + blue + "}";
    }

    @Override
    public void changeData(LinkedHashMap<String, Object> payload) {
        this.red = Integer.parseInt(payload.get("red").toString());
        this.green = Integer.parseInt(payload.get("green").toString());
        this.blue = Integer.parseInt(payload.get("blue").toString());
        this.alfa = Integer.parseInt(payload.get("alfa").toString());
    }
}
