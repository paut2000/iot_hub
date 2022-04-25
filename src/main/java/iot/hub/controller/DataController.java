package iot.hub.controller;

import iot.hub.controller.message.DeviceSampleMessage;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.device.AbstractDevice;
import iot.hub.model.device.data.AbstractData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class DataController {

    @Autowired
    private House house;

    @GetMapping("/data/{deviceId}")
    public ResponseEntity<AbstractData> getData(
            @PathVariable String deviceId
    ) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(house.getDevice(deviceId).getData());
    }

    @GetMapping("/statistic/{deviceId}")
    public ResponseEntity<DeviceSampleMessage> getStatisticForDevice(
            @PathVariable String deviceId
    ) throws ResourceNotFoundException {
        AbstractDevice device = house.getDevice(deviceId);
        DeviceSampleMessage sampleMessage = new DeviceSampleMessage();
        sampleMessage.setType(device.getType());
        sampleMessage.setDataList(device.getSample());
        return ResponseEntity.status(HttpStatus.OK).body(sampleMessage);
    }

}
