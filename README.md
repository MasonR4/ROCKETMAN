# ROCKETMAN
 ROCKETMAN is a game by Team 4 of Software Engineering 2024

 Inspired by classic games such as Bomberman and Tanks, ROCKETMAN offers a competitive top-down shooter experience for 2-8 players within a destructible grid based environment. It features fast paced gameplay, a fully fledged lobby system, statistics collection, and a bundled map editor as well.

 ## Installation


### Client Setup
To setup the ROCKETMAN client, follow the steps below.
```
1. Download the latest version of the ROCKETMAN source.
2. Extract the .ZIP into your desired location for the game
3. Run the 'RocketmanClient.bat' from the 'bat_files' directory.
4. (Optional) The 'config.txt' file contains some default settings that can be customized as needed.
```
### Server + DB Setup
The server setup is similar to the client setup but requires you to instantiate a local copy of the database prior to starting the server. The server setup portion utilizes XAMPP to manage the MySQL server.
```
1. Download the latest version of the ROCKETMAN source.
2. Extract the .ZIP into your desired location for the game.
3. Launch XAMPP and start your MySQL server.
4. Login to the server as student and select the 'student_space' database > mysql -h localhost -u student -p student_space
   Follow these steps if you do not have the 'student' account setup:
   1. Login as root and switch to the 'mysql' > use mysql;
   2. Create a new user named 'student' and grant them all privileges > grant all privileges on * to student@localhost identified by 'hello' with grant option; commit;
   Note: the 'student' account with password 'hello' is the account that is configured by default in the 'db.properties' and can be changed if needed.
   3. Create the 'student_space' database > create database student_space;
   4. Grant student all privileges on 'student_space' > grant all on student_space.* to student identified by 'hello';
   5. Exit the MySQL console and restart the MySQL server through XAMPP.
5. Copy the path to the 'RocketmanDB.sql' file within the game directory.
6. Use the source command within the MySQL console to create the necessary tables in the database > source C:\EXAMPLE-PATH\ROCKETMAN\RocketmanDB.sql
7. Run RocketmanServer.bat from the 'bat_files' directory.
8. (Optional) The 'server_config.txt' file contains some default settings that can be customized as needed.
```

### Map Editor
The Map Editor is included within the game, but you will need to import the project into Papyrus/Eclipse to add your maps to the game.

To use the map editor, left click to place a block. Click an already placed block to change that block to a spawn, and click it a third time to remove. The Tools tab contains a Line tool and a Hollow Square tool. To use the line tool, select the start and end points of the line. To use the hollow square tool, select the boxes you want to be opposite corners of the square. Under the File tab you can export or clear the grid. When exporting, your map must have at least 8 spawns. Name it something that won't conflict with the maps already in game. The output is stored in the 'output.txt' file. If you have the project loaded into Papyrus/Eclipse, you only need to add the map output to the constructor of the 'MapCreator.java' class within the 'server_utilities' package. Afterwards, any players joining your server will be able to play your new map.

## Thanks for Playing!

  
