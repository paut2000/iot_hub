package iot.hub.controller;

import iot.hub.dao.DeviceDao;
import iot.hub.exception.ResourceAlreadyExistException;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.House;
import iot.hub.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class HouseController {

    @Autowired
    private House house;

    @Autowired
    private DeviceDao deviceDao;

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);

    @GetMapping("/house")
    public House getHouse() {
        return house;
    }

    @GetMapping("/house/room/{roomName}")
    public ResponseEntity<Room> getRoom(
            @PathVariable("roomName") String roomName
    ) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(house.getRoom(roomName));
    }

    @PostMapping("/house/room")
    public ResponseEntity<Room> addRoom(
            @RequestParam(value = "roomName") String roomName
    ) throws ResourceAlreadyExistException {
        house.addRoom(new Room(roomName, deviceDao));
        Room room = null;
        try {
            room = house.getRoom(roomName);
        } catch (ResourceNotFoundException e) {
            logger.error("После добавления комната не появилась: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @DeleteMapping("/house/room")
    public ResponseEntity<Room> deleteRoom(
            @RequestParam(value = "roomName") String roomName
    ) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                house.removeRoom(roomName)
        );
    }

    @PutMapping("/house/room")
    public ResponseEntity<Room> updateRoom(
            @RequestParam(value = "newRoomName") String newRoomName,
            @RequestParam(value = "oldRoomName") String oldRoomName
    ) throws ResourceNotFoundException, ResourceAlreadyExistException {
        house.updateRoom(oldRoomName, newRoomName);
        Room room = null;
        try {
            room = house.getRoom(newRoomName);
        } catch (ResourceNotFoundException e) {
            logger.error("После обновления комната не обновилась: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }

}
