package iot.hub.model;

import iot.hub.repository.RoomRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class House {

    @Autowired
    private RoomRepo roomRepo;

    @Getter
    private Map<String, Room> rooms;

    @PostConstruct
    private void postConstructor() {
        rooms = StreamSupport.stream(roomRepo.findAll().spliterator(), false)
                .collect(Collectors.toMap(Room::getName, e->e));
    }

    public void addRoom(Room room) {
        rooms.put(room.getName(), room);
        roomRepo.save(room);
    }

    public void removeRoom(String roomName) {
        roomRepo.delete(rooms.get(roomName));
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
