CREATE TABLE userAdmin (
id TEXT PRIMARY KEY UNIQUE NOT NULL,
username VARCHAR(200) UNIQUE NOT NULL,
password VARCHAR(100) NOT NULL
);