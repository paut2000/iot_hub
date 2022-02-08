package iot.hub.controller;

import iot.hub.model.House;
import iot.hub.model.device.actuator.IActuator;
import iot.hub.model.device.actuator.RGBAStrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("api")
public class ActuatorController {

    @Autowired
    private House house;

    @PostMapping("/control/actuator/{roomName}/{deviceId}")
    public void control(
            @PathVariable String roomName,
            @PathVariable String deviceId,
            @RequestParam(name = "action") String action
    ) {
        try {
            IActuator actuator = (IActuator) (house.getRooms().get(roomName).getAbstractDevices().get(deviceId));
            if (action.equals("enable")) actuator.enable();
            if (action.equals("disable")) actuator.disable();
            if (action.equals("switch")) actuator.switchStatus();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    @PostMapping("/control/rgba/{roomName}/{deviceId}")
    public void rgba(
            @PathVariable String roomName,
            @PathVariable String deviceId,
            @RequestParam(name = "red") Integer red,
            @RequestParam(name = "green") Integer green,
            @RequestParam(name = "blue") Integer blue,
            @RequestParam(name = "alfa") Integer alfa
    ) {
        try {
            RGBAStrip rgba = (RGBAStrip) (house.getRooms().get(roomName).getAbstractDevices().get(deviceId));
            rgba.setRGBA(new RGBAStrip.RGBA(red, green, blue, alfa));
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    @GetMapping("/status/{roomName}/{deviceId}")
    public LinkedHashMap<String, Object> getStatus(
            @PathVariable String roomName,
            @PathVariable String deviceId
    ) {
        return null;
    }

}
