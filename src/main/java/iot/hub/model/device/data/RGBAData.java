package iot.hub.model.device.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

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
    public void changeData(AbstractData data) {
        this.red = ((RGBAData) data).getRed();
        this.green = ((RGBAData) data).getGreen();
        this.blue = ((RGBAData) data).getBlue();
        this.alfa = ((RGBAData) data).getAlfa();
    }
}
