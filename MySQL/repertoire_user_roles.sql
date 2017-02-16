CREATE TABLE repertoire.user_roles (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, username CHAR(25) NOT NULL, rolename CHAR(25) NOT NULL);
INSERT INTO repertoire.user_roles (username, rolename) VALUES ('pknibbe', 'Admin');
INSERT INTO repertoire.user_roles (username, rolename) VALUES ('RoseMarie', 'Edit');
INSERT INTO repertoire.user_roles (username, rolename) VALUES ('Marie', 'Edit');
INSERT INTO repertoire.user_roles (username, rolename) VALUES ('James', 'Listen');
INSERT INTO repertoire.user_roles (username, rolename) VALUES ('Pieter', 'Listen');
INSERT INTO repertoire.user_roles (username, rolename) VALUES ('Beth', 'Edit');