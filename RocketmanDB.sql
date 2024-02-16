DROP TABLE gameData; 

CREATE TABLE gameData (
username VARCHAR (30) NOT NULL, 
password VARBINARY (30) NOT NULL, 
wins INTEGER (50), 
losses INTEGER (50), 
eliminations INTEGER (50),
rocketsFired INTEGER (500), 
blocksDestroyed INTEGER (500), 
powerupsCollected INTEGER (500),

CONSTRAINT gameData_userName_pk PRIMARY KEY(userName));

INSERT INTO gameData VALUES('mason', AES_ENCRYPT('yourmom!','key'),15,20,450,1200,2);