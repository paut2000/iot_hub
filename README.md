# iot_hub

## About

This is the repo for server-side application of Smart Home.

## Component interaction

<img src="https://user-images.githubusercontent.com/48650232/175784303-ebe8c0f0-7ce0-44ab-a591-078a32980fac.png" alt="drawing"/>

## API

### ReST

#### get /api/house
Information about devices connected to the system.

#### delete /api/house/{ID}
Disconect device and delete all records linked with this device from database.

#### post /api/control/actuator/disable/{ID}
Disabling actuator, also save information about this action to database.

#### post /api/control/actuator/enable/{ID}
Enabling actuator, also save information about this action to database.

#### post /api/control/rgba/{ID}
Set RGBA-color for rgba-actuator, also save information about this action to database.

#### post /api/control/rgba/nosave/{ID}
Set RGBA-color for rgba-actuator, but not save info to database.

#### get /api/data/{ID}
Get device state.

#### get /api/statistic/period/{ID}?start={START}&end={END}
Get statistic sample for period.

### MQTT

#### /set/{MAC}
Used for sending data to device.

#### /get/{MAC}
Used for receive data from device. Server subsribe to this theme.

#### /device/new/+
Used for recieve information about new devices added to the system.

#### /device/died
Used for recieve information about devices lost connection.

## UML-diagramm

<img src="https://user-images.githubusercontent.com/48650232/175785489-13586720-51e1-4a25-994e-5680b55440bd.png" alt="drawing"/>

1. Green - model
2. Grey - ReST-controllers
3. Blue - database-access layer
4. Yellow - MQTT-client
5. Red - exceptions
6. Purple - messages

## Entity relashion

For mapping data from relational model to objective is used pattern "Data Mapper".

<img src="https://user-images.githubusercontent.com/48650232/175785720-908c026a-4f57-46ca-b5ef-1272b67c4934.png" alt="drawing"/>

