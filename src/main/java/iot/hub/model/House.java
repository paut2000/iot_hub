package iot.hub.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class House {

    @Getter
    private Map<String, Room> rooms = new LinkedHashMap<>();

    @PostConstruct
    private void postConstructor() {

    }

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

        if (rs.length() != 0) rs.deleteCharAt(rs.length() - 1);

        return "{" + "\"house\":{" + rs + "}}";
    }
}
