package iot.hub.controller;

import iot.hub.exception.InvalidCommandException;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.device.actuator.IActuator;
import iot.hub.model.device.actuator.RGBAStrip;
import iot.hub.model.device.data.RGBAStripData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ActuatorController {

    @Autowired
    private House house;

    @PostMapping("/control/actuator/{roomName}/{deviceId}")
    public ResponseEntity<IActuator> control(
            @PathVariable String roomName,
            @PathVariable String deviceId,
            @RequestParam(name = "action") String action
    ) throws ResourceNotFoundException {
        IActuator actuator = (IActuator) (house.getRoom(roomName).getDevice(deviceId));
        if (action.equals("enable")) actuator.enable();
        else if (action.equals("disable")) actuator.disable();
        else throw new InvalidCommandException(action);
        return ResponseEntity.status(HttpStatus.OK).body(actuator);
    }

    @PostMapping("/control/rgba/{roomName}/{deviceId}")
    public ResponseEntity<RGBAStrip> rgba(
            @PathVariable String roomName,
            @PathVariable String deviceId,
            @RequestParam(name = "red") Integer red,
            @RequestParam(name = "green") Integer green,
            @RequestParam(name = "blue") Integer blue,
            @RequestParam(name = "alfa") Integer alfa
    ) throws ResourceNotFoundException, ClassCastException {
        RGBAStrip rgba;
        rgba = (RGBAStrip) (house.getRoom(roomName).getDevice(deviceId));
        rgba.changeRGBA(new RGBAStripData(red, green, blue, alfa));
        return ResponseEntity.status(HttpStatus.OK).body(rgba);
    }

}
