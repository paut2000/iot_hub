package iot.hub.config;

import iot.hub.model.House;
import iot.hub.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HubConfig  implements CommandLineRunner {

    @Autowired
    private House house;

    @Override
    public void run(String... args) throws Exception {
        house.addRoom(new Room("bedroom"));
        house.addRoom(new Room("kitchen"));
    }
}
