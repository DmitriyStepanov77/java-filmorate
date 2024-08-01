DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Friends CASCADE;
DROP TABLE IF EXISTS Films CASCADE;
DROP TABLE IF EXISTS Likes CASCADE;
DROP TABLE IF EXISTS Genres_Films CASCADE;
DROP TABLE IF EXISTS Genre CASCADE;
DROP TABLE IF EXISTS MPA CASCADE;

CREATE TABLE Users(
    User_ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Login VARCHAR(50),
    Email VARCHAR(50),
    Name VARCHAR(50),
    Birthday TIMESTAMP
);

CREATE TABLE Friends(
    User_ID BIGINT not null,
    Friend_ID BIGINT not null,
    Status BOOLEAN,
    PRIMARY KEY(User_ID, Friend_ID),
    FOREIGN KEY (User_ID) REFERENCES Users(User_ID),
    FOREIGN KEY (Friend_ID) REFERENCES Users(User_ID)
);

CREATE TABLE MPA(
    MPA_ID INT not null PRIMARY KEY,
    Name VARCHAR(100)
);

CREATE TABLE Films(
    Film_ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Name VARCHAR(100),
    Description VARCHAR(200),
    ReleaseDate TIMESTAMP,
    Duration INT,
    MPA_ID INT,
    FOREIGN KEY (MPA_ID) REFERENCES MPA(MPA_ID)
);

CREATE TABLE Likes(
    Film_ID BIGINT not null,
    User_ID BIGINT not null,
    PRIMARY KEY(Film_ID, User_ID),
    FOREIGN KEY (Film_ID) REFERENCES Films(Film_ID),
    FOREIGN KEY (User_ID) REFERENCES Users(User_ID)
);

CREATE TABLE Genre(
    Genre_ID INT not null PRIMARY KEY,
    Name VARCHAR(100)
);

CREATE TABLE Genres_Films(
    Film_ID BIGINT not null,
    Genre_ID INT not null,
    PRIMARY KEY(Film_ID, Genre_ID),
    FOREIGN KEY (Film_ID) REFERENCES Films(Film_ID),
    FOREIGN KEY (Genre_ID) REFERENCES Genre(Genre_ID)
);

INSERT INTO Genre VALUES(1, 'Комедия');
INSERT INTO Genre VALUES(2, 'Драма');
INSERT INTO Genre VALUES(3, 'Мультфильм');
INSERT INTO Genre VALUES(4, 'Триллер');
INSERT INTO Genre VALUES(5, 'Документальный');
INSERT INTO Genre VALUES(6, 'Боевик');

INSERT INTO MPA VALUES(1, 'G');
INSERT INTO MPA VALUES(2, 'PG');
INSERT INTO MPA VALUES(3, 'PG-13');
INSERT INTO MPA VALUES(4, 'R');
INSERT INTO MPA VALUES(5, 'NC-17');