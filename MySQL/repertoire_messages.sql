CREATE TABLE repertoire.messages (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  subject CHAR(63) NOT NULL,
  sender INT,
  receiver INT,
  readflag INT,
  content CHAR(255) NOT NULL);
