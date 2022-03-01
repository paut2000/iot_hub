package iot.hub.controller;

import iot.hub.dao.DeviceDao;
import iot.hub.exception.ResourceAlreadyExistException;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.controller.response.HttpResponse;
import iot.hub.model.House;
import iot.hub.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ConfigInfoController {

    @Autowired
    private House house;

    @Autowired
    private DeviceDao deviceDao;

    @GetMapping("/house")
    public House getHouse() {
        return house;
    }

    @PostMapping("/house/room")
    public HttpResponse<?> addRoom(
            @RequestParam(value = "roomName") String roomName
    ) {
        try {
            house.addRoom(new Room(roomName, deviceDao));
        } catch (ResourceAlreadyExistException e) {
            return new HttpResponse<>("bad", e.getMessage());
        }
        return new HttpResponse<>("ok", "Комната добавлена: " + roomName);
    }

    @DeleteMapping("/house/room")
    public HttpResponse<?> deleteRoom(
            @RequestParam(value = "roomName") String roomName
    ) {
        try {
            house.removeRoom(roomName);
        } catch (ResourceNotFoundException e) {
            return new HttpResponse<>("bad", e.getMessage());
        }
        return new HttpResponse<>("ok", "Комната удалена: " + roomName);
    }

}
