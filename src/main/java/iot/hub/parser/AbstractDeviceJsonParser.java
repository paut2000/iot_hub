package iot.hub.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;

import java.io.IOException;

public class AbstractDeviceJsonParser extends StdDeserializer<AbstractDevice> {

    public AbstractDeviceJsonParser() {
        this(null);
    }

    protected AbstractDeviceJsonParser(Class<?> vc) {
        super(vc);
    }

    @Override
    public AbstractDevice deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String type = node.get("type").asText();

        ObjectMapper mapper = ((ObjectMapper) jsonParser.getCodec()).registerModule(
                new SimpleModule().addDeserializer(AbstractData.class, new DeviceDataJsonParser(type))
        );

        Class<?> aClass = null;
        try {
            aClass = Class.forName("iot.hub.model.device.actuator." + type);
        } catch (ClassNotFoundException ignored) {}

        if (aClass == null) {
            try {
                aClass = Class.forName("iot.hub.model.device.sensor." + type);
            } catch (ClassNotFoundException e) {
                System.out.println("Нет такого девайса: " + type);
            }
        }

        return (AbstractDevice) mapper.treeToValue(node, aClass);
    }
}
