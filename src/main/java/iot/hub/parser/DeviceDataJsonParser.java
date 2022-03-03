package iot.hub.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import iot.hub.model.device.data.AbstractData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DeviceDataJsonParser extends StdDeserializer<AbstractData> {

    private String type;

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataJsonParser.class);

    public DeviceDataJsonParser(String type) {
        super((Class<?>) null);
        this.type = type;
    }

    @Override
    public AbstractData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Class<?> aClass = null;
        try {
            aClass = Class.forName("iot.hub.model.device.data." + type + "Data");
        } catch (ClassNotFoundException e) {
            logger.error("Нет такого типа данных девайса: " + type + "Data");
        }

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();

        return (AbstractData) mapper.treeToValue(node, aClass);
    }
}
