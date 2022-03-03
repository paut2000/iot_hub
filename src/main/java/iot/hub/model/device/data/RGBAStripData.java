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
public class RGBAStripData extends AbstractData {

    private Integer red = 0;
    private Integer green = 0;
    private Integer blue = 0;
    private Integer alfa = 0;

    public RGBAStripData(Timestamp datetime, Integer red, Integer green, Integer blue, Integer alfa) {
        super(datetime);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alfa = alfa;
    }

    @Override
    public void changeData(AbstractData data) {
        this.red = ((RGBAStripData) data).getRed();
        this.green = ((RGBAStripData) data).getGreen();
        this.blue = ((RGBAStripData) data).getBlue();
        this.alfa = ((RGBAStripData) data).getAlfa();
    }
}
