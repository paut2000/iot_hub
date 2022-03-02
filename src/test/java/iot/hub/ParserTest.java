package iot.hub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iot.hub.model.device.actuator.Relay;
import iot.hub.model.device.data.RelayData;
import iot.hub.mqtt.message.NewDeviceMessage;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class ParserTest {

    @Test
    public void NewDeviceInfoJsonParserTest() throws JsonProcessingException {

        String payload = "{\n" +
                "\t\"roomName\" : \"bedroom\",\n" +
                "\t\"device\" : {\n" +
                "\t\t\"serialNumber\" : \"device-5\",\n" +
                "\t\t\"type\" : \"Relay\",\n" +
                "\t\t\"toDeviceTopic\" : \"/set/bedroom/device-5\",\n" +
                "\t\t\"fromDeviceTopic\" : \"/get/bedroom/device-5\",\n" +
                "\t\t\"data\" : {\n" +
                "\t\t\t\"status\" : true\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        NewDeviceMessage actualDeviceInfo = null;
        try {
            actualDeviceInfo = new ObjectMapper()
                    .readValue(payload, NewDeviceMessage.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        NewDeviceMessage expectedDeviceInfo = new NewDeviceMessage();
        expectedDeviceInfo.setRoomName("bedroom");
        Relay relay = new Relay();
        relay.setSerialNumber("device-5");
        relay.setType("Relay");
        relay.setToDeviceTopic("/set/bedroom/device-5");
        relay.setFromDeviceTopic("/get/bedroom/device-5");

        RelayData relayData = new RelayData();
        relayData.setStatus(true);

        relay.setData(relayData);

        expectedDeviceInfo.setDevice(relay);

        Assert.assertEquals(expectedDeviceInfo, actualDeviceInfo);
    }

}
