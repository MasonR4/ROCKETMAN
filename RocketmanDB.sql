DROP TABLE userData; 
DROP TABLE statistics;

CREATE TABLE userData (
username VARCHAR (30) NOT NULL, 
password VARBINARY (30) NOT NULL, 

CONSTRAINT userData_userName_pk PRIMARY KEY(userName));

CREATE TABLE statistics (
username VARCHAR (30) NOT NULL,
wins INTEGER (50), 
losses INTEGER (50), 
eliminations INTEGER (50),
deaths INTEGER (50),
rocketsFired INTEGER (255), 
blocksDestroyed INTEGER (255), 
damageDealt INTEGER(255),
totalScore INTEGER(255),

CONSTRAINT statistics_userName_pk PRIMARY KEY(userName));

INSERT INTO userData VALUES('mason', AES_ENCRYPT('yourmom!','key'));
INSERT INTO userData VALUES('noble', AES_ENCRYPT('12345678','key'));
INSERT INTO userData VALUES('ashton', AES_ENCRYPT('acorn','key'));
INSERT INTO statistics VALUES('mason', 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO statistics VALUES('ashton', 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO statistics VALUES('noble', 5, 1, 98, 1, 100, 500, 0, 0);

