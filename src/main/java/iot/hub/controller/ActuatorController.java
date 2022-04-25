package iot.hub.controller;

import iot.hub.exception.DiedDeviceException;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.device.actuator.AbstractActuator;
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

    @PostMapping("/control/actuator/disable")
    public House disableAll() {

        return house;
    }

    @PostMapping("/control/actuator/disable/{deviceId}")
    public ResponseEntity<AbstractActuator> disable(
            @PathVariable String deviceId
    ) throws ResourceNotFoundException, DiedDeviceException {
        AbstractActuator actuator = (AbstractActuator) (house.getDevice(deviceId));
        actuator.disable();
        return ResponseEntity.status(HttpStatus.OK).body(actuator);
    }

    @PostMapping("/control/actuator/enable/{deviceId}")
    public ResponseEntity<AbstractActuator> enable(
            @PathVariable String deviceId
    ) throws ResourceNotFoundException, DiedDeviceException {
        AbstractActuator actuator = (AbstractActuator) (house.getDevice(deviceId));
        actuator.enable();
        return ResponseEntity.status(HttpStatus.OK).body(actuator);
    }

    @PostMapping("/control/rgba/{deviceId}")
    public ResponseEntity<RGBAStrip> rgba(
            @PathVariable String deviceId,
            @RequestBody RGBAStripData data
    ) throws ResourceNotFoundException, ClassCastException, DiedDeviceException {
        RGBAStrip rgba;
        rgba = (RGBAStrip) (house.getDevice(deviceId));
        rgba.changeRGBA(data);
        return ResponseEntity.status(HttpStatus.OK).body(rgba);
    }

    @PostMapping("/control/rgba/nosave/{deviceId}")
    public ResponseEntity<RGBAStrip> rgbaNoSave(
            @PathVariable String deviceId,
            @RequestBody RGBAStripData data
    ) throws ResourceNotFoundException, ClassCastException, DiedDeviceException {
        RGBAStrip rgba;
        rgba = (RGBAStrip) (house.getDevice(deviceId));
        rgba.changeRGBAWithoutSave(data);
        return ResponseEntity.status(HttpStatus.OK).body(rgba);
    }

}
