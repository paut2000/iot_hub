package iot.hub.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;


public abstract class AbstractDao {

    @Autowired
    protected Connection connection;

    protected PreparedStatement statement = null;

}
