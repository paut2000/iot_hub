package iot.hub.controller;

import iot.hub.exception.ResourceNotFoundException;
import iot.hub.controller.response.HttpResponse;
import iot.hub.model.House;
import iot.hub.model.device.actuator.IActuator;
import iot.hub.model.device.actuator.RGBAStrip;
import iot.hub.model.device.data.RGBAData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ActuatorController {

    @Autowired
    private House house;

    @PostMapping("/control/actuator/{roomName}/{deviceId}")
    public HttpResponse<?> control(
            @PathVariable String roomName,
            @PathVariable String deviceId,
            @RequestParam(name = "action") String action
    ) {
        IActuator actuator;
        try {
            actuator = (IActuator) (house.getRoom(roomName).getDevice(deviceId));
            if (action.equals("enable")) actuator.enable();
            else if (action.equals("disable")) actuator.disable();
            else return new HttpResponse<>("bad", "Неизвестное действие: " + action);
        } catch (Exception exception) {
            return new HttpResponse<>("bad", exception.getMessage());
        }
        return new HttpResponse<>("ok", actuator);
    }

    @PostMapping("/control/rgba/{roomName}/{deviceId}")
    public HttpResponse<?> rgba(
            @PathVariable String roomName,
            @PathVariable String deviceId,
            @RequestParam(name = "red") Integer red,
            @RequestParam(name = "green") Integer green,
            @RequestParam(name = "blue") Integer blue,
            @RequestParam(name = "alfa") Integer alfa
    ) {
        RGBAStrip rgba;
        try {
            rgba = (RGBAStrip) (house.getRoom(roomName).getDevice(deviceId));
            rgba.setRGBA(new RGBAData(red, green, blue, alfa));
        } catch (Exception exception) {
            return new HttpResponse<>("bad", exception.getMessage());
        }
        return new HttpResponse<>("ok", rgba);
    }

    @GetMapping("/status/{roomName}/{deviceId}")
    public HttpResponse<?> getData(
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
