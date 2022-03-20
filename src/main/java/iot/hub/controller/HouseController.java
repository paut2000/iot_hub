package iot.hub.controller;

import iot.hub.dao.DeviceDao;
import iot.hub.model.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class HouseController {

    @Autowired
    private House house;

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);

    @GetMapping("/house")
    public House getHouse() {
        return house;
    }

}
