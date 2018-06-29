INSERT INTO Building (capacity, name, seperatemanagment, active) VALUES (477, 'Suites At Academy Square', 0, 1)

SELECT * FROM Building;

INSERT INTO RoomType (buildingid, capacity, description, ADA, active) VALUES (1, 7, '6 man apartment with extra mattress in room A', 0, 1)
INSERT INTO RoomType (buildingid, capacity, description, ADA, active) VALUES (1, 9, '8 man apartment with extra mattress in room A', 0, 1)
INSERT INTO RoomType (buildingid, capacity, description, ADA, active) VALUES (1, 9, '8 man apartment with extra mattress in room A, room D and E are one person rooms', 0, 1)

--Get the room types for all buildings
SELECT 
	Building.id as 'Building ID',
	Building.name as 'Building Name',
	RoomType.id as 'Room Type ID',
	RoomType.capacity as 'Room Type Capacity',
	RoomType.description as 'Room Type Description'
FROM
	Building INNER JOIN RoomType ON (Building.id = RoomType.buildingid)
	

INSERT INTO Room (buildingid, roomtypeid, roomnum, notes, vacant, active) VALUES (1, *, *, 0, 1)

SELECT 
	Building.id as 'Building ID',
	Building.name as 'Building Name'
FROM
	Room INNER JOIN	RoomType ON (Room.roomtypeid = RoomType.id) INNER JOIN Building ON (RoomType.buildingid = Building.id)