DROP TABLE userData; 

CREATE TABLE userData (
username VARCHAR (30) NOT NULL, 
password VARBINARY (30) NOT NULL, 

CONSTRAINT userData_userName_pk PRIMARY KEY(userName));

CREATE TABLE statistics (
wins INTEGER (50), 
losses INTEGER (50), 
eliminations INTEGER (50),
rocketsFired INTEGER (255), 
blocksDestroyed INTEGER (255), 
powerupsCollected INTEGER (255),

CONSTRAINT statistics_userName_pk FOREIGN KEY(userName));

INSERT INTO userData VALUES('mason', AES_ENCRYPT('yourmom!','key'));