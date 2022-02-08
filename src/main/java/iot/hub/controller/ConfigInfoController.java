package iot.hub.controller;

import iot.hub.model.House;
import iot.hub.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ConfigInfoController {

    @Autowired
    private House house;

    @GetMapping("/config")
    public String getConfig() {
        return house.toString();
    }

    @PostMapping("/config/room")
    public void addRoom(
            @RequestParam(value = "roomName") String roomName
    ) {
        house.addRoom(new Room(roomName));
    }

    @DeleteMapping("/config/room")
    public void deleteRoom(
            @RequestParam(value = "roomName") String roomName
    ) {
        house.removeRoom(roomName);
    }

}
