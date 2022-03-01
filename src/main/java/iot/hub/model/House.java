package iot.hub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iot.hub.dao.RoomDao;
import iot.hub.exception.ResourceAlreadyExistException;
import iot.hub.exception.ResourceNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class House {

    @Getter
    private Map<String, Room> rooms;

    @Autowired
    private RoomDao roomDao;

    @PostConstruct
    private void postConstructor() {
        rooms = roomDao.findAll();
    }

    public void addRoom(Room room) throws ResourceAlreadyExistException {
        if (rooms.containsKey(room.getName())) {
            throw new ResourceAlreadyExistException("комната " + room.getName());
        }
        else {
            rooms.put(room.getName(), room);
            roomDao.save(room);
        }
    }

    public void removeRoom(String roomName) throws ResourceNotFoundException {
        if (rooms.containsKey(roomName)) {
            rooms.remove(roomName);
            roomDao.delete(roomName);
        }
        else {
            throw new ResourceNotFoundException("комната " + roomName);
        }
    }

    @JsonIgnore
    public Room getRoom(String roomName) throws ResourceNotFoundException {
        Room room = this.rooms.get(roomName);
        if (room == null) throw new ResourceNotFoundException("Комната " + roomName);
        return room;
    }

}
