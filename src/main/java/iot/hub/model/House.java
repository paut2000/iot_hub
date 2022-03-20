package iot.hub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iot.hub.dao.RoomDao;
import iot.hub.exception.ResourceAlreadyExistException;
import iot.hub.exception.ResourceNotFoundException;
import iot.hub.model.device.AbstractDevice;
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
        roomDao.save(room);
        rooms.put(room.getName(), room);
    }

    public Room removeRoom(String roomName) throws ResourceNotFoundException {
        if (!rooms.containsKey(roomName)) {
            throw new ResourceNotFoundException("комната " + roomName);
        }
        roomDao.delete(roomName);
        return rooms.remove(roomName);
    }

    public void updateRoom(String oldRoomName, String newRoomName) throws ResourceNotFoundException, ResourceAlreadyExistException {
        if (!rooms.containsKey(oldRoomName)) {
            throw new ResourceNotFoundException("комната " + oldRoomName);
        }
        if (rooms.containsKey(newRoomName)) {
            throw new ResourceAlreadyExistException("комната " + newRoomName);
        }
        roomDao.update(oldRoomName, newRoomName);
        Room room = rooms.remove(oldRoomName);
        room.setName(newRoomName);
        rooms.put(newRoomName, room);
    }

    @JsonIgnore
    public Room getRoom(String roomName) throws ResourceNotFoundException {
        Room room = this.rooms.get(roomName);
        if (room == null) throw new ResourceNotFoundException("Комната " + roomName);
        return room;
    }

    @JsonIgnore
    public AbstractDevice getDevice(String serialNumber) throws ResourceNotFoundException {
        for (Room room : rooms.values()) {
            if (room.getAbstractDevices().containsKey(serialNumber)) {
                return room.getAbstractDevices().get(serialNumber);
            }
        }
        throw new ResourceNotFoundException("Девайс " + serialNumber);
    }

}
