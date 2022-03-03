package iot.hub.controller;

import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.device.data.AbstractData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("api")
public class DataController {

    @Autowired
    private House house;

    @GetMapping("/data/{roomName}/{deviceId}")
    public ResponseEntity<AbstractData> getData(
            @PathVariable String roomName,
            @PathVariable String deviceId
    ) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(house.getRoom(roomName).getDevice(deviceId).getData());
    }

    @GetMapping("/statistic/{roomName}/{deviceId}")
    public ResponseEntity<ArrayList<? extends AbstractData>> getStatisticForDevice(
            @PathVariable String roomName,
            @PathVariable String deviceId
    ) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(house.getRoom(roomName).getDevice(deviceId).getSample());
    }

}
