DROP TABLE gameData; 

CREATE TABLE gameData (
username VARCHAR (30) NOT NULL, 
password VARCHAR (30) NOT NULL, 
wins INTEGER (50), 
losses INTEGER (50), 
eliminations INTEGER (50),
rocketsFired INTEGER (500), 
blocksDestroyed INTEGER (500), 
powerupsCollected INTEGER (500),
CONSTRAINT gameData_userName_pk PRIMARY KEY(userName));