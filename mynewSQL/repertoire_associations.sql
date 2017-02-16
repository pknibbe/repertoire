CREATE TABLE repertoire.associations (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  leftTableName CHAR(25) NOT NULL,
  leftTableKey INT,
  rightTableKey INT,
  rightTableName CHAR(25) NOT NULL);
