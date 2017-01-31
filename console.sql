create table users (id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
                     username CHAR(25) NOT NULL,
                     password CHAR(25) NOT NULL,
                     email CHAR(55),
                     text CHAR(25),
                     playlists INTEGER,
                     priviledges INTEGER);

create table playlists (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        name CHAR(25),
                        role INTEGER);
create table playlist (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       song INTEGER);
create table song (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                   userID INTEGER,
                   location CHAR(125) NOT NULL,
                   name CHAR(55),
                   performer CHAR(55),
                   duration CHAR(25));
create table credentials (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          userID INTEGER,
                          site CHAR(125) NOT NULL,
                          username CHAR(25),
                          password CHAR(25));