CREATE TABLE Users (
    username VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(15) NOT NULL,
    profileid INT,
FOREIGN KEY (profileid) REFERENCES profiles(rowid)
 );

//drop table Users;

INSERT INTO Users (rowid,username, password) VALUES
    (1,'megha','123456',101),
    (2,'ved','123456',102);



CREATE TABLE Profiles (
     id INTEGER PRIMARY KEY  ASC,
    firstname VARCHAR(20) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL,
    profpic BLOB,
    userid int null
   );


INSERT INTO Profiles VALUES
    (101,'Megha', 'Iyer', 'connecteduqa@gmail.com',null,1),
  (102,'Ved', 'Iyer', 'connecteduqa@gmail.com',null,2);


***************************

CREATE TABLE Stories (
   id INTEGER PRIMARY KEY ASC,
    title VARCHAR(30) NOT NULL,
    firstPart VARCHAR(300) NOT NULL,
    middlePart VARCHAR(300) NOT NULL,
    lastPart VARCHAR(300) NOT NULL,
    authorid INT NULL,
    storypic BLOB
  );


INSERT INTO Stories VALUES
    (1001,'A Clever Cat', 'Once upon a time there was a cat named Poncho. She was very clever.' , 'Suddenly, there was a tragedy ...', 'Cat convinced all her friends and with the team work they were able to come out of the drought.','1',NULL),
     (1002,'An elephant has cold', 'Once upon a time there was a cat named Poncho. She was very clever.' , 'Suddenly, there was a tragedy ...', 'Cat convinced all her friends and with the team work they were able to come out of the drought.','2',NULL);