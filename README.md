# ROCKETMAN
 ROCKETMAN is a game by Team 4 of Software Engineering 2024

 Inspired by classic games such as Bomberman and Tanks, ROCKETMAN offeres a competitive top-down shooter experience for 2-8 players within a destructible grid based environment. It features fast paced gameplay, a fully fledged lobby system, statistics collection, and a bundled map editor as well.

 ## Installation

 The latest release for ROCKETMAN can be obtained at this link: https://github.com/MasonR4/ROCKETMAN/releases/tag/v1.0-release

### Client Setup
To setup the ROCKETMAN client, follow the steps below.
```
1. Download the latest version of the ROCKETMAN source from the link above
2. Extract the .ZIP into your desired location for the game
3. Run the 'RocketmanClient.bat' from the 'bat_files' directory.
4. (Optional) The 'config.txt' file contains some default settings that can be customized as needed.
```
### Server + DB Setup
The server setup is similar to the client setup but requires you to instantiate a local copy of the database prior to starting the server. The server setup portion utilizes XAMPP to manage the MySQL server.
```
1. Download the latest version of the ROCKETMAN source from the link above.
2. Extract the .ZIP into your desired location for the game.
3. Launch XAMPP and start your MySQL server.
4. Login to the server as student and select the 'student_space' database > mysql -h localhost -u student -p student_space
   Follow these steps if you do not have the 'student' account setup:
   1. Login as root and switch to the 'mysql' > use mysql;
   2. Create a new user named 'student' and grant them all privilieges > grant all privileges on * to student@localhost identified by 'hello' with grant option; commit;
   Note: the 'student' account with password 'hello' is the account that is configured by default in the 'db.properties' and can be changed if needed.
   3. Create the 'student_space' database > create database student_space;
   4. Grant student all privileges on 'student_space' > grant all on student_space.* to student identified by 'hello';
   5. Exit the MySQL console and restart the MySQL server through XAMPP.
5. Copy the path to the 'RocketmanDB.sql' file within the game directory.
6. Use the source command within the MySQL console to create the necessary tables in the database > source C:\EXAMPLE-PATH\ROCKETMAN\RocketmanDB.sql
7. Run RocketmanServer.bat from the 'bat_files' directory.
8. (Optional) The 'server_config.txt' file contains some default settings that can be customized as needed.
```
## Thanks for Playing!

  
