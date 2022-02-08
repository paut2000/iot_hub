package iot.hub.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class House {

    @Getter
    private LinkedHashMap<String, Room> rooms = new LinkedHashMap<>();

    public void addRoom(Room room) {
        rooms.put(room.getName(), room);
    }

    public void removeRoom(String roomName) {
        rooms.remove(roomName);
    }

}
