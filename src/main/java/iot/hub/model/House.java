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

    @Override
    public String toString() {

        StringBuilder rs = new StringBuilder();

        for (Room room : rooms.values()) {
            rs.append(room.toString()).append(",");
        }

        rs.deleteCharAt(rs.length() - 1);

        return "{" + "\"house\":{" + rs + "}}";
    }
}
