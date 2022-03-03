package iot.hub.controller;

import iot.hub.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class SensorController {

    @Autowired
    private House house;

}
