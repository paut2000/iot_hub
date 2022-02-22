package iot.hub.controller;

import iot.hub.model.House;
import iot.hub.model.device.AbstractDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/statistic")
public class StatisticController {

    @Autowired
    private House house;

    @GetMapping("/{roomName}/{deviceId}")
    public String getStatisticForDevice(
            @PathVariable String roomName,
            @PathVariable String deviceId
    ) {
        AbstractDevice device = house.getRooms().get(roomName).getAbstractDevices().get(deviceId);
        return device.getStatistic();
    }

}