package iot.hub.model;

import iot.hub.dao.DeviceDao;
import iot.hub.dao.RoomDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
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

    public void addRoom(Room room) {
        if (rooms.containsKey(room.getName())) {
            System.out.println("Комната " + room.getName() + " уже есть");
        }
        else {
            rooms.put(room.getName(), room);
            roomDao.save(room);
        }
    }

    public void removeRoom(String roomName) {
        rooms.remove(roomName);
        roomDao.delete(roomName);
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
