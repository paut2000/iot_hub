package iot.hub.controller;

import iot.hub.exception.ResourceNotFoundException;
import iot.hub.controller.response.HttpResponse;
import iot.hub.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class SensorController {

    @Autowired
    private House house;

    @GetMapping("/sensor/{roomName}/{deviceId}")
    public HttpResponse<?> getSensorInfo(
            @PathVariable String roomName,
            @PathVariable String deviceId
    ) {
        try {
            return new HttpResponse<>("ok", house.getRoom(roomName).getDevice(deviceId).getData());
        } catch (ResourceNotFoundException e) {
            return new HttpResponse<>("bad", e.getMessage());
        }
    }

}
